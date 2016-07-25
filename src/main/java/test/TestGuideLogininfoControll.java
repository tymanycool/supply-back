package test;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import net.shopin.supply.util.HttpUtil;

/**
 * 
 * ClassName: TestGuideLogininfoControll 
 * @Description: TODO
 * @author zhangqing
 * @date 2015-3-25
 */
public class TestGuideLogininfoControll {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
//	@Ignore
	@Test
	public void loginToPad(){
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		//导购
//		map.put("username", "S131156");
//		map.put("password", "1156");
//		map.put("macAddress", "");
		map.put("username", "s021070448");
		map.put("password", "0448");
		map.put("macAddress", "");
		
		//主管
//		map.put("username", "S0111461");
//		map.put("password", "1461");
//		map.put("macAddress", "");
//		map.put("username", "S1005");
//		map.put("password", "111111");
//		map.put("macAddress", "");
//		http://192.168.200.118:8082/supply/guideLogininfo/loginToPad.json
		String url = "http://172.16.200.68:8082/supply/";
//		String url = "http://192.168.200.118:8082/supply/";
//		String url = "http://localhost/supply_core/";
		String result = HttpUtil.HttpPost(url, "guideLogininfo/loginToPad", map);
		System.out.println(result);
//		logger.info(result);
	}
	
	@Ignore
//	@Test
	public void updatePasswordToPad(){
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		//主管
		map.put("username", "S1001");
		map.put("password", "zhang123457");
		map.put("sid", "275");
		String url = "http://localhost/supply/";
		String result = HttpUtil.HttpPost(url, "guideLogininfo/updatePasswordToPad", map);
		logger.info(result);
	}

}
