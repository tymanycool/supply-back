package com.shopin.core.framework.base.service.impl;

import java.util.List;
import java.util.Map;

import com.shopin.core.constants.ErrorCodeConstants;
import com.shopin.core.constants.ErrorCodeConstants.ErrorCode;
import com.shopin.core.framework.base.persistence.BaseMapper;
import com.shopin.core.framework.base.service.IBaseService;
import com.shopin.core.framework.base.vo.Page;
import com.shopin.core.framework.exception.ShopinException;

/**
 * 说明:
 * @author guansq
 * @date 2013-5-29 下午03:48:08
 * @modify 
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	private BaseMapper<T> mapper = null;
	
	public void init(BaseMapper<T> mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public Integer insert(T entity) {
		// TODO Auto-generated method stub
		return this.mapper.insert(entity);
	}

	@Override
	public Integer update(T entity) {
		// TODO Auto-generated method stub
		return this.mapper.update(entity);
	}

	@Override
	public T get(Long sid) throws ShopinException{
		// TODO Auto-generated method stub
		T entity = this.mapper.get(sid);
		return entity;
	}

	@Override
	public List<T> selectListByParam(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<T> list = this.mapper.selectListByParam(paramMap);
		return list;
	}

	@Override
	public Page<T> selectPageByParam(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		Page<T> page = new Page<T>();
		List<T> list = this.mapper.selectPageListByParam(paramMap);
		page.setList(list);
		page.setTotalCount(this.mapper.getCountByParam(paramMap));
		page.setPageNumber(Integer.valueOf(paramMap.get("pageNumber").toString()));
		page.setFetchSize(Integer.valueOf(paramMap.get("fetchSize").toString()));
		page.setPageCount(list.size());
		return page;
	}
}
