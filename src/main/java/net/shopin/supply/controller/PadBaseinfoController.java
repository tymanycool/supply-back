package net.shopin.supply.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.PadLog;
//import net.shopin.supply.domain.entity.PadOperateLog;
import net.shopin.supply.domain.entity.PadPurchaseInfo;
import net.shopin.supply.domain.entity.PadSupply;
import net.shopin.supply.domain.vo.PadBaseinfoVO;
import net.shopin.supply.persistence.PadBaseinfoMapper;
import net.shopin.supply.service.IPadBaseinfoService;
import net.shopin.supply.service.IPadPurchaseInfoService;
import net.shopin.supply.service.IPadSupplyRelationService;
import net.shopin.supply.service.IPadSupplyService;
import net.shopin.supply.util.CommonProperties;
import net.shopin.supply.util.HttpUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ls.LSInput;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopin.core.constants.ErrorCodeConstants;
import com.shopin.core.util.DateUtils;
import com.shopin.core.util.ResultUtil;

/**
 * @ClassName: PadBaseinfoController
 * @author zhangq
 * @date 2014-12-24 上午11:09:52
 *
 */
@Controller
@RequestMapping("/padBaseinfo")
public class PadBaseinfoController {

	@Autowired
	private IPadBaseinfoService padBaseinfoService;
	@Autowired
	private IPadPurchaseInfoService padPurchaseInfoService;
	@Autowired
	private IPadSupplyService padSupplyService;
	private Logger logger = Logger.getLogger(this.getClass());
	
