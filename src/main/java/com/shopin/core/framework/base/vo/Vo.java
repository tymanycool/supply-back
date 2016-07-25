package com.shopin.core.framework.base.vo;

/**
 * 抽象paramsobject
 * 
 * @author wangmeng
 * 
 */
public abstract class Vo<E> {
	private E entity;

	private Page<E> page;

	public Page<E> getPage() {
		return page;
	}

	public void setPage(Page<E> page) {
		this.page = page;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

}
