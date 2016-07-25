package com.shopin.core.util.beanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils{
	
	static{
		// 注册日期转换器，支持java.util.Date
		ConvertUtils.register(new DateConverter(),Date.class);
		// 注册数字转换器，防止默认把null设为0
		ConvertUtils.register(new NumberConverter(),java.lang.Integer.class);
		// 注册字符串转换器，防止"null"字符串
		ConvertUtils.register(new StringConverter(),java.lang.String.class);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void populate(Object bean, Map properties) throws IllegalAccessException, InvocationTargetException{
		org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
	}
	
	
	public static void setProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException{
		org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
	}
}
