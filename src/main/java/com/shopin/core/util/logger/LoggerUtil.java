/**
 * 说 明     : 日志工具类
 * author: 陆湘星
 * data  : 2011-5-28
 * email : xiangxingchina@163.com
 **/
package com.shopin.core.util.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shopin.core.constants.PropertiesLoad;
import com.shopin.core.constants.SystemConfig.DebugType;
import com.shopin.core.util.DateUtils;



/**
log4j具有5种正常级别(Level)。 
Level DEBUG  指出细粒度信息事件对调试应用程序是非常有帮助的。
Level INFO   表明 消息在粗粒度级别上突出强调应用程序的运行过程
Level WARN   表明会出现潜在错误的情形
level ERROR  指出虽然发生错误事件，但仍然不影响系统的继续运行。
level FATAL  指出每个严重的错误事件将会导致应用程序的退出。
//自定义 
level Stock  跟库存相关  需要同步到数据库日志
 * @author luxiangxing
 *
 */
public class LoggerUtil {
  
  public static final String LOG_FILE_LOGGERINFO = "loggerInfo";				//普通日志
  public static final String LOG_FILE_EXCEPTIONINFO = "exceptionInfo";			//异常日志
  public static final String LOG_FILE_WSINFO = "wsInfo";						//WS日志
  public static final String LOG_FILE_WSEXCEPTIONINFO = "wsExceptionInfo";		//WS异常日志
  public static final String LOG_FILE_IMPORTIONINFO = "importantInfo";			//重要 日志
  public static final String LOG_FILE_IMPORTIONEXCEPTIONINFO = "importantExceptionInfo";//重要 异常日志
  //阿里支付日志
  public static final String LOG_FILE_ALIPAYINFO = "alipayInfo";			//阿里日志
  public static final String LOG_FILE_ALIPAYEXCEPTIONINFO = "alipayExceptionInfo";//阿里异常日志
  
  
  private static Map<String,Logger> logMap = new HashMap<String, Logger>();
//  loggerInfo,exceptionInfo,importantInfo,stockExceptionInfo
  
  private static String base_message = null;       	//存储类路径
  private static String condMessage = null;		//存储条件对象
  
  private static int debug_level = 0;
  
  public  synchronized static LoggerUtil getLogger(String baseMessage) {
	   base_message = baseMessage;
	   if(debug_level==0){
			   String debug = PropertiesLoad.getPropertiesDebugLevel();
			   if(debug!=null) {
				   try {
					   debug_level = Integer.valueOf(debug);
					} catch (Exception e) {
					}
		   }
	   }
	  return new LoggerUtil();
   }
   
   public void setCondMessage(String condMessage){
	   this.condMessage = condMessage;
   }
   
   public String getMessage(){
		StringBuffer msg = new StringBuffer();
		if(base_message!=null )msg.append("\r\n").append("["+base_message+"]");
		if(condMessage!=null) msg.append("\r\n").append("["+condMessage+"]");
		return msg.toString();
	}
   
   public  void  printException(String msg,Exception e){
	   	if(debug_level<=DebugType.exceptionInfo.getValue()){
	   		printLog(LOG_FILE_EXCEPTIONINFO,   msg + "\r\n" + getTrace(e)+ "\r\n");
	   	}
   }
   
   
   public  void  printWsException(String msg,Exception e){
		if(debug_level<=DebugType.wsExceptionInfo.getValue()){
	   		printLog(LOG_FILE_WSEXCEPTIONINFO, msg + "\r\n" + getTrace(e)+ "\r\n");
	   	}  	
   }
   

   
   public  void  printImportantException(String msg,Exception e){
		if(debug_level<=DebugType.importantExceptionInfo.getValue()){
	   		printLog(LOG_FILE_IMPORTIONEXCEPTIONINFO,  msg + "\r\n" + getTrace(e)+ "\r\n");
	   	}
  }
   
   public  void  printAlipayException(String msg,Exception e){
		if(debug_level<=DebugType.alipayExceptionInfo.getValue()){
	   		printLog(LOG_FILE_ALIPAYEXCEPTIONINFO,  msg + "\r\n" + getTrace(e)+ "\r\n");
	   	}
   }
   //--信息 
   public  void  printResultInfo(String resultJson,Date start){
	   if(debug_level<DebugType.nolog.getValue()){
		   StringBuffer infoStr = new StringBuffer();
			  if(debug_level<=DebugType.debug.getValue()) {
				  int len =  0;
				  if(resultJson!=null) len = resultJson.toString().length();
				  infoStr.append("result = " + resultJson+"\r\n返回长度"+len);
			  }			  
			 String useTime = DateUtils.PrintTimeGap("", start, new Date());
			 infoStr.append(" 使用时间："+useTime);
			 printLog(LOG_FILE_LOGGERINFO,infoStr.toString());
	   }
   }
   
   
   public  void  printWsResultInfo(String resultJson,Date start){
	   if(debug_level<DebugType.nolog.getValue()){
		   StringBuffer infoStr = new StringBuffer();
			  if(debug_level<=DebugType.wsInfo.getValue()) {
				  int len =  0;
				  if(resultJson!=null) len = resultJson.toString().length();
				  infoStr.append("result = " + resultJson+"\r\n返回长度"+len);
			  }			  
			 String useTime = DateUtils.PrintTimeGap("", start, new Date());
			 infoStr.append(" 使用时间："+useTime);
			 printLog(LOG_FILE_WSINFO,infoStr.toString());
	   }
   }
   
   public  void  printImportantResultInfo(String resultJson,Date start){
	   if(debug_level<DebugType.nolog.getValue()){
		   StringBuffer infoStr = new StringBuffer();
			  if(debug_level<=DebugType.importantInfo.getValue()) {
				  int len =  0;
				  if(resultJson!=null) len = resultJson.toString().length();
				  infoStr.append("result = " + resultJson+"\r\n返回长度"+len);
			  }			  
			 String useTime = DateUtils.PrintTimeGap("", start, new Date());
			 infoStr.append(" 使用时间："+useTime);
			 printLog(LOG_FILE_IMPORTIONINFO,infoStr.toString());
	   }
   }
   
   public  void  printAlipayResultInfo(String resultJson,Date start){
	   if(debug_level<DebugType.nolog.getValue()){
		   StringBuffer infoStr = new StringBuffer();
			  if(debug_level<=DebugType.alipayInfo.getValue()) {
				  int len =  0;
				  if(resultJson!=null) len = resultJson.toString().length();
				  infoStr.append("result = " + resultJson+"\r\n返回长度"+len);
			  }			  
			 String useTime = DateUtils.PrintTimeGap("", start, new Date());
			 infoStr.append(" 使用时间："+useTime);
			 printLog(LOG_FILE_ALIPAYINFO,infoStr.toString());
	   }
   }
   
   public  void  printLog(String logFile,String logStr){
	    Logger logger = logMap.get(logFile);
	    if(logger==null ){
	    	synchronized (this) {
	    		logger = Logger.getLogger(logFile);
			}
	    	logMap.put(logFile, logger);
	    }
	    	logger.info(getMessage()+ "\r\n"  + logStr+ "\r\n");
  }
	 
	 //--私有方法
	private  String getTrace(Throwable t) {
	        StringWriter stringWriter= new StringWriter();
	        PrintWriter writer= new PrintWriter(stringWriter);
	        t.printStackTrace(writer);
	        StringBuffer buffer= stringWriter.getBuffer();
	        return buffer.toString();
	}
	
}
