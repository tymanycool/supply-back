package net.shopin.supply.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shopin.core.framework.exception.ShopinException;


public class DataUtil {
	/**
	 * 将时间字符串转成Date类型
	 * @param time
	 * @return
	 */
	public static Date parseString(String time){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date=sf.parse(time);
		} catch (ParseException e) {
			date=new Date();
		}
		return date;
	}
	/**
	 * 将时间字符串转成Date类型
	 * @param time
	 * @return
	 */
	public static Date parseStringToDate(String time){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date=sf.parse(time);
		} catch (ParseException e) {
			date=new Date();
		}
		return date;
	}
	/**
	 * 将Date类型转成时间字符串
	 * @param time
	 * @return
	 */
	public static String parseDate(Date time){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str="";
		str=sf.format(time);
		return str;
	}
	  //   格式化日期为字符串   "yyyy-MM-dd"   
    public static String formatDateTime(Date time){   
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String str="";
		str=sf.format(time);
		return str;
    }   
    public static String formatDateTimeToCash(Date time){   
    	SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		String str="";
		str=sf.format(time);
		return str;
    }  
    public static String formatDateTimeToSap(Date time){   
    	SimpleDateFormat sf=new SimpleDateFormat("HH:mm:ss");
		String str="";
		str=sf.format(time);
		return str;
    } 
    
    /**
     * 将日期2013-2-2或2013-02-02  --转化为--》2013-02-02
     */
    public static String formatDateStr(String time){
    	String[] date = time.split("-");
    	String year = date[0];
    	String month = date[1];
    	String day = date[2];
    	if(month.length()==1){
    		month = "0"+month;
    	}
    	if(day.length() == 1){
    		day = "0"+day;
    	}
    	String str = year+"-"+month+"-"+day;
    	return str;
    }
	/**
	 * 对json进行格式化
	 * @param uglyJSONString
	 * @return
	 */
	public static String jsonFormatter(String uglyJSONString){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJSONString);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}
	

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
	
	public static String Object2Json(Object obj) {
		String jsonStr = "";
		if (obj != null) {
            if (isArray(obj)) {
                JSONArray jsonArray = JSONArray.fromObject(obj);
                jsonStr =  jsonArray.toString();
            } else {
                JSONObject jsonObject = JSONObject.fromObject(obj);
                jsonObject.put("success", "true");
                jsonStr = jsonObject.toString();
            }
        }
		return jsonStr;
	}
	public static void Json2Object(String jsonStr ,Object target){
		 if (target != null && jsonStr.length() > 0 && jsonStr.charAt(0) == '[') {
	            JSONArray jsonArray = JSONArray.fromObject(jsonStr);
	            if (target.getClass().isArray()) {
	                JSONArray.toArray(jsonArray, target, new JsonConfig());
	            } else {
	                JSONArray.toList(jsonArray, target, new JsonConfig());
	            }

	        } else {
	            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
	            JSONObject.toBean(jsonObject, target, new JsonConfig());
	        }
	}
	private  static boolean isArray(Object obj) {
        return obj instanceof Collection || obj.getClass().isArray();
    }
	
	public static String getErrorJson(String jsontype,String msg){
		String jsondata="";
		if(jsontype!=null&&jsontype.equals("ext")){
			jsondata="{'success':false,'msg':'"+msg+"'}";
		}else{
			jsondata="{'success':'false','msg':'"+msg+"'}";
		}
		return jsondata;
	}
	
	
	public static String addDays(String supplyTime,int i ){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dd;
		try {
			dd = df.parse(supplyTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DAY_OF_MONTH,i);
			return df.format(calendar.getTime());
		} catch (ParseException e) {
			return e.toString();
		}
	}
}
