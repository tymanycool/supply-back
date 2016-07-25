package net.shopin.supply.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtil {
	public static String GetUrlContent(String url,String paramStr){
		String encoding = "UTF-8";
		StringBuffer sBuffer = new StringBuffer();
		//构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		//创建GET方法的实例
		GetMethod getMethod = new GetMethod(url+"?"+paramStr);
		getMethod.addRequestHeader("Content-Type","text/html;charset="+encoding);
		//使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			//执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ getMethod.getStatusLine());
			}
			//读取内容
			byte[] responseBody = getMethod.getResponseBody();
			//处理内容
			sBuffer.append(new String(responseBody,encoding));
		} catch (HttpException e) {
			//发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
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
			System.out.println("Please check your provided http address!");
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
	
	public static String HttpPostForStr(String url, String method, String str){
		String encoding="UTF-8";
		String webUrl=url+"/"+method;
		StringBuffer sBuffer = new StringBuffer();
		//构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		//创建POS方法的实例			  
		PostMethod postMethod = new PostMethod(webUrl);
		try {
			postMethod.setRequestEntity(new ByteArrayRequestEntity(str.getBytes(),"application/json; charset=utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		postMethod.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, encoding); 
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000); //连接5秒超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);//读取30秒超时
		//postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
		//postMethod.addRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");

		postMethod.setDoAuthentication(true);
		postMethod.addRequestHeader("Authorization", "Basic U0FQX0FNOkFubmllTWFvMTIzNA==");
//		Base64.encode(("username:password").getBytes());----自动登录
		postMethod.setRequestHeader("Content-type", "application/json");
		try {
			//执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
				sBuffer=new StringBuffer();
			}else{
				sBuffer = new StringBuffer(postMethod.getResponseBodyAsString() + "");  
			}
		} catch (HttpException e) {
			//发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
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
	
	public static String HttpPost(String url, String method, Map paramMap){
		String encoding="UTF-8";
		String webUrl=url+"/"+method;
		if(encoding==null || "".equals(encoding)) encoding = "UTF-8";
				StringBuffer sBuffer = new StringBuffer();
				//构造HttpClient的实例
			  	HttpClient httpClient = new HttpClient();
	  	  		//创建POS方法的实例			  
			  	NameValuePair[] pairs = null;
			  	PostMethod postMethod = new PostMethod(webUrl);
			  	if(paramMap!=null){
			  		pairs = new NameValuePair[paramMap.size()];
					Set set= paramMap.keySet();
					Iterator it=set.iterator();
					int i=0;
					while(it.hasNext()){
						Object key=it.next();
						Object value=paramMap.get(key);
						pairs[i] = new NameValuePair(key.toString(), value.toString());
						i++;
					}
					postMethod.setRequestBody(pairs); 
				}
			  	postMethod.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);  			  
			  	httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000); //连接5秒超时
			  	httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);//读取30秒超时
	  	  try {
	  	   //执行getMethod
	  	   int statusCode = httpClient.executeMethod(postMethod);
	  	   if (statusCode != HttpStatus.SC_OK) {
		  	    System.err.println("Method failed: " + postMethod.getStatusLine());
		  	    sBuffer=new StringBuffer();
	  	   }else{
			  	sBuffer = new StringBuffer(postMethod.getResponseBodyAsString() + "");  
	  	   }
	  	  } catch (HttpException e) {
	  	   //发生致命的异常，可能是协议不对或者返回的内容有问题
	  	   System.out.println("Please check your provided http address!");
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
	
	public static String HttpPostForJson(String url, String method, String str){
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
		  	    System.err.println("Method failed: " + postMethod.getStatusLine());
		  	    sBuffer=new StringBuffer();
	  	   }else{
	  			InputStream resStream = postMethod.getResponseBodyAsStream();
			  	sBuffer = new StringBuffer(postMethod.getResponseBodyAsString() + "");  
	  	   }
	  	  } catch (HttpException e) {
	  	   //发生致命的异常，可能是协议不对或者返回的内容有问题
	  	   System.out.println("Please check your provided http address!");
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
}
