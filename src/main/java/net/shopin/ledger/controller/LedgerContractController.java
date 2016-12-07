package net.shopin.ledger.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopin.core.util.DateUtils;
import com.shopin.core.util.json.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.expense.util.ExcelUtil;
import net.shopin.ledger.po.LedgerContractCourse;
import net.shopin.ledger.po.LedgerContractCustom;
import net.shopin.ledger.po.LedgerContractExample;
import net.shopin.ledger.po.LedgerContractSupplyInfo;
import net.shopin.ledger.po.LedgerContractSupplyInfoExample;
import net.shopin.ledger.service.LedgerContractService;
import net.shopin.supply.domain.vo.DifferDeductionVo;
import net.shopin.supply.util.ResultUtil;
@SuppressWarnings("restriction")
@Controller
@RequestMapping("/ledgerContract")
public class LedgerContractController extends BaseController {

	@Autowired
	LedgerContractService ledgerContractService;
	private static List<LedgerContractCustom> lists=null;
	private static Logger logger = Logger.getLogger(LedgerContractController.class);
	
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy.MM.dd"), true));    
    }   
	
	
	@ResponseBody
	@RequestMapping("/insertLedgerContractCustom")
	public String insertLedgerContractCustom(LedgerContractCustom ledgerContractCustom,HttpServletRequest request) {
		String yearOfSettlementStatus = request.getParameter("settlementStatusId0");
		if (null != yearOfSettlementStatus && !"".equals(yearOfSettlementStatus)) {
			ledgerContractCustom.setSettlementStatus(yearOfSettlementStatus + "年" + ledgerContractCustom.getSettlementStatus());
		}else{
			ledgerContractCustom.setSettlementStatus(ledgerContractCustom.getSettlementStatus());
		}
		try {
			ledgerContractService.insertLedgerContractCustom(ledgerContractCustom);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.createFailureResult("10000", e.getMessage());
		}
		
		String json = ResultUtil.createSuccessResult();
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/selectBySupplierCodeOfSupplyInfo")
	public String selectBySupplierCodeOfSupplyInfo(String supplierCode) {
		LedgerContractSupplyInfo ledgerContractSupplyInfo  = null;
		try {
			ledgerContractSupplyInfo = ledgerContractService.selectBySupplierCodeOfSupplyInfo(supplierCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = ResultUtil.createSuccessResult(ledgerContractSupplyInfo);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/selectLedgerContractCustomList")
	public String selectLedgerContractCustomList(Integer start, Integer limit, HttpServletRequest request) {
		
		List<LedgerContractCustom>  ledgerContractCustomList = null;
		List<LedgerContractCustom>  ledgerContractCustomList2 = null;
		int count = 0;
		
		
		//创建合同台账基本信息查询条件
		LedgerContractExample example1 = new LedgerContractExample();
		LedgerContractExample.Criteria criteria1 = example1.createCriteria();
		//填充合同台账基本信息查询条件
		String supplierCode  = request.getParameter("supplierCode");
		if(supplierCode != null && !"".equals(supplierCode)) {
			criteria1.andSupplierCodeEqualTo(supplierCode);//供应商编码精确查询
		}
		String brandName = request.getParameter("brandName");
		if(brandName != null && !"".equals(brandName)) {
			criteria1.andBrandNameLike("%" + brandName + "%");//品牌名称模糊查询
		}
		String category = request.getParameter("category");
		if(category != null && !"".equals(category)) {
			criteria1.andCategoryEqualTo(category);//品类精确查询
		}
		String status = request.getParameter("status");
		if (status!=null&&!"".equals(status)) {
			criteria1.andCabinetStatusEqualTo(status);
		}
		// TODO added by syk
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 对应表ledger_contract中start_data字段
			String contractStartDate = request.getParameter("contractStartDate");
			String contractStartDate2 = request.getParameter("contractStartDate2");
			if (contractStartDate != null && !"".equals(contractStartDate) && contractStartDate2 != null
					&& !"".equals(contractStartDate2)) {
				criteria1.andStartDateBetween(sdf.parse(contractStartDate), sdf.parse(contractStartDate2));
			}
			// 对应表ledger_contract中contract_deadline字段
			String contractEndDate = request.getParameter("contractEndDate");
			String contractEndDate2 = request.getParameter("contractEndDate2");
			if (contractEndDate != null && !"".equals(contractEndDate) && contractEndDate2 != null
					&& !"".equals(contractEndDate2)) {
				criteria1.andStartDateBetween(sdf.parse(contractEndDate), sdf.parse(contractEndDate2));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//创建供应商基本信息查询条件
		LedgerContractSupplyInfoExample example2 = new LedgerContractSupplyInfoExample();
		LedgerContractSupplyInfoExample.Criteria criteria2 = example2.createCriteria();
		//填充供应商基本信息查询条件
		String supplierName = request.getParameter("supplierName");
		if(supplierName != null && !"".equals(supplierName)) {
			criteria2.andSupplierNameLike("%" + supplierName + "%");//供应商名称模糊查询
		}
		try {
			//FIXME 在此查出的数据可能会过滤掉补充的条款，所以需要将可能被过滤掉的补充条款再加上,并先去掉分页
			//ledgerContractCustomList = ledgerContractService.selectLedgerContractCustomByPaginAndExample(start, limit, example1, example2);
			ledgerContractCustomList = ledgerContractService.selectLedgerContractCustomByPaginAndExample(null, null, example1, example2);
			count = ledgerContractService.selectLedgerContractCustomCountByPaginAndExample(example1, example2);
			if (null != ledgerContractCustomList) {
				ledgerContractCustomList2 = handLedgerContractLists(ledgerContractCustomList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = ResultUtil.createSuccessResult(ledgerContractCustomList2);
		json = json.substring(0, json.length()-1)+",'total':'"+count+"'}";
		lists=ledgerContractCustomList2;
		return json;
	}
	/**
	 * 处理合同台帐的补充条款
	 * @param ledgerContractCustomList
	 * @return
	 * @throws Exception 
	 */
	private List<LedgerContractCustom> handLedgerContractLists(List<LedgerContractCustom> ledgerContractCustomList) throws Exception {
		Integer id = null;
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < ledgerContractCustomList.size(); i++) {
			LedgerContractCustom obj = ledgerContractCustomList.get(i);
			ids.add(obj.getId());
		}
		List<LedgerContractCustom> results = new ArrayList<LedgerContractCustom>();
		for (int i = 0; i < ledgerContractCustomList.size(); i++) {
			LedgerContractCustom obj = ledgerContractCustomList.get(i);
			Integer parentId = obj.getParentId();
			id = obj.getId();
			if (0 == parentId || (0 != parentId && !ids.contains(parentId))) {
				List<LedgerContractCustom> tmp = null;
				if (0 == parentId) {
					results.add(obj);
					tmp = ledgerContractService.selectLedgerContractCustomListByParentId(id);
				}
				if (0 != parentId && !ids.contains(parentId)) {
					LedgerContractCustom obj2 =ledgerContractService.selectLedgerContractCustomListById(parentId);
					setCustomInfo(obj2);
					results.add(obj2);
					tmp = ledgerContractService.selectLedgerContractCustomListByParentId(parentId);
				}
				if (null != tmp) {
					for (LedgerContractCustom t : tmp) {
						setCustomInfo(t);
					}
					results.addAll(tmp);
				}
			}
		}
		return results;
	}

	private void setCustomInfo(LedgerContractCustom t) throws Exception {
		// 获取供应商基本信息
		LedgerContractSupplyInfo ledgerContractSupplyInfo = t.getLedgerContractSupplyInfo();
		// 将供应商基本信息填充到ledgerContractCustom对象中
		t.setSupplierCode(ledgerContractSupplyInfo.getSupplierCode());
		t.setSupplierName(ledgerContractSupplyInfo.getSupplierName());
		t.setLegalRepresentative(ledgerContractSupplyInfo.getLegalRepresentative());
		t.setAttorney(ledgerContractSupplyInfo.getAttorney());
		t.setMobilePhone1(ledgerContractSupplyInfo.getMobilePhone1());
		t.setMobilePhone1Name(ledgerContractSupplyInfo.getMobilePhone1Name());
		t.setMobilePhone2(ledgerContractSupplyInfo.getMobilePhone2());
		t.setMobilePhone2Name(ledgerContractSupplyInfo.getMobilePhone2Name());
		t.setFixedTelephone(ledgerContractSupplyInfo.getFixedTelephone());
		t.setFixedTelephoneName(ledgerContractSupplyInfo.getFixedTelephoneName());
		t.setBasicSigningDate(ledgerContractSupplyInfo.getBasicSigningDate());
		// 获取合同台账基本信息的主键id
		int ledgerContractId = t.getId();
		// 根据合同台账基本信息主键id获取所有的协议历程信息
		List<LedgerContractCourse> ledgerContractCourseAllInfoList = ledgerContractService
				.selectByLedgerContractIdOfCourse(ledgerContractId);
		// 将查询到的协议历程集合转换为json字符串
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String ledgerContractCourseJsonStr = gson.toJson(ledgerContractCourseAllInfoList);
		t.setCourseAllInfo(ledgerContractCourseJsonStr);
		t.setLedgerContractCourseList(ledgerContractCourseAllInfoList);
	}

	@ResponseBody
	@RequestMapping("/updateLedgerContractCustom")
	public String updateLedgerContractCustom(LedgerContractCustom ledgerContractCustom,HttpServletRequest request) {
		String yearOfSettlementStatus = request.getParameter("settlementStatusId0");
		if (null != yearOfSettlementStatus && !"".equals(yearOfSettlementStatus)) {
			ledgerContractCustom.setSettlementStatus(yearOfSettlementStatus + "年" + ledgerContractCustom.getSettlementStatus());
		} else {
			ledgerContractCustom.setSettlementStatus(ledgerContractCustom.getSettlementStatus());
		}
		try {
			logger.info("供应商"+ledgerContractCustom.getSupplierCode()+"在controller中修改台账信息传递参数为:"+JsonUtil.Object2JsonFilterNull(ledgerContractCustom));
			ledgerContractService.updateLedgerContractCustom(ledgerContractCustom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = ResultUtil.createSuccessResult();
		return json;
	}
	@ResponseBody
	@RequestMapping("/importLedgerContract")
	public String importLedgerContract(HttpServletRequest request) {
		String json = "";
		try {
			InputStream inputStream = null;
			Workbook workbook = null;
			logger.info("*************直接忽略掉标题，读内容即从第2行开始***********");
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			// 直接忽略掉标题，读内容即从第2行开始
			int startRow = 1;
			logger.info("*************parseExcel()开始***********");
			HashMap<String, List<String>> hMap = parseExcel(inputStream, workbook, items, startRow);
			logger.info("*************parseExcel()结束***********");
			this.ledgerContractService.insertOfExcelLedgerContractCustom(hMap, startRow);
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.createFailureResult("10000", e.getMessage());
		}
		return json;
	}
	//解析Excel
	public HashMap<String, List<String>> parseExcel(InputStream is, Workbook workbook, List<FileItem> items,
			int startRow) throws Exception {
		logger.info("***************进入方法:parseExcel()***************");
		HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
		for (FileItem item : items) {
			if (item.isFormField()) {
				System.out.println(item.getFieldName());
				System.out.println(item.getString());
			} else {
				logger.info("上传的文件名字：" + item.getName());
				is = item.getInputStream();
			}
		}
		// 2003,2007
		if (!is.markSupported()) {
			is = new PushbackInputStream(is, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(is)) {
			workbook = new HSSFWorkbook(is);
		} else if (POIXMLDocument.hasOOXMLHeader(is)) {
			workbook = new XSSFWorkbook(is);
		} else {
			logger.error("导入的文件格式有错误!");
			throw new Exception("导入的文件格式有错误!");
		}
		Sheet sheet = workbook.getSheetAt(0);
		Row row;
		int rowCount = sheet.getLastRowNum() + 1;// 行数
		if (rowCount <= startRow) {// 数据可能为空
			logger.error("Excel中不存在可用数据!");
			throw new Exception("Excel中不存在可用数据!");
		}
		int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();// 列数
		for (int i = startRow; i < rowCount; i++) {
			row = sheet.getRow(i);
			List<String> list = new ArrayList<String>();
			for (int j = 0; j < columnCount; j++) {
				String content = "";
				if (row.getCell(j) != null && !"".equals(row.getCell(j).toString())) {
					row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
					content = row.getCell(j).toString();
				}
				list.add(content);
			}
			// 判断空行
			if (isBlankRow(list)) {
				throw new Exception("Excel中存在空行，请删除后重新操作!");
			}
			hashMap.put("row" + i, list);
		}
		logger.info("要导入的数据为：" + hashMap.toString());
		return hashMap;
	}

	private boolean isBlankRow(List<String> list) {
		for (int j = 0; j < list.size(); j++) {
			if (!"".equals(list.get(j))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 合同台帐导出（先查找，再导出）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportExcelTable")
	public void exportExcelTableOfLedgerContract(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("合同台帐导出-条目数：" + lists.size());
		String[] tableHeader = { "结算状态", "签署标记", "在柜状态", "撤柜日期", "授权开始日", "授权结束日", "供应商编码", "供应商名称", "法人代表", "委托代理人",
				"联系方式", "基本签订日期", "品类", "品牌名称", "门店编码", "门店", "基础扣率", "清算类型", "清算阈值", "清算扣率", "商户装修手册", "面积", "商务合同版本",
				"合作方式", "考核指标", "商务签订日期", "开始日期", "结束日期", "合同截止日期", "补充协议", "综合管理费(/元/天/平米)", "内外卡手续费", "合同版本",
				"合同返回日期", " 合同开始结束日期", "合同类型", "审核状态", "备注", "是否有效", "标注"};
		JSONArray array = new JSONArray();      
		JSONObject obj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < lists.size(); i++) {
			LedgerContractCustom tmp = lists.get(i);
			obj.put("0", tmp.getSettlementStatus()+"");
			obj.put("1", tmp.getSignMark()+"");
			obj.put("2", tmp.getCabinetStatus()+"");
			if (tmp.getCabinetDate() != null) {
				obj.put("3", sdf.format(tmp.getCabinetDate()));
			} else {
				obj.put("3", "");
			}
			if (null != tmp.getAuthorizedStartDate()) {
				obj.put("4", sdf.format(tmp.getAuthorizedStartDate()));
			} else {
				obj.put("4", "");
			}
			if (null != tmp.getAuthorizedEndDate()) {
				obj.put("5", sdf.format(tmp.getAuthorizedEndDate()));
			} else {
				obj.put("5", "");
			}
			obj.put("6", tmp.getSupplierCode()+"");
			obj.put("7", tmp.getSupplierName()+"");
			obj.put("8", tmp.getLegalRepresentative()+"");
			obj.put("9", tmp.getAttorney());
			String mphone1 = (tmp.getMobilePhone1Name()==null?"":tmp.getMobilePhone1Name())+":"+ (tmp.getMobilePhone1()==null?"":tmp.getMobilePhone1());
			String mphone2 = (tmp.getMobilePhone2Name()==null?"":tmp.getMobilePhone2Name())+":"+(tmp.getMobilePhone2()==null?"":tmp.getMobilePhone2());
			String fixPhone = (tmp.getFixedTelephoneName()==null?"":tmp.getFixedTelephoneName())+":"+(tmp.getFixedTelephone()==null?"":tmp.getFixedTelephone());
			obj.put("10", (mphone1.equals(":")?"":(mphone1 + "\n")) + (mphone2.equals(":")?"":(mphone2 + "\n")) + (fixPhone.equals(":")?"":fixPhone));
			if (null != tmp.getBasicSigningDate()) {
				obj.put("11", sdf.format(tmp.getBasicSigningDate()));
			} else {
				obj.put("11", "");
			}
			obj.put("12", tmp.getCategory()+"");
			obj.put("13", tmp.getBrandName()+"");
			obj.put("14", tmp.getStoreEncoding()+"");
			obj.put("15", tmp.getStoreName()+"");
			Double deductionRate = tmp.getDeductionRate();
			obj.put("16", +deductionRate*100+"%");    //基础扣率 ：从数据库获取为0.18  ，页面展示需要 18%
			
			obj.put("17", tmp.getClearType()+"");
			obj.put("18", tmp.getClearThreshold()+"");
			obj.put("19", tmp.getClearDeduction()+"");    //清算扣率
			if (null != tmp.getDecorateRules() && !"".equals(tmp.getDecorateRules())) {
				if (!tmp.getDecorateRules()) {
					obj.put("20", "无");
				} else {
					obj.put("20", "有");
				}
			}else {
				obj.put("20", "");
			}
			obj.put("21", tmp.getArea()+"");
			obj.put("22", tmp.getContractVersion()+"");
			obj.put("23", tmp.getCooperationWay()+"");
			
			obj.put("24", tmp.getAssessmentIndicator()+"");
			if (null != tmp.getSigningDate()) {
				obj.put("25", sdf.format(tmp.getSigningDate()));
			} else {
				obj.put("25", "");
			}
			if (null != tmp.getStartDate()) {
				obj.put("26", sdf.format(tmp.getStartDate()));
			} else {
				obj.put("26", "");
			}
			if (null != tmp.getEndDate()) {
				obj.put("27", sdf.format(tmp.getEndDate()));
			} else {
				obj.put("27", "");
			}
			if (null != tmp.getContractDeadline()) {
				obj.put("28", sdf.format(tmp.getContractDeadline()));
			} else {
				obj.put("28", "");
			}
			obj.put("29", tmp.getSupplemental()+"");
			obj.put("30", tmp.getManagementFees()+"");
			obj.put("31", tmp.getCardFees()+"");
			obj.put("32", tmp.getContractVersion()+"");
			//合同返回日期   合同类型    合同开始结束日期   审核状态
			String courseAllInfo = tmp.getCourseAllInfo();
			String courseReturnDate = "";
			String courseBeginDate = "";
			String courseType = "";
			String courseStatus = "";
			if (null != courseAllInfo && !"[]".equals(courseAllInfo)) {
				JSONArray infoArr = JSONArray.fromObject(courseAllInfo);
				for (int j = 0; j < infoArr.size(); j++) {
					JSONObject course = infoArr.getJSONObject(j);
					String tmpReturnDate = course.getString("contractReturnDate");
						courseReturnDate += (tmpReturnDate != null ? tmpReturnDate : "") + "\n";
					String tmpBeginDate = course.getString("contractBeginDate") + "至" + course.getString("contractEndDate");
						courseBeginDate += (tmpBeginDate != null ? tmpBeginDate : "") + "\n";
					String tmpType = course.getString("contractType");
						courseType += (tmpType != null ? tmpType : "") + "\n";
					String tmpStatus = course.getString("contractReviewStatus");
						courseStatus += (tmpStatus != null ? tmpStatus : "") + "\n";
				}
				courseReturnDate = courseReturnDate.substring(0, courseReturnDate.length()-1);
				courseBeginDate = courseBeginDate.substring(0, courseBeginDate.length()-1);
				courseType = courseType.substring(0, courseType.length()-1);
				courseStatus = courseStatus.substring(0, courseStatus.length()-1);
			}
			obj.put("33", courseReturnDate);
			obj.put("34", courseBeginDate);
			obj.put("35", courseType);
			obj.put("36", courseStatus);
			
			obj.put("37", tmp.getMark()+"");// 备注
			if (null != tmp.getValid() && !"".equals(tmp.getValid())) {
				if (tmp.getValid()) {
					obj.put("38", "有效");
				} else {
					obj.put("38", "无效");
				}
			} else {
				obj.put("38", "");
			}
			obj.put("39", tmp.getParentId()==0?"主":"");
			
			array.add(obj);
			obj.clear();
		}
		ExcelUtil.createExcel(response, tableHeader, array);
	}
	
	/**
	 * 差异性扣率导出（同一供应商同品牌不同门店）
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/exportExcelTable1")
	public void exportExcelTableOfDiffDeduction1(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<DifferDeductionVo> ledgerContractLists = this.ledgerContractService.selectByDifferDeduction();
		String[] tableHeader = { "供应商编码", "供应商名称", "品类", "品牌名称", "门店", "扣率", "合作方式" };
		JSONArray array = new JSONArray();
		JSONObject obj1 = new JSONObject();
		for (int i = 0; i < ledgerContractLists.size(); i++) {
			obj1.put("0", ledgerContractLists.get(i).getSupplierCode());
			obj1.put("1", ledgerContractLists.get(i).getSupplierName());
			obj1.put("2", ledgerContractLists.get(i).getCategory());
			obj1.put("3", ledgerContractLists.get(i).getBrandName());
			obj1.put("4", ledgerContractLists.get(i).getStoreName());
			obj1.put("5", ledgerContractLists.get(i).getDeductionRate()+"%");
			obj1.put("6", ledgerContractLists.get(i).getCooperationWay());
			array.add(obj1);
			obj1.clear();
		}
		ExcelUtil.createExcel(response, tableHeader,array);
	}
	
	/**
	 * 差异性扣率导出（同品牌不同供应商不同门店）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportExcelTable2")
	public void exportExcelTableOfDiffDeduction2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<DifferDeductionVo> ledgerContractLists = this.ledgerContractService.selectDifferDeductionByDifferSupply();
		String[] tableHeader = { "品类", "品牌名称", "供应商编码", "供应商名称", "门店", "扣率", "合作方式" };
		JSONArray array = new JSONArray();
		JSONObject obj1 = new JSONObject();
		for (int i = 0; i < ledgerContractLists.size(); i++) {
			obj1.put("0", ledgerContractLists.get(i).getCategory());
			obj1.put("1", ledgerContractLists.get(i).getBrandName());
			obj1.put("2", ledgerContractLists.get(i).getSupplierCode());
			obj1.put("3", ledgerContractLists.get(i).getSupplierName());
			obj1.put("4", ledgerContractLists.get(i).getStoreName());
			obj1.put("5", ledgerContractLists.get(i).getDeductionRate()+"%");
			obj1.put("6", ledgerContractLists.get(i).getCooperationWay());
			array.add(obj1);
			obj1.clear();
		}
		ExcelUtil.createExcel(response, tableHeader,array);
	}
	
	/**
	 * 品牌扣率导出（在某一范围内的品牌扣率）
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/exportExcelTable3")
	public void exportExcelTableOfDeductionByRange(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String tmprangeStart = request.getParameter("rangeStart");
		String tmprangeEnd = request.getParameter("rangeEnd");
		Integer rangeStart = null;
		Integer rangeEnd = null;
		if (!"null".equals(tmprangeStart)&&!"".equals(tmprangeStart)) {
			 rangeStart =Integer.parseInt(tmprangeStart);
		}
		if (!"null".equals(tmprangeEnd)&&!"".equals(tmprangeEnd)) {
			 rangeEnd =Integer.parseInt(tmprangeEnd);
		}
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("rangeStart", rangeStart);
		map.put("rangeEnd", rangeEnd);
		List<DifferDeductionVo> result = this.ledgerContractService.selectDeductionByRange(map);
		String[] tableHeader = { "供应商编码", "供应商名称", "品类", "品牌名称", "门店", "扣率", "合作方式" };
		JSONArray array = new JSONArray();
		JSONObject obj1 = new JSONObject();
		for (int i = 0; i < result.size(); i++) {
			obj1.put("0", result.get(i).getSupplierCode());
			obj1.put("1", result.get(i).getSupplierName());
			obj1.put("2", result.get(i).getCategory());
			obj1.put("3", result.get(i).getBrandName());
			obj1.put("4", result.get(i).getStoreName());
			obj1.put("5", result.get(i).getDeductionRate()+"%");
			obj1.put("6", result.get(i).getCooperationWay());
			array.add(obj1);
			obj1.clear();
		}
		ExcelUtil.createExcel(response, tableHeader,array);
	}
	
	
	
	public static void main(String[] args) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");//小写的mm表示的是分钟  
		String dstr="2008.4.24";  
		try {
			java.util.Date date=sdf.parse(dstr);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
	}
}