	@ResponseBody
	@RequestMapping("/padCanAllocateList")
	public String padCanAllocateList(HttpServletRequest request, HttpServletResponse response) {

		String json = "";
		String padNo = request.getParameter("padNo");
		String macAddress = request.getParameter("macAddress");
		String padStatus = request.getParameter("padStatus");
		String useType = request.getParameter("useType");
		String shopId = request.getParameter("shopId");
		String targetShopId = request.getParameter("targetShopId");
		String supplyId = request.getParameter("supplyId");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		HashMap<String, Object> newMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			if (null != padNo && !padNo.equals("")) {
				map.put("padNo", padNo);
			}
			if (null != macAddress && !macAddress.equals("")) {
				map.put("macAddress", macAddress);
			}
			if (null != padStatus && !padStatus.equals("") && !padStatus.equals("-1")) {
				map.put("padStatus", Integer.parseInt(padStatus));
			}
			if (null != useType && !useType.equals("") && !useType.equals("-1")) {
				map.put("useType", Integer.parseInt(useType));
			}
			if (null != shopId && !shopId.equals("")) {
				map.put("shopId", shopId);
			}
			if (null != targetShopId && !targetShopId.equals("")) {
				map.put("targetShopId", targetShopId);
			}
			if (null != supplyId && !supplyId.equals("")) {
				map.put("supplyId", supplyId);
			}
			if (null != start && !start.equals("")) {
				map.put("start", Integer.parseInt(start));
			}
			if (null != limit && !limit.equals("")) {
				map.put("limit", Integer.parseInt(limit));
			}
			
			
			List<PadBaseinfoVO> list = this.padBaseinfoService.selectPadCanAllocateList(map);
			Integer count = this.padBaseinfoService.getPadCanAllocateCount(map);
				newMap.put("list", list);
				newMap.put("total", count);
				json = ResultUtil.createSuccessResult(newMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;

	}

	/**
	 * 保存pad基本信息
	 * 
	 * @param request
	 *            padNo pad编号，username 系统登录用户姓名，macAddress mac地址，purchaseOrderno
	 *            采购订单号，padStatus pad状态，brand pad品牌
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String save(HttpServletRequest request, HttpServletResponse response) {

		String json = "";
		String sid = request.getParameter("sid");
		String padNo = request.getParameter("padNo");// pad 编号
		String username = request.getParameter("username");// 系统登录用户名
		String userSid = request.getParameter("userSid");// 系统登录用户名sid
		String macAddress = request.getParameter("macAddress");// MAC地址
		String purchaseOrderno = request.getParameter("purchaseOrderno");// 采购订单号
		String padStatus = request.getParameter("padStatus");// pad 状态
		String useType = request.getParameter("useType");// 使用类型
		String useTypeDesc = request.getParameter("useTypeDesc");// 使用类型描述
		String brand = request.getParameter("brandName");// pad 品牌
		String padPurchaseBatchNo = request.getParameter("padPurchaseBatchNo");// 批次号
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("padNo", padNo);
			PadBaseinfo padBaseinfoObj = this.padBaseinfoService.checkPadinfoIsUnique(map);

			PadBaseinfo padBaseinfo = new PadBaseinfo();
			if (sid != null && !"".equals(sid)) {
				padBaseinfo.setSid(Integer.parseInt(sid));
				if (null != padBaseinfoObj) {
					if (padBaseinfoObj.getSid() != Integer.parseInt(sid)) { // 更新：修改的pad编号不可存在。
						return ResultUtil.createFailureResult("10000", "此PAD编号已存在，请重新输入！");
					}
				}
			} else {
				if (null != padBaseinfoObj) {
					return ResultUtil.createFailureResult("10000", "此PAD编号已存在，请重新输入！");
				}
			}

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("macAddress", macAddress);
			padBaseinfoObj = this.padBaseinfoService.checkPadinfoIsUnique(param);

			if (sid != null && !"".equals(sid)) {
				padBaseinfo.setSid(Integer.parseInt(sid));
				if (null != padBaseinfoObj) {
					if (padBaseinfoObj.getSid() != Integer.parseInt(sid)) {
						return ResultUtil.createFailureResult("00000", "此PAD MAC地址已存在，请重新输入！");
					}
				}
			} else {
				if (null != padBaseinfoObj) {
					return ResultUtil.createFailureResult("00000", "此PAD MAC地址已存在，请重新输入！");
				}
			}
			if (padNo != null && !padNo.equals("")) {
				padBaseinfo.setPadNo(padNo);
			}
			if (macAddress != null && !macAddress.equals("")) {
				padBaseinfo.setMacAddress(macAddress);
			}
			if (purchaseOrderno != null && !purchaseOrderno.equals("")) {
				padBaseinfo.setPurchaseOrderno(purchaseOrderno);
			}
			if (padStatus != null && !padStatus.equals("")) { // 状态非必选 (修改界面)
				padBaseinfo.setPadStatus(Integer.parseInt(padStatus));
			}
			if (null != useType && !"".equals(useType)) {
				padBaseinfo.setUseType(Integer.parseInt(useType));
				padBaseinfo.setUseTypeDesc(useTypeDesc);
			}
			padBaseinfo.setCreateTime(new Date());
			padBaseinfo.setOperator(username);
			padBaseinfo.setOperatorSid(Integer.parseInt(userSid));
			padBaseinfo.setPadPurchaseBatchNo(padPurchaseBatchNo);
			// added by syk 
			// 更新时：不需判断
			if (null != padPurchaseBatchNo && !padPurchaseBatchNo.equals("") && !"null".equals(padPurchaseBatchNo)) {

				PadPurchaseInfo padPurchaseInfo = this.padPurchaseInfoService
						.getPadPurchseInfoByBatchNo(padPurchaseBatchNo);

				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("padPurchaseBatchNo", padPurchaseBatchNo);
				
//				int count = this.padBaseinfoService.getPadCountByBatchNo(map2);
				int count = this.padBaseinfoService.getCountByParam(map2);
				
				padBaseinfo.setBrand(padPurchaseInfo.getPadBrand()); //pad品牌=padPurchase品牌
				if (padPurchaseInfo != null && padPurchaseInfo.getPadPurchaseNum() != null) {
					int purchaseNum = padPurchaseInfo.getPadPurchaseNum().intValue();
					if (count >= purchaseNum) {
						json = ResultUtil.createFailureResult("00000", "该批次PAD个数添加已达上限！");
						return json;
					}
				}
			}
			// by syk end

			int result = this.padBaseinfoService.save(padBaseinfo, username,userSid);

			if (result != 1) {
				json = ResultUtil.createFailureResult("00000", "保存失败！");
			} else {
				json = ResultUtil.createSuccessResult("保存成功");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 根据PAD编号查询 @Title: selectPadByPadNo @param @param request @param @param
	 * response @param @return 设定文件 @return String 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/selectPadByPadNo", method = { RequestMethod.POST, RequestMethod.GET })
	public String selectPadByPadNo(HttpServletRequest request, HttpServletResponse response) {

		String json = "";
		String padNo = request.getParameter("padNo");
		try {
			PadBaseinfo padBaseinfo = this.padBaseinfoService.selectPadByPadNo(padNo);
			if (null != padBaseinfo) {
				json = ResultUtil.createSuccessResult(padBaseinfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 删除录入的PAD信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePadInfoBySid", method = { RequestMethod.GET, RequestMethod.POST })
	public String deletePadInfoBySid(HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		try {
			String sids = request.getParameter("sid");
			String username = request.getParameter("username");
			String userSid = request.getParameter("userSid");
			String sidArr[] = sids.split(",");
			for (int i = 0; i < sidArr.length; i++) {
				PadBaseinfo padBaseInfo = padBaseinfoService.selectPadbyPk(Long.valueOf(sidArr[i]));
				this.padBaseinfoService.deletePadInfoBySid(Long.parseLong(sidArr[i]),username,userSid);
				logger.info("系统时间：" + DateUtils.formatDate2Str(new Date()) + "  用户：" + username + "删除 编号为"
						+ padBaseInfo.getPadNo() + " 的PAD");
			}

			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 保存调拨信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savePadAllocationInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String savePadAllocationInfo(HttpServletRequest request) {
		String json = "";
		String userName = request.getParameter("userAuthName");
		String userSid = request.getParameter("userAuthId");
		Map<String, String> info = new HashMap<String, String>();
		info.put("userName", userName);
		info.put("userSid", userSid);
		try {
			String targetShopId = request.getParameter("padTargetShop");
			String targetShopName = request.getParameter("padTargetShopName");
			String padIds = request.getParameter("padIds");
			String padIdArr[] = padIds.split(",");
			this.padBaseinfoService.savePadAllocationInfo(padIdArr, targetShopId, targetShopName, info);
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 待收货查询:目的门店为本店，状态为在途
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPadReceiveInfo")
	public String getPadReceiveInfo(HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		try {
			String targetShopId = request.getParameter("targetShopId");
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("targetShopId", targetShopId);
//			map.put("padStatus", 5);// ['0',"在库"],['1',"卖场"],['2',"送修"],['3',"停用"],['4',"丢失"],['5',"在途"]
			if (null != start && !start.equals("")) {
				map.put("start", Integer.parseInt(start));
			}
			if (null != limit && !limit.equals("")) {
				map.put("limit", Integer.parseInt(limit));
			}
			List padBaseinfo = this.padBaseinfoService.getPadBaseAndSupplyList(map);
			int count = this.padBaseinfoService.getCountByParam(map);
			map.put("list", padBaseinfo);
			json = ResultUtil.createSuccessResult(map);
			json = json.substring(0, json.length() - 1) + ",total:" + count + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 确认收货
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savePadReceiveInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String savePadReceiveInfo(HttpServletRequest request) {
		String json = "";
		String userName = request.getParameter("userAuthName");
		String userSid = request.getParameter("userAuthId");
		Map<String, String> info = new HashMap<String, String>();
		info.put("userName", userName);
		info.put("userSid", userSid);
		try {
			String padIds = request.getParameter("padIds");
			String padIdArr[] = padIds.split(",");
			this.padBaseinfoService.savePadReceiveInfo(padIdArr, info);
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("000000", e.getMessage());
		}
		return json;
	}

	/**
	 * PAD信息报表(查找)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectPadInfoReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String selectPadInfoReport(HttpServletRequest request) {
		String json = "";
		try {
			String shopId = request.getParameter("shopId");
			String shopName = request.getParameter("shopName");

			if (shopId != null && shopId == "") {
				// 查询所有门店信息
				json = selectPadInfoReportAll(request);
				return json;
			}
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shopId", shopId);
			// TODO
			String hasBatchNo = request.getParameter("hasBatchNo");
			if (hasBatchNo != null) {
				paramMap.put("hasBatchNo", hasBatchNo);
			}
			Map<String, String> map = this.padBaseinfoService.selectPadInfoReportByPadStatus(paramMap);
			map.put("shopName", shopName);
			json = ResultUtil.createSuccessResult(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/selectPadInfoReportAll", method = { RequestMethod.GET, RequestMethod.POST })
	public String selectPadInfoReportAll(HttpServletRequest request) {
		String json = "";
		JSONArray array = null;

		String hasBatchNo = request.getParameter("hasBatchNo");   //用于区分新旧系统：  旧系统批次号为：'00000000'
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		if (hasBatchNo != null) {
			paramMap.put("hasBatchNo", hasBatchNo);
		}

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			String url = CommonProperties.get("GET_SHOP_LIST");
			json = HttpUtil.GetUrlContent(url, null);
			JSONObject jsonO = JSONObject.fromObject(json);
			if (jsonO.getBoolean("success")) {
				array = jsonO.getJSONArray("result");
				// 获取到shopId，shopName.
				String shopId = "";
				String shopName = "";
				int length = array.size();
				for (int i = 0; i < length; i++) {
					JSONObject object = (JSONObject) array.get(i);
					shopId = object.getString("sid");
					shopName = object.getString("shopName");
					if (shopId != null && shopId.startsWith("10")) {   //实体店id均以“10”开头
						paramMap.put("shopId", shopId);
						Map<String, String> map1 = this.padBaseinfoService.selectPadInfoReportByPadStatus(paramMap);
						map1.put("shopName", shopName);
						list.add(map1);
					}
				}
				// 公司总部
				paramMap.put("shopId", "1000");
				Map<String, String> map2 = this.padBaseinfoService.selectPadInfoReportByPadStatus(paramMap);
				map2.put("shopName", "公司总部");
				list.add(map2);
				// 总计
				String noShopId = "";
				paramMap.put("shopId", noShopId);
				Map<String, String> map3 = this.padBaseinfoService.selectPadInfoReportByPadStatus(paramMap);
				map3.put("shopName", "共计");
				list.add(map3);
			}
			json = ResultUtil.createSuccessResult((Object) list);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 导入Excel
	 * 
	 * @param str
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importExcelOfPad", method = { RequestMethod.GET, RequestMethod.POST })
	public String importExcelOfPadInfo(HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		try {
			String padPurchaseBatchNo = request.getParameter("padPurchaseBatchNo");
			String userName = request.getParameter("username");
			String shopId = request.getParameter("shopid");
			String userSid = request.getParameter("userSid");
			Map<String, String> info = new HashMap<String, String>();
			info.put("userName", userName);
			info.put("shopId", shopId);
			info.put("userSid", userSid);
			info.put("padPurchaseBatchNo", padPurchaseBatchNo);
			InputStream inputStream = null;
			Workbook workbook = null;
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			// 直接忽略掉标题，读内容即从第2行开始
			int startRow = 1;

			for (FileItem item : items) {
				if (item.isFormField()) {
					logger.info(item.getFieldName());
					logger.info(item.getString());
				} else {
					logger.info("上传的文件名字：" + item.getName());
					inputStream = item.getInputStream();
				}
			}
			// 2003,2007
			if (!inputStream.markSupported()) {
				inputStream = new PushbackInputStream(inputStream, 8);
			}
			if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				return ResultUtil.createFailureResult("10000", "文件格式错误！");
			}
			Sheet sheet = workbook.getSheetAt(0);
			Row row;
			int rowCount = sheet.getLastRowNum() + 1;// 行数
			if (rowCount <= startRow) {// 数据可能为空
				return ResultUtil.createFailureResult("10000", "Excel中不存在可用数据!");
			}
			int columnCount = 2;// sheet.getRow(0).getPhysicalNumberOfCells();//列数
			HashMap<String, Object> resultList = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder();     //Mac地址格式不正确
			StringBuilder uniquePadNoSb = new StringBuilder(); //padNo唯一性错误
			StringBuilder uniqueMacAddressSb = new StringBuilder(); //Mac地址唯一性错误
			for (int i = startRow; i < rowCount; i++) {
				row = sheet.getRow(i);
				HashMap<String, String> result = new HashMap<String, String>();
				for (int j = 0; j < columnCount; j++) {
					Cell cell = row.getCell(j);
					if (cell != null && !"".equals(cell.toString())) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String content = cell.toString();
						switch (j) {
						case 0:
							//检查padNo唯一性
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("padNo", content);
							PadBaseinfo padBaseinfoObj = this.padBaseinfoService.checkPadinfoIsUnique(map);
							if(padBaseinfoObj !=null){
								uniquePadNoSb.append((i + 1) + ",");
								break;
							}
							result.put("padNo", content);
							break;
						case 1:
							if (!checkMacValid(content)) {
								sb.append((i + 1) + ",");
								break;
							}
							//检查mac地址唯一性
							Map<String, Object> map1 = new HashMap<String, Object>();
							map1.put("macAddress", content);
							PadBaseinfo padBaseinfoObj1 = this.padBaseinfoService.checkPadinfoIsUnique(map1);
							if(padBaseinfoObj1 !=null){
								uniqueMacAddressSb.append((i + 1) + ",");
								break;
							}
							result.put("macAddress", content.toLowerCase());
							break;
						}
					}
				}
				resultList.put("row" + i, result);
			}
			// 有错误信息，不添加任何数据
			if (sb.length() > 0) {
				return ResultUtil.createFailureResult("10000", "第" + sb.toString() + "行数据的MAC地址不正确请核对后重新填写！");
			}
			if (uniquePadNoSb.length() > 0) {
				return ResultUtil.createFailureResult("10000", "第" + uniquePadNoSb.toString() + "行数据的padNo已存在。");
			}
			if (uniqueMacAddressSb.length() > 0) {
				return ResultUtil.createFailureResult("10000", "第" + uniqueMacAddressSb.toString() + "行数据的MAC地址已存在。");
			}
			this.padBaseinfoService.savePadInfoOfExcel(resultList, info);
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
//			e.printStackTrace();
			return ResultUtil.createFailureResult("10000", e.getMessage());
		}
		return json;
	}

	// 简单判断mac地址是否合法
	public static boolean checkMacValid(String mac) {

		String regEx = "^([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}$"; // 格式：98:3c:88:33:22:0f
																// ,只能由0~9，a~f组成。
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(mac);
		return mat.matches();
	}

	/**
	 * 查看PAD历史操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectPadOperateLogByPadNo", method = { RequestMethod.POST, RequestMethod.GET })
	public String selectPadOperateLogByPadNo(HttpServletRequest request, HttpServletResponse response) {

		String json = "";
		String padNo = request.getParameter("padNo");
		try {
			List logs = this.padBaseinfoService.selectPadOperateLogByPadNo(padNo);
			/*for (Object obj : logs) {
				PadLog  padLog = (PadLog)obj;
				String description = padLog.getDescription();
				if(description.startsWith("{")){  //json数据
					if(description.startsWith("{\"success")){  //旧系统
						
					}else{
						
					}
				}
				Gson gson = new GsonBuilder().setDateFormat("").create();
				Map map = gson.fromJson(description, Map.class);
				map.get
			}*/
			if (null != logs) {
				json = ResultUtil.createSuccessResult(logs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 查看PAD信息模块，关联PAD入库表查询
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPadInfoAndPurchaseInfo")
	public String getPadInfoAndPurchaseInfo(HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		try {
			String padNo = request.getParameter("padNo");
			String macAddress = request.getParameter("macAddress");
			String padStatus = request.getParameter("padStatus");
			String useType = request.getParameter("useType");
			String shopId = request.getParameter("shopId");
			String supplyId = request.getParameter("supplyId");
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");
			String padPurchaseBatchNo = request.getParameter("padPurchaseBatchNo");// 该批次下已录入的信息
			String padSupply = request.getParameter("padProducer"); // PAD供货厂商
			String padbrand = request.getParameter("padBrand");
			String isCheck = request.getParameter("isCheck");
			Map<String, Object> map = new HashMap<String, Object>();
			

			if (null != padNo && !padNo.equals("")) {
				map.put("padNo", padNo);
			}
			if (null != macAddress && !macAddress.equals("")) {
				map.put("macAddress", macAddress);
			}
			if (null != padStatus && !padStatus.equals("") && !padStatus.equals("-1")) {
				map.put("padStatus", Integer.parseInt(padStatus));
			}
			if (null != useType && !useType.equals("") && !useType.equals("-1")) {
				map.put("useType", Integer.parseInt(useType));
			}
			if (null != shopId && !shopId.equals("")) {
				map.put("shopId", shopId);
			}
			if (null != supplyId && !supplyId.equals("")) {
				map.put("supplyId", supplyId);
			}
			if (null != start && !start.equals("")) {
				map.put("start", Integer.parseInt(start));
			}
			if (null != limit && !limit.equals("")) {
				map.put("limit", Integer.parseInt(limit));
			}
			if (null != padSupply && !padSupply.equals("")) {
				map.put("padSupply", padSupply);
			}

			if (null != padbrand && !padbrand.equals("")) {
				map.put("padBrand", padbrand);
			}
			if (null != padPurchaseBatchNo && !padPurchaseBatchNo.equals("") && !"null".equals(padPurchaseBatchNo)) {
				map.put("padPurchaseBatchNo", padPurchaseBatchNo);
			}
			List padBaseinfo = this.padBaseinfoService.getPadInfoAndPurchaseInfo(map);
			int count = this.padBaseinfoService.getCountByPadInfoAndPurchaseInfo(map);
			map.put("list", padBaseinfo);
			json = ResultUtil.createSuccessResult(map);
			json = json.substring(0, json.length() - 1) + ",total:" + count + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 批量更新pad的使用类型和状态
	 * 
	 * @author xuanyy
	 * @param request
	 * @param response
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value = { "/padBaseinfoBatchUpdate" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String padBaseinfoBatchUpdate(HttpServletRequest request, HttpServletResponse response) {
		String padNos = request.getParameter("padNos"); // padSids
		String[] padNosArr = padNos.split(",");
		String padStatus = request.getParameter("padStatus"); // pad状态
		String username = request.getParameter("username"); // 操作人
		String userSid = request.getParameter("userSid"); // 操作人sid
		boolean isBound = false;
		// 判断：和供应商是否解绑了。
		for (String padNo : padNosArr) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("padNo", padNo);
			List<PadSupply> padSupplies = padSupplyService.selectListByParam(paramMap);
			if (padSupplies != null) {
				if (padSupplies.size() > 1) {isBound = true;}
				else if (padSupplies.size() == 1) {
					PadSupply padSupply = padSupplies.get(0);
					Integer supplyId = padSupply.getSupplyId();
					if (supplyId != null) {isBound = true;}
				}
			}
		}

		// PAD ： 还有绑定关系。
		if (isBound) {
			String json = ResultUtil.createFailureResult("00000", "请先解绑，才可以更新状态");
			return json;
		}

		StringBuilder sb = new StringBuilder();
		for (String padNo : padNosArr) {
			PadBaseinfo pad = this.padBaseinfoService.selectPadByPadNo(padNo);
			pad.setPadStatus(Integer.valueOf(padStatus));
			pad.setUseType(-1); // 清空使用类型。 0 ： 导购 1：主管 2：内衣功能区，3：大场
			pad.setUseTypeDesc("");
			pad.setCreateTime(new Date());
			pad.setOperator(username);
			pad.setOperatorSid(Integer.valueOf(userSid));
			Integer result = this.padBaseinfoService.save(pad, username,userSid);
			if (result != 1) {
				sb.append(pad.getPadNo() + ",");
			}
		}
		// 判断是否更新失败
		if (sb.length() > 0) {
			String json = ResultUtil.createFailureResult("00000", "PAD编号为：" + sb.toString() + "更新失败");
		}
		String json = ResultUtil.createSuccessResult("批量更新成功");
		return json;
	}

	public static void main(String[] args) {
		/*
		 * String str = "11:11:11"; String str1 = "11:11:11:11:11:ag"; String
		 * str2 = "11:76:aa:11:11:ak"; boolean bo = checkMacValid(str); boolean
		 * bo1 = checkMacValid(str1); boolean bo2 = checkMacValid(str2);
		 * System.out.println(bo); System.out.println(bo1);
		 * System.out.println(bo2); System.out.println(str2.toLowerCase());
		 */

	}
	
	/**
	 * 点击PAD信息录入：列表  （默认查询所有在公司总部的pad）
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/padInfoInputList")
	public String padInfoInputList(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");
			String padPurchaseBatchNo = request.getParameter("padPurchaseBatchNo");// 批次号
			Map<String, Object> map = new HashMap<String, Object>();
			
			if (null != start && !start.equals("")) {
				map.put("start", Integer.parseInt(start));
			}
			if (null != limit && !limit.equals("")) {
				map.put("limit", Integer.parseInt(limit));
			}
			if (null != padPurchaseBatchNo && !padPurchaseBatchNo.equals("") && !"null".equals(padPurchaseBatchNo)) {
				map.put("padPurchaseBatchNo", padPurchaseBatchNo);
			}
			map.put("shopId",1000);  //默认查询:公司总部  的pad
			List padBaseinfo = this.padBaseinfoService.getPadBaseAndSupplyList(map);
			int count = this.padBaseinfoService.getCountByPadBaseAndSupplyInfo(map);
			
			map.put("list", padBaseinfo);
			json = ResultUtil.createSuccessResult(map);
			json = json.substring(0, json.length() - 1) + ",total:" + count + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	@ResponseBody
	  @RequestMapping({"/list"})
	  public String getPadBaseinfoList(Model model, HttpServletRequest request, HttpServletResponse response)
	  {
	    String json = "";
	    try {
	      String padNo = request.getParameter("padNo");
	      String macAddress = request.getParameter("macAddress");
	      String padStatus = request.getParameter("padStatus");
	      String useType = request.getParameter("useType");
	      String shopId = request.getParameter("shopId");
	      String supplyId = request.getParameter("supplyId");
	      String start = request.getParameter("start");
	      String limit = request.getParameter("limit");
	      Map map = new HashMap();

	      if ((padNo != null) && (!(padNo.equals(""))))
	        map.put("padNo", padNo);

	      if ((macAddress != null) && (!(macAddress.equals(""))))
	        map.put("macAddress", macAddress);

	      if ((padStatus != null) && (!(padStatus.equals(""))) && (!(padStatus.equals("-1"))))
	        map.put("padStatus", Integer.valueOf(Integer.parseInt(padStatus)));

	      if ((useType != null) && (!(useType.equals(""))) && (!(useType.equals("-1"))))
	        map.put("useType", Integer.valueOf(Integer.parseInt(useType)));

	      if ((shopId != null) && (!(shopId.equals(""))) && (!(shopId.equals("1000"))))
	        map.put("shopId", shopId);

	      if ((supplyId != null) && (!(supplyId.equals(""))))
	        map.put("supplyId", supplyId);

	      if ((start != null) && (!(start.equals(""))))
	        map.put("start", Integer.valueOf(Integer.parseInt(start)));

	      if ((limit != null) && (!(limit.equals("")))) {
	        map.put("limit", Integer.valueOf(Integer.parseInt(limit)));
	      }
	      
	      
	      List padBaseinfo = this.padBaseinfoService.getPadInfoAndPurchaseInfo(map);
			int count = this.padBaseinfoService.getCountByPadInfoAndPurchaseInfo(map);
			map.put("list", padBaseinfo);
			json = ResultUtil.createSuccessResult(map);
			json = json.substring(0, json.length() - 1) + ",total:" + count + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;

	      /*List padBaseinfo = this.padBaseinfoService.getPadBaseAndSupplyList(map);
	      int count = this.padBaseinfoService.getCountByParam(map).intValue();
	      map.put("list", padBaseinfo);
	      json = ResultUtil.createSuccessResult(map);
	      json = json.substring(0, json.length() - 1) + ",total:" + count + "}";
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    
	    return json;*/
	  }


}
