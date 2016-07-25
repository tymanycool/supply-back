package com.shopin.core.util.beanUtils;

import org.apache.commons.beanutils.Converter;

import com.shopin.core.util.DateUtils;
import com.shopin.core.util.StringUtils;


public class DateConverter implements Converter{

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (type.equals(java.util.Date.class) ) {
			if(null != value && StringUtils.isNotEmpty(value.toString())){
				return DateUtils.formatStr2Date(value.toString(), DateUtils.DATE_PATTON_1);
			}else{
				return value;
			}
		}
		return null;
	}

}
