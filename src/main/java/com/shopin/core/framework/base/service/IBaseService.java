package com.shopin.core.framework.base.service;

import java.util.List;
import java.util.Map;

import com.shopin.core.framework.base.vo.Page;
import com.shopin.core.framework.exception.ShopinException;

/**
 * 说明:
 * @author guansq
 * @date 2013-5-29 下午03:44:56
 * @modify 
 */
public interface IBaseService<T> {
	public Integer insert(T entity);
	
	public Integer update(T entity);
	
	public T get(Long sid) throws ShopinException;
	
	public List<T> selectListByParam(Map<String, Object> paramMap) throws ShopinException;
	
	public Page<T> selectPageByParam(Map<String, Object> paramMap) throws ShopinException;
	
	public Integer save(T entity);
}
