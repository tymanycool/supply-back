/**
 * 说 明     : 正则表达式工具类
 * author: 陆湘星
 * data  : 2011-10-20
 * email : xiangxingchina@163.com
 **/
package com.shopin.core.util.regExp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtil {
	//获取第一个数据
	public static String GetTagParameter(String data,String para)  
	   {  
	       String result="";  
	       StringBuffer tag=new StringBuffer();  
	       tag.append("<");  
	       tag.append(para);  
	       tag.append(">");  
	       tag.append("(.*?)");  
	       tag.append("</");  
	       tag.append(para);  
	       tag.append(">");  
	       Pattern pattern=Pattern.compile(tag.toString());  
	       Matcher matcher=pattern.matcher(data);  
	       if(matcher.find())  
	       {  
	           result=matcher.group(1);  
	       }  
	       return result;  
	   } 
		//获取列表数据
	   public static List<String> GetTagParameterArray(String data,String para)  
	   {  
		   List<String> tags = new ArrayList<String>();
	       StringBuffer reStr=new StringBuffer();  
	       reStr.append("<");  
	       reStr.append(para);  
	       reStr.append(">");  
	       reStr.append("(.*?)");  
	       reStr.append("</");  
	       reStr.append(para);  
	       reStr.append(">");  
	       Pattern pattern=Pattern.compile(reStr.toString());  
	       Matcher matcher=pattern.matcher(data);  
	       while(matcher.find())  
	       {  
	    	   tags.add(matcher.group(1));  
	       }  
	       return tags;  
	   }  
}
