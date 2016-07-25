package com.shopin.core.framework.base.persistence;

import java.util.List;
import java.util.Map;

/**
 * 说明:
 * @author guansq
 * @date 2013-5-29 下午03:41:09
 * @modify 
 */
public interface BaseMapper<T> {
	public Integer insert(T entity);
	
	public Integer update(T entity);
	
	public T get(Long sid);
	
	public List<T> selectListByParam(Map<String, Object> paramMap);
	
	public List<T> selectPageListByParam(Map<String, Object> paramMap);
	
	public Integer getCountByParam(Map<String, Object> paramMap);
	
	public Integer delete(Long sid);
}
