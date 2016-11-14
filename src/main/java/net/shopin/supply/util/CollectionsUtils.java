package net.shopin.supply.util;

import java.util.Iterator;
import java.util.Map;

import com.beust.jcommander.internal.Maps;

import net.sf.cglib.beans.BeanMap;

public class CollectionsUtils {

	/**
	 * Bean转Map工具类过滤掉bean中空属性
	 * @param <T>
	 */
	public static <T> Map<String,Object> BeanToMapFilterNull(T bean){
		Map<String,Object> map = Maps.newHashMap();
		
		if(bean!=null){
			BeanMap beanMap = BeanMap.create(bean);
			
			for(Object key:beanMap.keySet()){
				
				if(beanMap.get(key)==null||"".equals(beanMap.get(key))){
					continue;
				}
				
				map.put(key+"", beanMap.get(key));
			}
		}
		return map;
	}
	
	/**
	 * 过滤掉bean中空属性
	 * @param <T>
	 */
	public static <T> Map<String,Object> BeanToMap(T bean){
		Map<String,Object> map = Maps.newHashMap();
		
		if(bean!=null){
			BeanMap beanMap = BeanMap.create(bean);
			
			for(Object key:beanMap.keySet()){
				map.put(key+"", beanMap.get(key));
			}
		}
		return map;
	}
	
	
	/**
	 * Map遍历删除某个key值
	 */
	public static Map<String,Object> deleteFromMap(String key,Map<String,Object> map){
		if(key==null||"".equals(key)||map==null||map.size()==0){
			return map; 
		}
		
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,Object> entry = it.next();
			String currentKey = entry.getKey();
			if(currentKey.equals(key)){
				it.remove();
			}
		}
		return map; 
	}
	
	//过滤掉null值
	public static Map<String,Object> putFilterNull(String key,Object obj,Map<String,Object> map){
		if(key==null||"".equals(key)||obj==null){
			return map;
		}
		map.put(key, obj);
		return map;
	}
	
	
	/**
	 *Map 更新key值(只更新key,value不变) 
	 */
	public static Map<String,Object> updateKeyFromMap(String oldKey,String newKey,Map<String,Object> map){
		
		if(oldKey==null||"".equals(oldKey)||map.size()==0
				||map==null||newKey==null||"".equals(newKey)){
			return map;
		}
		
		if(!map.containsKey(oldKey)){
			return map;
		}
		
		Object obj = map.get(oldKey);
	    map = CollectionsUtils.deleteFromMap(oldKey, map);
		map.put(newKey, obj);
		
		return map;
	}
	
	
	
	public static void main(String[] args) {
		
//		testBeanToMapFilterNull();
//		testDeleteFromMap();
		testUpdateFromMap();
	}
	
	private static void testUpdateFromMap(){
		Map<String,Object> map = Maps.newHashMap();
		map.put("key1", "value1");
		map.put("key2", "value2");
		
		map = CollectionsUtils.updateKeyFromMap("key2", "key3", map);
		
		
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key  = (String) it.next();
			System.out.println("key="+key+"value="+map.get(key));
		}
		
	}
	
	//测试遍历删除map某个key值
	@SuppressWarnings("unused")
	private static void testDeleteFromMap(){
		Map<String,Object> map = Maps.newHashMap();
//		map.put("key1", "value1");
//		map.put("key2", "value2");
//		map.put("key3", "value3");
//		map.put("key4", "value4");
		
		map = CollectionsUtils.deleteFromMap("key2", map);
		
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key  = (String) it.next();
			System.out.println("key="+key+"value="+map.get(key));
		}
		
	}
	
	//测试bean转map
	@SuppressWarnings({ "unused", "rawtypes" })
	private static void testBeanToMapFilterNull(){
		
	}
}
