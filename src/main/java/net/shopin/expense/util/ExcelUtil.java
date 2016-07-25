/**
 * ExcelUtil.java
 * net.shopin.pz.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   Dec 7, 2015  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.util;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <p>ClassName:ExcelUtil</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 Dec 7, 201511:14:56 AM
 */
public class ExcelUtil {
	/**
	 * syk
	 * @param response
	 * @param tableHeader
	 * @param list
	 */
	public static void createExcel(HttpServletResponse response, String[] tableHeader, JSONArray list) {
		response.setContentType("application/vnd.ms-excel");

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		DataFormat dataFormat = workbook.createDataFormat();
		//设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth((short) 20);
		//内容的样式
		Font fontOfContent = workbook.createFont();
		fontOfContent.setFontHeightInPoints((short) 12);
		CellStyle cellStyleOfContent = workbook.createCellStyle();
//		cellStyleOfContent.setFont(fontOfContent);
		cellStyleOfContent.setWrapText(true);
		cellStyleOfContent.setDataFormat(dataFormat.getFormat("###0.0"));
		
		//标题的样式
		Font font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);//自动换行
		cellStyle.setDataFormat(dataFormat.getFormat("###0.0"));
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Row row = sheet.createRow(0);
		// 设置表头
		int columns = 0;
		for (int i = 0; i < tableHeader.length; i++) {
			Cell cell = row.createCell(columns++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(tableHeader[i]);
		}
		// 填入数据
		int rowNum = 1;
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = list.getJSONObject(i);
			row = sheet.createRow(rowNum++);
			columns = 0;
			for (int j = 0; j < obj.size(); j++) {
				Cell cell = row.createCell(columns++);
				if (obj.has(j + "")) {
					cell.setCellStyle(cellStyleOfContent);
					if (null == obj.getString(j + "") || "null".equals(obj.getString(j + ""))) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(obj.getString(j + ""));
					}
				} else {
					cell.setCellValue("");
				}
			}
		}
		try {
			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void createExcelTable(HttpServletResponse response, String[] tableHeader, JSONArray list, String tableHead){
		
		response.setContentType("application/vnd.ms-excel");
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)12);
		font.setColor(IndexedColors.BLACK.getIndex());
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		DataFormat dataFormat = workbook.createDataFormat();
		cellStyle.setDataFormat(dataFormat.getFormat("###0.0"));
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直   
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);// 水平   
		Row row = sheet.createRow(0);
		int rowNum = 1;
		//设置表头
		if(tableHead != null && !"".equals(tableHead)){
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, tableHeader.length-1));
			Cell cell = row.createCell(0);
			cell.setCellValue(tableHead);
			row = sheet.createRow(1);
			rowNum = 2;
		}
		//设置列名
		int columns = 0;
		for(int i=0; i<tableHeader.length; i++){
			Cell cell = row.createCell(columns++);
			cell.setCellValue(tableHeader[i]);
		}
		//填入数据
		for(int i=0; i<list.size(); i++){
			JSONObject obj = list.getJSONObject(i);
			row = sheet.createRow(rowNum++);
			columns = 0;
			for(int j=0; j<obj.size(); j++){
				Cell cell = row.createCell(columns++);
				if(obj.has(j+"")){
					cell.setCellValue(obj.getString(j+""));
				} else {
					cell.setCellValue("");
				}
			}
		}
		
		try {
			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
public static void createExcelTableByList(HttpServletResponse response, List<String> tableHeader, JSONArray list, String tableHead){
		
		response.setContentType("application/vnd.ms-excel");
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)12);
		font.setColor(IndexedColors.BLACK.getIndex());
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		DataFormat dataFormat = workbook.createDataFormat();
		cellStyle.setDataFormat(dataFormat.getFormat("###0.0"));
		Row row = sheet.createRow(0);
		int rowNum = 1;
		//设置表头
		if(tableHead != null && !"".equals(tableHead)){
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, tableHeader.size()-1));
			Cell cell = row.createCell(0);
			cell.setCellValue(tableHead);
			row = sheet.createRow(1);
			rowNum = 2;
		}
		//设置列名
		int columns = 0;
		for(int i=0; i<tableHeader.size(); i++){
			Cell cell = row.createCell(columns++);
			cell.setCellValue(tableHeader.get(i));
		}
		//填入数据
		for(int i=0; i<list.size(); i++){
			JSONObject obj = list.getJSONObject(i);
			row = sheet.createRow(rowNum++);
			columns = 0;
			for(int j=0; j<obj.size(); j++){
				Cell cell = row.createCell(columns++);
				if(obj.has(j+"")){
					cell.setCellValue(obj.getString(j+""));
				} else {
					cell.setCellValue("");
				}
			}
		}
		
		try {
			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

