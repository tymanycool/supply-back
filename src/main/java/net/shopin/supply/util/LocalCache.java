package net.shopin.supply.util;

import java.util.HashMap;
import java.util.List;

public class LocalCache {

	public static HashMap<String,Object> cache= new HashMap();
	
	
	public static List getValue(String key){
		if(cache.get(key) == null)
			return null;
		return (List)cache.get(key);
	}
	
	public static void setValue(String k,Object v){
		cache.put(k, v);
	}
	
}
