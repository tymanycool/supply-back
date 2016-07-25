/**
 * DateUtil.java
 * net.shopin.pz.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0   2015年9月5日  	 wangxiaoming
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package net.shopin.expense.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;



/**
 * <p>ClassName:DateUtil</p>
 * <p>Description:	</p>
 * <p>Company: www.shopin.net</p>
 * @author   wangxiaoming
 * @version  1.0
 * @Date	 2015年9月5日下午12:13:39
 */
public class DataUtil {

	public static Object readRequest(HttpServletRequest request){
		 String str="";
		 String readerStr="";
		try {
			InputStreamReader in=new InputStreamReader(request.getInputStream(),"utf8");
			BufferedReader reader=new BufferedReader(in);
			while((str=reader.readLine())!=null){
				readerStr=readerStr+str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return readerStr;
	}
}

