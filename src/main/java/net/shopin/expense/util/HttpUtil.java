package net.shopin.expense.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;


public class HttpUtil {		
	
	public static Logger logger = Logger.getLogger(HttpUtil.class);
	
	public static String HttpPostForlogistics(String url, String method, String str){
		String encoding="UTF-8";
		String webUrl=url+"/"+method;
		if(encoding==null || "".equals(encoding)) encoding = "UTF-8";
				StringBuffer sBuffer = new StringBuffer();
				//构造HttpClient的实例
			  	HttpClient httpClient = new HttpClient();
	  	  		//创建POS方法的实例			  
			  	PostMethod postMethod = new PostMethod(webUrl);
			  	try {
//					postMethod.setRequestEntity(new ByteArrayRequestEntity(str.getBytes(),"application/json; charset=utf-8"));
					postMethod.setRequestBody(str);
			  	} catch (Exception e1) {
					e1.printStackTrace();
				}
			  	postMethod.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, encoding); 
			  	httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000); //连接5秒超时
			  	httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);//读取30秒超时
//			  	postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
//			  	postMethod.setDoAuthentication(false);
//			  	postMethod.addRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
			  	
			  	postMethod.setDoAuthentication(true);
				postMethod.addRequestHeader("Authorization", "Basic U0FQX0FNOkFubmllTWFvMTIzNA==");
				postMethod.setRequestHeader("Content-type", "application/json");
	  	  try {
	  	   //执行getMethod
	  	   int statusCode = httpClient.executeMethod(postMethod);
	  	   if (statusCode != HttpStatus.SC_OK) {
		  	    logger.error("Method failed: " + postMethod.getStatusLine());
		  	    sBuffer=new StringBuffer();
	  	   }else{
	  			InputStream resStream = postMethod.getResponseBodyAsStream();
			  	sBuffer = new StringBuffer(postMethod.getResponseBodyAsString() + "");  
	  	   }
	  	  } catch (HttpException e) {
	  	   //发生致命的异常，可能是协议不对或者返回的内容有问题
	  		  logger.info("Please check your provided http address!");
	  	   e.printStackTrace();
	  	  } catch (IOException e) {
	  	   //发生网络异常
	  	   e.printStackTrace();
	  	  } finally {
	  	   //释放连接
	  		postMethod.releaseConnection();
	  	  }
	  	  return sBuffer.toString();
	}
	
	public static String HttpPostForMap(String url, String method, Map<String, Object> parmMap){
		String encoding="UTF-8";
		String webUrl=url+"/"+method;
		if(encoding==null || "".equals(encoding)) encoding = "UTF-8";
		StringBuffer sBuffer = new StringBuffer();
		//构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		//创建POS方法的实例
		NameValuePair[] pairs = null;
		PostMethod postMethod = new PostMethod(webUrl);
		if(parmMap!=null){
			pairs = new NameValuePair[parmMap.size()];
			Set<?> set= parmMap.keySet();
			Iterator<?> it=set.iterator();
			int i=0;
			while(it.hasNext()){
				Object key=it.next();
				Object value=parmMap.get(key);
				pairs[i] = new NameValuePair(key.toString(), value.toString());
				i++;
			}
			postMethod.setRequestBody(pairs);
		}
		postMethod.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
		try {
			//执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
//			InputStream resStream = postMethod.getResponseBodyAsStream();
			sBuffer = new StringBuffer(postMethod.getResponseBodyAsString() + "");
			//处理内容
			//sBuffer.append(new String(responseBody,encoding));
		} catch (HttpException e) {
		//发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.info("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			//发生网络异常
			e.printStackTrace();
		} finally {
			//释放连接
			postMethod.releaseConnection();
		}
		return sBuffer.toString();
	}
	
	public static String HttpGetResufull(String url){
		
		String encoding="UTF-8";
		if(encoding==null || "".equals(encoding)) encoding = "UTF-8";
		StringBuffer sBuffer = new StringBuffer();
		//构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		//创建POS方法的实例
		NameValuePair[] pairs = null;
//		PostMethod postMethod = new PostMethod(webUrl);
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
		try {
			//执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			sBuffer = new StringBuffer(getMethod.getResponseBodyAsString() + "");
		} catch (HttpException e) {
			//发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.info("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			//发生网络异常
			e.printStackTrace();
		} finally {
			//释放连接
			getMethod.releaseConnection();
		}
		return sBuffer.toString();

      }
}
