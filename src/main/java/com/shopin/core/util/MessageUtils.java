package com.shopin.core.util;

/**
 * 消息处理工具类.
 * <p>
 * 创建日期：2010-07-01<br>
 * 创建人：Xiyt<br>
 * 修改日期：<br>
 * 修改人：<br>
 * 修改内容：<br>
 * 
 * @author Xiyt
 * @version 1.0
 */
public class MessageUtils {
	public static String setParamMessage(String paramMsg, String[] values){
		if(StringUtils.isEmpty(paramMsg) || null == values || values.length < 1){
			return "参数错误！";
		}
		for(int i=0;i<values.length;i++){
			paramMsg = paramMsg.replaceFirst("\\{"+i+"\\}", values[i]);
		}
		return paramMsg;
	}
}
