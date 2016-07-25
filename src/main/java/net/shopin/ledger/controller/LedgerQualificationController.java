package net.shopin.ledger.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.expense.util.ExcelUtil;
import net.shopin.ledger.po.LedgerQualificationCustom;
import net.shopin.ledger.po.LedgerQualificationExample;
import net.shopin.ledger.po.LedgerQualificationSupplyInfo;
import net.shopin.ledger.po.LedgerQualificationSupplyInfoExample;
import net.shopin.ledger.service.LedgerQualificationService;
import net.shopin.supply.domain.vo.ActingLevelVO;
import net.shopin.supply.domain.vo.QualiErrorInfoVO;
import net.shopin.supply.util.ResultUtil;
//import test.ExportExcelUtils;


@Controller
@RequestMapping("/ledgerQualification")
public class LedgerQualificationController extends BaseController {

	@Autowired
	LedgerQualificationService ledgerQualificationService;
	private static Logger logger = Logger.getLogger(LedgerQualificationController.class);
	private static List<LedgerQualificationCustom> lists=null;
	@ResponseBody
	@RequestMapping("/insertLedgerQualificationCustom")
	public String insertLedgerQualificationCustom(LedgerQualificationCustom ledgerQualificationCustom) {
		try {
			ledgerQualificationService.insertLedgerQualificationCustom(ledgerQualificationCustom);
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
		LedgerQualificationSupplyInfo  ledgerQualificationSupplyInfo = null;
		try {
			ledgerQualificationSupplyInfo = ledgerQualificationService.selectBySupplierCodeOfSupplyInfo(supplierCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = ResultUtil.createSuccessResult(ledgerQualificationSupplyInfo);
		return json;
	}
	@ResponseBody
	@RequestMapping("/selectLedgerQualificationCustomList")
	public String selectLedgerQualificationCustomList(Integer start, Integer limit, HttpServletRequest request) {
		List<LedgerQualificationCustom> ledgerQualificationCustomList = null;
		List<LedgerQualificationCustom> ledgerQualificationCustomList2 = null;
		int count = 0;
		
		//构造资质台账基本信息查询条件
		LedgerQualificationExample example1 = new LedgerQualificationExample();
		LedgerQualificationExample.Criteria criteria1 = example1.createCriteria();
		//填充资质台账基本信息查询条件
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
		// by syk
		// 对应表ledger_qulification中end_date字段
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = request.getParameter("endDate");
		if (endDate != null && !"".equals(endDate)) {
			try {
				criteria1.andEndDateEqualTo(sdf.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//按是否在柜查询
		String status = request.getParameter("status");
		if (status != null && !"".equals(status)) {
			criteria1.andStatusEqualTo(status);
		}
		
		//构造供应商基本信息查询条件
		LedgerQualificationSupplyInfoExample example2 = new LedgerQualificationSupplyInfoExample();
		LedgerQualificationSupplyInfoExample.Criteria criteria2 = example2.createCriteria();
		//填充供应商基本信息查询条件
		String supplierName = request.getParameter("supplierName");
		if(supplierName != null && !"".equals(supplierName)) {
			criteria2.andSupplierNameLike("%" + supplierName + "%");//供应商名称模糊查询
		}
		
		
		
		try {
			ledgerQualificationCustomList = ledgerQualificationService.selectLedgerQualificationCustomByPagingAndExample(start, limit, example1, example2, null);
			count = ledgerQualificationService.selectLedgerQualificationCustomCountByPagingAndExample(example1, example2);
			ledgerQualificationCustomList2 = ledgerQualificationService.selectLedgerQualificationCustomByPagingAndExample(null, null, example1, example2, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = ResultUtil.createSuccessResult(ledgerQualificationCustomList);
		json = json.substring(0, json.length()-1)+",'total':'"+count+"'}";
		lists = ledgerQualificationCustomList2;
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateLedgerQualificationCustom")
	public String updateLedgerQualificationCustom(LedgerQualificationCustom ledgerQualificationCustom) {
		try {
			ledgerQualificationService.updateLedgerQualificationCustom(ledgerQualificationCustom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = ResultUtil.createSuccessResult();
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateLedgerQualificationOfReviewByPrimaryKey")
	public String updateLedgerQualificationOfReviewByPrimaryKey(String approvalAllInfoJSONStr) {
		try {
			ledgerQualificationService.updateLedgerQualificationOfReviewByPrimaryKey(approvalAllInfoJSONStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = ResultUtil.createSuccessResult();
		return json;
	}
	
	/**
	 * 导入资质台帐
	 * @param test
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/importLedgerQualification")
	public String importLedgerQualification(String str, HttpServletRequest request) {
		String json = "";
		try {
			InputStream inputStream = null;
			Workbook workbook = null;
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			// 直接忽略掉标题，读内容即从第3行开始
			int startRow = 2;
			HashMap<String, List<String>> hMap = parseExcel(inputStream, workbook, items, startRow);
			this.ledgerQualificationService.insertExcelOfLedgerQualificationCustom(hMap, startRow);
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.createFailureResult("10000", e.getMessage());
		}
		return json;
	}
	
	//解析Excel
	private HashMap<String, List<String>> parseExcel(InputStream is, Workbook workbook, List<FileItem> items,
			int startRow) throws Exception {
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
	 * 因资质不能结算明细 的导出
	 * 因资质问题不能结算明细查询，有下列情况之一过期即为不能结算:
		结束日期（注册证的）-end_date
		工作联系单截止日期-expiration_date
		代理日期（1...6）
		质检报告(只判断有质检的是否过期，无质检的需要人工判断)
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/exportExcelTable1")
	public void exportExcelTableOfQualiError(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("--------------------因资质不能结算明细 的导出controller开始-------------------------");
		Date today = new Date();
		List<QualiErrorInfoVO> results = this.ledgerQualificationService.selectLedgerQualificationCustomForQualiError(today);
		String[] tableHeader = { "供应商编码", "供应商名称", "品类", "品牌名称", "备注"};
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < results.size(); i++) {
			obj.put("0", results.get(i).getSupplierCode());
			obj.put("1", results.get(i).getSupplierName());
			obj.put("2", results.get(i).getCategory());
			obj.put("3", results.get(i).getBrandName());
			obj.put("4", "");//手动填写
			array.add(obj);
			obj.clear();
		}
		ExcelUtil.createExcel(response, tableHeader, array);
	}
	
	/**
	 * 资质台帐明细表（先查询，再导出）
	 * 导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportExcelTable")
	public void exportExcelTableOfLedgerQualification(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("--------------------资质台帐明细导出开始-------------------------");
		String[] tableHeader = { "工作联系单截止日期", "状态", "联系电话", "品牌级别", "供应商编码", "供应商名称", "营业执照", "国税登记证", "地税登记证", "一般纳税人",
				"品类", "品牌名称", "注册证号码", "核定类别", "结束日期", "申请日期", "个人商标持有人姓名", "持有人身份证", "单店授权", "代理级别", "一级代理日期",
				"一级代理日期批注", "二级代理日期", "一级代理批注", "三级代理日期", "三级代理批注", "四级代理日期", "四级代理批注", "五级代理日期", "五级代理批注", "六级代理日期",
				"六级代理批注", "质检类型", "质检有效期", "报关单", "问题汇总描述", "初审日期", "初审人", "信息维护日期", "信息维护人", "复审日期", "复审人", "备注",
				"是否有效"};//TODO
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < lists.size(); i++) {
			LedgerQualificationCustom tmpQuali = lists.get(i);
			if (tmpQuali.getExpirationDate() != null) {
				obj.put("0", sdf.format(tmpQuali.getExpirationDate()));
			} else {
				obj.put("0", "");
			}
			obj.put("1", tmpQuali.getStatus());
			String mphone1 = (tmpQuali.getMobilePhone1Name()==null?"":tmpQuali.getMobilePhone1Name())+":"+ (tmpQuali.getMobilePhone1()==null?"":tmpQuali.getMobilePhone1());
			String mphone2 = (tmpQuali.getMobilePhone2Name()==null?"":tmpQuali.getMobilePhone2Name())+":"+(tmpQuali.getMobilePhone2()==null?"":tmpQuali.getMobilePhone2());
			String fixPhone = (tmpQuali.getFixedTelephoneName()==null?"":tmpQuali.getFixedTelephoneName())+":"+(tmpQuali.getFixedTelephone()==null?"":tmpQuali.getFixedTelephone());
			obj.put("2", (mphone1.equals(":")?"":(mphone1 + "\n")) + (mphone2.equals(":")?"":(mphone2 + "\n")) + (fixPhone.equals(":")?"":fixPhone));
			obj.put("3", tmpQuali.getBrandLevel());
			obj.put("4", tmpQuali.getSupplierCode());
			obj.put("5", tmpQuali.getSupplierName());
			obj.put("6", tmpQuali.getBusinessLicense());
			Boolean ntr = tmpQuali.getNationalTaxRegistration();
			if (ntr != null) {
				if (true == ntr) {
					obj.put("7", "有");
				} else if (false == ntr) {
					obj.put("7", "无");
				}
			} else if (ntr == null) {
				obj.put("7", "");
			}
			Boolean ltr = tmpQuali.getLandTaxRegistration();
			if (ltr != null) {
				if (true == ltr) {
					obj.put("8", "有");
				} else if (false == ltr) {
					obj.put("8", "无");
				}
			} else if (ltr == null) {
				obj.put("8", "");
			}
			obj.put("9", tmpQuali.getGeneralTaxpayer());
			obj.put("10", tmpQuali.getCategory());
			obj.put("11", tmpQuali.getBrandName());
			obj.put("12", tmpQuali.getRegistrationNumber());
			obj.put("13", tmpQuali.getApprovedCategory());
			if (tmpQuali.getEndDate() != null) {
				obj.put("14", sdf.format(tmpQuali.getEndDate()));
			} else {
				obj.put("14", "");
			}
			if (tmpQuali.getApplicationDate() != null) {
				obj.put("15", sdf.format(tmpQuali.getApplicationDate()));
			} else {
				obj.put("15", "");
			}
			obj.put("16", tmpQuali.getTrademarkHolderName());
			obj.put("17", tmpQuali.getCertificateNumber());
			obj.put("18", tmpQuali.getSingleStoreAuthorization());
			obj.put("19", tmpQuali.getActingLevel());
			if (tmpQuali.getFirstProxyDate() != null) {
				obj.put("20", sdf.format(tmpQuali.getFirstProxyDate()));
			} else {
				obj.put("20", "");
			}
			obj.put("21", tmpQuali.getFirstProxyAnnotation()+"");
			if (tmpQuali.getSecondProxyDate() != null) {
				obj.put("22", sdf.format(tmpQuali.getSecondProxyDate()));
			} else {
				obj.put("22", "");
			}
			obj.put("23", tmpQuali.getSecondProxyAnnotation()+"");
			if (tmpQuali.getThirdProxyDate() != null) {
				obj.put("24", sdf.format(tmpQuali.getThirdProxyDate()));
			} else {
				obj.put("24", "");
			}
			obj.put("25", tmpQuali.getThirdProxyAnnotation()+"");
			if (tmpQuali.getForthProxyDate() != null) {
				obj.put("26", sdf.format(tmpQuali.getForthProxyDate()));
			} else {
				obj.put("26", "");
			}
			obj.put("27", tmpQuali.getForthProxyAnnotation()+"");
			if (tmpQuali.getFifthProxyDate() != null) {
				obj.put("28", sdf.format(tmpQuali.getFifthProxyDate()));
			} else {
				obj.put("28", "");
			}
			obj.put("29", tmpQuali.getFifthProxyAnnotation()+"");
			if (tmpQuali.getSixthProxyDate() != null) {
				obj.put("30", sdf.format(tmpQuali.getSixthProxyDate()));
			} else {
				obj.put("30", "");
			}
			obj.put("31", tmpQuali.getSixthProxyAnnotation()+"");
			//FIXME
			// 质检日期
			String inspectionAllInfo = tmpQuali.getInspectionAllInfo();
			String insType = "";
			String insDate = "";
			if (null != inspectionAllInfo && !"[]".equals(inspectionAllInfo)) {
				JSONArray infoArr = JSONArray.fromObject(inspectionAllInfo);
				for (int m = 0; m < infoArr.size(); m++) {
					String tmpType = infoArr.getJSONObject(m).getString("inspectionType");
					insType += (tmpType != null ? tmpType : "") + "\n";
					if ("有质检（有期限）".equals(tmpType)) {
						String tmpDate = infoArr.getJSONObject(m).getString("inspectionDate");
						insDate += tmpDate + "\n";
					} else {
						insDate += "\n";
					}
				}
				insType = insType.substring(0, insType.length() - 1);
				insDate = insDate.substring(0, insDate.length() - 1);
			}
			obj.put("32", insType);// 质检类型
			obj.put("33", insDate);// 质检有效期
			obj.put("34", tmpQuali.getDeclaration());
			obj.put("35", tmpQuali.getDescriptionProblem());
			// 初审人、初审日期不存在，置为空 TODO
			obj.put("36", "");
			obj.put("37", "");
			if (tmpQuali.getInformationMaintenanceDate() != null) {
				obj.put("38", sdf.format(tmpQuali.getInformationMaintenanceDate()));
			} else {
				obj.put("38", "");
			}
			obj.put("39", tmpQuali.getInformationMaintenanceName()+"");
			if (tmpQuali.getReviewDate() != null) {
				obj.put("40", sdf.format(tmpQuali.getReviewDate()));
			} else {
				obj.put("40", "");
			}
			obj.put("41", tmpQuali.getReviewName()+"");
			obj.put("42", tmpQuali.getMark()+"");
			if (null != tmpQuali.getValid() && !"".equals(tmpQuali.getValid())) {
				if (tmpQuali.getValid()) {
					obj.put("43", "有效");
				} else {
					obj.put("43", "无效");
				}
			} else {
				obj.put("43", "");
			}
			array.add(obj);
			obj.clear();
		}
		lists = null;
		ExcelUtil.createExcel(response, tableHeader, array);
	}
	/**
	 * 代理商级别表导出(在柜的)
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/exportExcelTable2")
	public void exportExcelTableByActingLevel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String[] targetCategory = { "女装", "内衣", "儿童", "毛纺", "服饰", "运动", "鞋", "户外", "皮具", "休闲", "男装", "羽绒"};
		String[] headers = { "供应商编码", "供应商名称", "品类", "品牌名称", "代理级别" };
		HSSFWorkbook workbook = new HSSFWorkbook();
		List<List<ActingLevelVO>> results = this.ledgerQualificationService.selectLedgerQualificationCustomByActingLevel();
		//只查在柜的
		for (int i = 0; i < results.size(); i++) {
			exportExcelToMutiSheet(workbook, i, targetCategory[i], headers, results.get(i), response);
		}
		try {
			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportExcelToMutiSheet(HSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers,
			List<ActingLevelVO> lists, HttpServletResponse response) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// workbook.setSheetName(sheetNum,sheetTitle,HSSFWorkbook.ENCODING_UTF_16);
		// FIXME
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(true);
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell((short) i);
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16); FIXME
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text.toString());
		}
		// 遍历集合数据，产生数据行
		if (lists != null) {
			int index = 1;
			for (int i = 0; i < lists.size(); i++) {
				row = sheet.createRow(index);
				int cellIndex = 0;

				ActingLevelVO actingLevelVO = lists.get(i);
				JSONArray array = new JSONArray();
				array.add(actingLevelVO.getSupplierCode());
				array.add(actingLevelVO.getSupplierName());
				array.add(actingLevelVO.getCategory());
				array.add(actingLevelVO.getBrandName());
				array.add(actingLevelVO.getActingLevel());
				for (int k = 0; k < 5; k++) {
					HSSFCell cell = row.createCell((short) cellIndex);
					// cell.setEncoding(HSSFCell.ENCODING_UTF_16);FIXME
					cell.setCellValue(array.get(cellIndex).toString());
					cellIndex++;
				}
				index++;
			}
		}
	}
}
