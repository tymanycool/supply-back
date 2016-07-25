package com.shopin.core.util;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class DataConvertUtil {
	public static <T extends Object> T convertJsonToBean(Class clazz, String json) {
		T object = null;
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(
				"yyyy-MM-dd HH:mm:ss"));
		try {
			JSONObject jsonobject = JSONObject.fromObject(json, config);
			object = (T) JSONObject.toBean(jsonobject, clazz);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public static <T> Object convertJsonToBean(Class clazz, String json, Map cmap)
	  {
	    Object object = null;
	    try
	    {
	      JSONObject jsonobject = JSONObject.fromObject(json);
	      object = JSONObject.toBean(jsonobject, clazz, cmap);
	    }
	    catch (RuntimeException e) {
	      e.printStackTrace();
	    }
	    return object;
	}
	
	public static String converBeanToJson(Object object) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(
				"yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonobject = null;
		try {
			jsonobject = JSONObject.fromObject(object, config);
			return jsonobject.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public static String converBeanListToJson(Object object) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(
				"yyyy-MM-dd HH:mm:ss"));
		try {
			JSONArray jsonobject = JSONArray.fromObject(object, config);
			return jsonobject.toString();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
