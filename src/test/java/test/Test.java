package test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.util.DaemonThreadFactory;
import com.shopin.core.util.DateUtils;

import net.shopin.supply.util.HttpUtil;

public class Test {
	
	@org.junit.Test
	public void testSelectGuideList(){
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("shopId", "1001");
		map.put("supplyId", "100791");
		String url = "http://localhost/supply_core";
		String result = HttpUtil.HttpPost(url, "padMonitor/selectGuideListByParam", map);
		System.out.println(result);
		
	}
	@org.junit.Test
	public void testCompareTo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(new Date()).substring(8, 10));
	}
	
	@org.junit.Test
	public void testRemoveZero(){
		String supplySid = "0000000000100001";
		System.out.println(supplySid);
		for(int j=0; j<supplySid.length(); j++){
			Character c = supplySid.charAt(j);
			if(c != '0'){
				supplySid = supplySid.substring(j);
				break;
			}
		}
		System.out.println(supplySid);
	}
	
	@org.junit.Test
	public void testBigDecimal(){
		double i=2, j=2.1, k=2.5, m=2.9;
		   System.out.println("舍掉小数取整:Math.floor(2)=" + (int)Math.floor(i));
		   System.out.println("舍掉小数取整:Math.floor(2.1)=" + (int)Math.floor(j));
		   System.out.println("舍掉小数取整:Math.floor(2.5)=" + (int)Math.floor(k));
		   System.out.println("舍掉小数取整:Math.floor(2.9)=" + (int)Math.floor(m));
		                                       
		   /* 这段被注释的代码不能正确的实现四舍五入取整
		   System.out.println("四舍五入取整:Math.rint(2)=" + (int)Math.rint(i));
		   System.out.println("四舍五入取整:Math.rint(2.1)=" + (int)Math.rint(j));
		   System.out.println("四舍五入取整:Math.rint(2.5)=" + (int)Math.rint(k));
		   System.out.println("四舍五入取整:Math.rint(2.9)=" + (int)Math.rint(m));
		  
		   System.out.println("四舍五入取整:(2)=" + new DecimalFormat("0").format(i));
		   System.out.println("四舍五入取整:(2.1)=" + new DecimalFormat("0").format(i));
		   System.out.println("四舍五入取整:(2.5)=" + new DecimalFormat("0").format(i));
		   System.out.println("四舍五入取整:(2.9)=" + new DecimalFormat("0").format(i));
		   */
		  
		   System.out.println("四舍五入取整:(2)=" + new BigDecimal("2").setScale(0, BigDecimal.ROUND_HALF_UP));
		   System.out.println("四舍五入取整:(2.1)=" + new BigDecimal("2.1").setScale(0, BigDecimal.ROUND_HALF_UP));
		   System.out.println("四舍五入取整:(2.5)=" + new BigDecimal("2.5").setScale(0, BigDecimal.ROUND_HALF_UP));
		   System.out.println("四舍五入取整:(2.9)=" + new BigDecimal("2.9").setScale(0, BigDecimal.ROUND_HALF_UP));

		   System.out.println("凑整:Math.ceil(2)=" + (int)Math.ceil(i));
		   System.out.println("凑整:Math.ceil(2.1)=" + (int)Math.ceil(j));
		   System.out.println("凑整:Math.ceil(2.5)=" + (int)Math.ceil(k));
		   System.out.println("凑整:Math.ceil(2.9)=" + (int)Math.ceil(m));

		   System.out.println("舍掉小数取整:Math.floor(-2)=" + (int)Math.floor(-i));
		   System.out.println("舍掉小数取整:Math.floor(-2.1)=" + (int)Math.floor(-j));
		   System.out.println("舍掉小数取整:Math.floor(-2.5)=" + (int)Math.floor(-k));
		   System.out.println("舍掉小数取整:Math.floor(-2.9)=" + (int)Math.floor(-m));
		  
		   System.out.println("四舍五入取整:(-2)=" + new BigDecimal("-2").setScale(0, BigDecimal.ROUND_HALF_UP));
		   System.out.println("四舍五入取整:(-2.1)=" + new BigDecimal("-2.1").setScale(0, BigDecimal.ROUND_HALF_UP));
		   System.out.println("四舍五入取整:(-2.5)=" + new BigDecimal("-2.5").setScale(0, BigDecimal.ROUND_HALF_UP));
		   System.out.println("四舍五入取整:(-2.9)=" + new BigDecimal("-2.9").setScale(0, BigDecimal.ROUND_HALF_UP));

		   System.out.println("凑整:Math.ceil(-2)=" + (int)Math.ceil(-i));
		   System.out.println("凑整:Math.ceil(-2.1)=" + (int)Math.ceil(-j));
		   System.out.println("凑整:Math.ceil(-2.5)=" + (int)Math.ceil(-k));
		   System.out.println("凑整:Math.ceil(-2.9)=" + (int)Math.ceil(-m)); 
	}
	@org.junit.Test
	public void testMath(){
		Integer result = Integer.valueOf(" ");
		System.out.println("看这里：=====》"+result);
	}
	
	
	@org.junit.Test
	public void testupdateOrderDeatilByParamForPrint(){
		Map<String ,String> map=new HashMap<String ,String>();
		map.put("fromSystem", "print");
		map.put("detailNos", "20161201432911-1");
		String url = "http://172.16.200.61:31081/wmsservice";
		String result = HttpUtil.HttpPost(url, "orderDetail/updateOrderDeatilByParamForPrint.json", map);
		System.out.println(result);
	}
	
	@org.junit.Test
	public void printDeliveryForWeb(){
		String json = "";
		Map<String ,String> paramMap = new HashMap<String, String>();
		paramMap.put("deliveryComNo", "400023");
		paramMap.put("deliveryComName", "顺丰快递");
		paramMap.put("orderNoList", "20161201432911,");
		paramMap.put("courierNoList", "972626304840");
		paramMap.put("expressName", "");
		paramMap.put("userName", "songchaoshuai");
		String method = "order/printDeliveryForWeb.json";
		String url = "http://172.16.200.61:31081/wmsservice";
		json = HttpUtil.HttpPost(url, method, paramMap);
		System.out.println(json);
	}
	
	@org.junit.Test
	public void TestUpdateOrderPrintTime(){
		String json = "";
		Map<String ,String> paramMap = new HashMap<String, String>();
		paramMap.put("orderNo", "20161201432911");
		String method = "order/updateOrderPrintTime.json";
		String url = "http://172.16.200.61:31081/wmsservice";
		json = HttpUtil.HttpPost(url, method, paramMap);
		System.out.println(json);
	}
	

}
