package com.shopin.core.util.beanUtils;

import org.apache.commons.beanutils.Converter;

import com.shopin.core.util.StringUtils;


public class StringConverter implements Converter{

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (type.equals(java.lang.String.class) ) {
			if(null != value && StringUtils.isNotEmpty(value.toString())){
				return value.toString();
			}else{
				return null;
			}
		}
		return null;
	}

}
