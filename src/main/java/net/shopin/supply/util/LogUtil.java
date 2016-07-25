/**
 * Project Name:supply_core
 * File Name:PadLogUtil.java
 * Package Name:net.shopin.supply.util
 * Date:2016年5月4日
 *
*/

package net.shopin.supply.util;

import java.util.LinkedHashMap;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.shopin.supply.domain.entity.PadBaseinfo;

/**
 * Date:     2016年5月4日 下午12:58:19 <br/>
 */
public class LogUtil {
	
	
	public static String createPadLogDesc(PadBaseinfo padBaseinfo,String description){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("description", description);
		map.put("obj", padBaseinfo);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String jsonData = gson.toJson(map);
		return jsonData;
	}
}

