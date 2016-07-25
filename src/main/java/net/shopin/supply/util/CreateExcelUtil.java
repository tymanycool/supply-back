package net.shopin.supply.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat;
import java.util.List;

import net.shopin.supply.domain.vo.GuideinfoVO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Title: Logistics_job
 * Description: 
 * Copyright @2015~2015
 * @author :chenqb
 * @creatDate:2015-3-9上午2:31:44
 * @version: $Reversion:$
 */
public class CreateExcelUtil {
	private final static Logger logger = LoggerFactory
	.getLogger(CreateExcelUtil.class);
	
	public static void createExcelForAuthorizeGuide(List<GuideinfoVO> guideinfoVOList, String fileName)throws RuntimeException{
		try {
			logger.info("创建导购变价权限excel开始");
			//创建一个xls文件
			HSSFWorkbook workbook=new HSSFWorkbook();
			//创建一个Sheet
			HSSFSheet sheet=workbook.createSheet("导购变价权限");
			
			//设置column宽度
			 sheet.setColumnWidth(0, 15*256);
			 sheet.setColumnWidth(1, 15*256);
			 sheet.setColumnWidth(2, 15*256);
			 sheet.setColumnWidth(3, 20*256);
			 sheet.setColumnWidth(4, 15*256);
			 sheet.setColumnWidth(5, 15*256);
			 sheet.setColumnWidth(6, 15*256);
			 sheet.setColumnWidth(7, 15*256);
			 sheet.setColumnWidth(8, 15*256);
			 sheet.setColumnWidth(9, 15*256);
			//创建 一个row
//			HSSFRow row=sheet.createRow(0);
			
//			//合并单元格
////			CellRangeAddress cellRangeAddress=new CellRangeAddress(0, 0, 0, 6);
////			sheet.addMergedRegion(cellRangeAddress);
//			 for(int i=0;i<7;i++){
//			 	HSSFCell cell=row.createCell(i);
////			 	if(i==0){
////			 		cell.setCellValue("超过48小时没有发货的订单（无发货前退货）");
////			 	}
//				//设置居中样式
//				HSSFCellStyle cellStyle=workbook.createCellStyle();
//				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//				//设置字体加粗
//				HSSFFont font=workbook.createFont();
//				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//				cellStyle.setFont(font);
//				//加边框
//				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//				//给单元格加上样式
//				cell.setCellStyle(cellStyle);
//			 }
			 
			 //导购姓名   导购号   门店   供应商编码	  供应商名称	开通时间	结束时间	操作人   操作时间	品类
			//创建标题
			 HSSFRow title=sheet.createRow(0);//第一行
			 HSSFCell nameCell=title.createCell(0);
			 nameCell.setCellValue("导购姓名 ");
			 HSSFCell guideNoCell=title.createCell(1);
			 guideNoCell.setCellValue("导购号");
			 HSSFCell shopNameCell=title.createCell(2);
			 shopNameCell.setCellValue("门店");
			 HSSFCell supplySidCell=title.createCell(3);
			 supplySidCell.setCellValue("供应商编码");
			 HSSFCell supplyNameCellCell=title.createCell(4);
			 supplyNameCellCell.setCellValue("供应商名称");
			 HSSFCell startTimeCell=title.createCell(5);
			 startTimeCell.setCellValue("开通时间");
			 HSSFCell endTimeCell=title.createCell(6);
			 endTimeCell.setCellValue("结束时间");
			 HSSFCell operatoeNameCell=title.createCell(7);
			 operatoeNameCell.setCellValue("操作人");
			 HSSFCell operatTimeCell=title.createCell(8);
			 operatTimeCell.setCellValue("操作时间");
			 HSSFCell categroysCell=title.createCell(9);
			 categroysCell.setCellValue("品类");
			 
			if(guideinfoVOList!=null&&guideinfoVOList.size()>0){
				 //创建订单信息
				int i = 0;
				for (GuideinfoVO o : guideinfoVOList) {
					HSSFRow title_=sheet.createRow(i+1);
					HSSFCell nameCell_=title_.createCell(0);
					nameCell_.setCellValue(o.getName());
					HSSFCell chestcardNumberCell_=title_.createCell(1);
					chestcardNumberCell_.setCellValue(o.getChestcardNumber());
					HSSFCell shopNameCell_=title_.createCell(2);
					shopNameCell_.setCellValue(o.getShopName());
					HSSFCell supplySidCell_=title_.createCell(3);
					supplySidCell_.setCellValue(o.getSupplyId());
					HSSFCell supplyNameCellCell_=title_.createCell(4);
					supplyNameCellCell_.setCellValue(o.getSupplyName());
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					HSSFCell startTimeCell_=title_.createCell(5);
					if(o.getStartTime()!=null){
						startTimeCell_.setCellValue(sdf.format(o.getStartTime()));
					}else{
						startTimeCell_.setCellValue("");
					}
					
					HSSFCell endTimeCell_=title_.createCell(6);
					if(o.getEndTime()!=null){
						endTimeCell_.setCellValue(sdf.format(o.getEndTime()));
					}else{
						endTimeCell_.setCellValue("");
					}
					
					HSSFCell operatoeNameCell_=title_.createCell(7);
					operatoeNameCell_.setCellValue(o.getOperatoeName());
					HSSFCell operatTimeCell_=title_.createCell(8);
					
					if(o.getOperatTime()!=null){
						operatTimeCell_.setCellValue(sdf.format(o.getOperatTime()));
					}else{
						operatTimeCell_.setCellValue("");
					}
					
					HSSFCell categroysCell_=title_.createCell(9);
					categroysCell_.setCellValue(o.getCategroys());
					i++;
				}
			}
			logger.info("文件创建Excel");
			//创建一个OutputStream
			String name = new File("").getAbsolutePath()+File.separator+"authorizeGuideExcel"+File.separator+fileName;
			File f = new File(new File("").getAbsolutePath()+File.separator+"authorizeGuideExcel");//windows是\,unix是/
			if(!f.exists()){
				f.mkdir();
			}
			logger.info("保存路径"+name);
			OutputStream out=new FileOutputStream(name);
			//写一个xls文件到本地磁盘
			workbook.write(out);
			
			out.flush();
			out.close();
			logger.info("创建Excel成功");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error occured in creating book~");
		}
	}

}
