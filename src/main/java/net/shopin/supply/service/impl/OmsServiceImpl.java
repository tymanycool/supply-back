package net.shopin.supply.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.supply.domain.vo.OmsInfoVo;
import net.shopin.supply.ogg.persistence.OmsMapper;
import net.shopin.supply.service.IOmsService;
import net.shopin.supply.util.ExcelFile;

@Service
public class OmsServiceImpl implements IOmsService {

	@Autowired
	private OmsMapper omsMapper;
	
	@Override
	public List selectCashierList(Map paramMap) {
		
		return this.omsMapper.selectCashierList(paramMap);
		
	}

	@Override
	public int SupplyCashierListCount(Map paramMap) {
		
		return this.omsMapper.supplyCashierListCount(paramMap);
		
	}

	@Override
	public String cashierSelectsToExcel(HttpServletResponse response, List<OmsInfoVo> list, String type) {

		
		List<String> header = new ArrayList<String>();
		List<List<String>> data = new ArrayList<List<String>>();

		String title = "";
		if(type.equals("1")){//收银员流水查询报表
			
			title = "收银员流水查询报表";
			
			header.add("门店号");
			header.add("门店名称");
			header.add("日期");
			header.add("供应商码");
			header.add("品牌");
//			header.add("支付方式");
			header.add("流水号");
			header.add("数量");
			header.add("金额");
			header.add("设备En号");
			header.add("银行卡号");
			header.add("参考号");
			header.add("银行终端号");
			header.add("收银员登陆号");
			
//			String guideBitText = "";
			for(OmsInfoVo omsInfoVo:list){
				List<String> inlist = new ArrayList<String>();
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 
				
				inlist.add(omsInfoVo.getShopSid() == null?"":String.valueOf(omsInfoVo.getShopSid()));//门店号
				inlist.add(omsInfoVo.getShopName() == null?"":omsInfoVo.getShopName());
				inlist.add(omsInfoVo.getPayTime() == null?"":sdf.format(omsInfoVo.getPayTime()));//支付时间
				inlist.add(omsInfoVo.getSupplySid() == null?"":String.valueOf(omsInfoVo.getSupplySid()));//供应商码
				inlist.add(omsInfoVo.getBrandName() == null?"":omsInfoVo.getBrandName());//品牌名称
				/*if(omsInfoVo.getPaymentTypeSid() == 44){//旺pos支付
					guideBitText = "旺pos支付";
				}else{
					guideBitText = "";
				}
				inlist.add(guideBitText);*/
				inlist.add(omsInfoVo.getCashierNumber() == null?"":omsInfoVo.getCashierNumber());//流水号
				inlist.add(omsInfoVo.getSaleSum() == null?"":String.valueOf(omsInfoVo.getSaleSum()));//销售数量
				inlist.add(omsInfoVo.getSaleAllPrice() == null?"":omsInfoVo.getSaleAllPrice());//销售金额
				inlist.add(omsInfoVo.getDeviceEn() == null?"":omsInfoVo.getDeviceEn());//设备En号
				inlist.add(omsInfoVo.getBankNo() == null?"":omsInfoVo.getBankNo());//银行卡号
				inlist.add(omsInfoVo.getRefNo() == null?"":omsInfoVo.getRefNo());//参考号
				inlist.add(omsInfoVo.getTerminalNo() == null?"":omsInfoVo.getTerminalNo());//银行终端号
				inlist.add(omsInfoVo.getGuideNo() == null?"":omsInfoVo.getGuideNo());//收银员登陆号
				data.add(inlist);
			}
		}
		
		ExcelFile ef = new ExcelFile(title, header, data);
		try {
			OutputStream file = response.getOutputStream();
			response.reset();
			response.setContentType("APPLICATION/OCTET-STREAM"); 
			response.setHeader("Content-disposition","attachment; filename=/"+ new String(title.getBytes("gb2312"), "ISO8859-1" ) +".xls");
			ef.save(file);
			
			return "成功";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		
	}

	@Override
	public List selectCashierListForExcel(Map paramMap) {
		return this.omsMapper.selectCashierListForExcel(paramMap);
	}

	@Override
	public List selectLongShortList(Map paramMap) {
		return this.omsMapper.selectLongShortList(paramMap);
	}

	@Override
	public int SupplyLongShortListCount(Map paramMap) {
		return this.omsMapper.selectLongShortListCount(paramMap);
	}

	@Override
	public String longShortSelectsToExcel(HttpServletResponse response, List<OmsInfoVo> list, String type) {

		
		List<String> header = new ArrayList<String>();
		List<List<String>> data = new ArrayList<List<String>>();

		String title = "";
		if(type.equals("1")){//收银员流水查询报表
			
			title = "收银员长短款查询报表";
			
			header.add("收银员登陆号");
			header.add("日期");
			header.add("金额");
			header.add("银行终端号");
			
			for(OmsInfoVo omsInfoVo:list){
				List<String> inlist = new ArrayList<String>();
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 
				
				inlist.add(omsInfoVo.getGuideNo() == null?"":omsInfoVo.getGuideNo());//收银员登陆号
				inlist.add(omsInfoVo.getPayTime() == null?"":sdf.format(omsInfoVo.getPayTime()));//支付时间
				inlist.add(omsInfoVo.getSaleAllPrice() == null?"":omsInfoVo.getSaleAllPrice());//销售金额
				inlist.add(omsInfoVo.getTerminalNo() == null?"":omsInfoVo.getTerminalNo());//银行终端号
				data.add(inlist);
			}
		}
		
		ExcelFile ef = new ExcelFile(title, header, data);
		try {
			OutputStream file = response.getOutputStream();
			response.reset();
			response.setContentType("APPLICATION/OCTET-STREAM"); 
			response.setHeader("Content-disposition","attachment; filename=/"+ new String(title.getBytes("gb2312"), "ISO8859-1" ) +".xls");
			ef.save(file);
			
			return "成功";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		
	}

	@Override
	public List selectLongShortListForExcel(Map paramMap) {
		return this.omsMapper.selectLongShortListForExcel(paramMap);
	}

	@Override
	public String SupplyLongShortListTotalMoney(Map paramMap) {
		return this.omsMapper.selectLongShortListTotalMoney(paramMap);
	}

	@Override
	public String selectCashierListTotalMoney(Map paramMap) {
		return this.omsMapper.selectCashierListTotalMoney(paramMap);
	}

}
