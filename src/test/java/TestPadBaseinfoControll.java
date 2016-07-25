package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;

import net.shopin.supply.util.HttpUtil;

public class TestPadBaseinfoControll {
	
	
	@org.junit.Test
	public void testSave(){
		
		Map<String,String> map=new HashMap<String,String>();
		
		map.put("sid", "5");
		map.put("padNo", "1002");
		map.put("macAddress", "192.168.0.4");
		map.put("purchaseOrderno", "20141223004");
		map.put("padStatus", "2");
		map.put("description", "已领用");
		String url = "http://localhost/supply_core";
		String result = HttpUtil.HttpPost(url, "padBaseinfo/save", map);
		System.out.println(result);
		
	}
	
	@Ignore
	@org.junit.Test
	public void testList(){
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("sid", "1");
		String url = "http://localhost/supply_core";
		String result = HttpUtil.HttpPost(url, "padBaseinfo/list", map);
		System.out.println(result);
		
	}

}
