package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;

import net.shopin.supply.util.HttpUtil;

public class TestPadGuideinfoControll {
	
	@Ignore
	@org.junit.Test
	public void testSelectGuideList(){
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("shopId", "1001");
		map.put("supplyId", "100791");
		String url = "http://localhost/supply_core";
		String result = HttpUtil.HttpPost(url, "padGuideinfo/selectGuideListByParam", map);
		System.out.println(result);
	}
	
	@Ignore
	@org.junit.Test
	public void testSave(){
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("sid", "");
		map.put("guideNo", "1005");
		map.put("spell", "lili");
		map.put("mobile", "15810623345");
		map.put("email", "158eere@163.com");
		map.put("guideCard", "1306447632588723554");
		map.put("guideBit", "1");
//		map.put("receivePadBit", "0");
		String url = "http://localhost/supply_core";
		String result = HttpUtil.HttpPost(url, "padGuideinfo/save", map);
		System.out.println(result);
		
	}
	
	@Ignore
	@org.junit.Test
	public void testUpdateValidBitStatus(){
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("sid", "2");
		String url = "http://localhost/supply_core";
		String result = HttpUtil.HttpPost(url, "padGuideinfo/updateValidBitStatus", map);
		System.out.println(result);
		
	}
	
	
	@org.junit.Test
	public void testList(){
		
		Map<String,String> map=new HashMap<String,String>();
		String url = "http://localhost/supply_core";
		String result = HttpUtil.HttpPost(url, "padGuideinfo/list", map);
		System.out.println(result);
		
	}

}
