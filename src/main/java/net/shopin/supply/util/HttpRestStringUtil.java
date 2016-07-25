package net.shopin.supply.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpRestStringUtil {

	private static Log log = LogFactory.getLog(HttpRestStringUtil.class);

	private static ThreadLocal<HttpClient> clientPool = new ThreadLocal<HttpClient>();

	private static String defaultCharset = "UTF-8";

	public static String getDefaultCharset() {
		return defaultCharset;
	}

	public static void setDefaultCharset(String defaultCharset) {
		HttpRestStringUtil.defaultCharset = defaultCharset;
	}

	private static HttpClient getClient() {
		if (clientPool.get() != null)
			return clientPool.get();
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset(defaultCharset);
		clientPool.set(client);
		return client;
	}

	public static String getRestString(String url,
			Object... params) {
		HttpClient client = getClient();
		client.getParams().setContentCharset(defaultCharset);
		Pattern pattern = Pattern.compile("\\{[a-zA-Z]{1}\\w*\\}");
		Matcher matcher = pattern.matcher(url);
		int i = 0;
		while (matcher.find()) {
			try {
				String param = params[i].toString();
				param = URLEncoder.encode(param, defaultCharset);
				url = matcher.replaceFirst(param);
				matcher.reset(url);
			} catch (UnsupportedEncodingException e1) {
			} catch (IndexOutOfBoundsException e) {
				throw new IllegalArgumentException("params amount invalid.");
			}
			i++;
		}
		HttpMethod method = new GetMethod(url);
		if (log.isDebugEnabled()) {
			try {
				log.debug("real request url: " + method.getURI());
			} catch (URIException e) {
			}
		}
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				return method.getResponseBodyAsString();
			}
			log.error("response not OK, get status:" + statusCode);
			return null;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("Http调用失败", e);
			}
			return null;
		}
	}

}
