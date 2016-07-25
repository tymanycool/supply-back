/**
 * 说 明     : JSON 控制过滤器
 * author: 陆湘星
 * data  : 2011-6-20
 * email : xiangxingchina@163.com
 **/
package com.shopin.core.util.json;

import net.sf.json.util.PropertyFilter;

public class JsonPropertyFilter implements PropertyFilter {  
  
    public boolean apply(Object source, String name, Object value) {  
        // 过滤掉为null的属性  和 ""
        if (value == null || "".equals(value))  
            return true;  
        return false;  
    }  
}
