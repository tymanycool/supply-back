package com.shopin.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipOutputStream;

/**
 * Servlet处理公共类.
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
public class ServletHelp {

	/**
	 * 回写json格式的结果
	 * 
	 * @param request
	 * @return
	 */
	public static void outRequestForJson(HttpServletRequest request,
			HttpServletResponse response, String res) throws IOException {
		response.setContentType("application/x-json; charset=UTF-8");
		response.getWriter().print(res);
	}

	/**
	 * 获得服务器真实路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request,
			String virtualPath) {
		return request.getSession().getServletContext()
				.getRealPath(virtualPath);
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	/**
	 * 把Map转换成javascript数组
	 * 
	 * @param map
	 *            要转行的Map
	 * @param emptyText
	 *            设置空值显示的文本，为空是不添加空选项
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getArrayFromMap(Map map, String needEmpty) {
		if (null == map) {
			return null;
		}
		StringBuffer sb = null;
		if (StringUtils.isNotEmpty(needEmpty)) {
			sb = new StringBuffer("[[\'\', \'" + needEmpty + "\'],");
		} else {
			sb = new StringBuffer("[");
		}
		Set<String> keySet = map.keySet();
		Iterator<String> ite = keySet.iterator();
		String key = "";
		while (ite.hasNext()) {
			key = ite.next();
			sb.append("[\'" + key + "\', \'" + map.get(key) + "\'],");
		}
		String rs = sb.toString();
		if (rs.endsWith(",")) {
			rs = rs.substring(0, rs.length() - 1);
		}
		return rs += "]";
	}

	/**
	 * 将inputStream放入ZipOutputStream
	 * 
	 * @param zipOutput
	 * @param in
	 * @throws Exception
	 */
	private static void writeZipStream(ZipOutputStream zipOutput, InputStream in)
			throws Exception {
		if (in == null) {
			return;
		}
		byte b[] = new byte[1024 * 5];
		int len = 0;
		while ((len = in.read(b)) != -1) {
			zipOutput.write(b, 0, len);
		}
		in.close();
	}

	/**
	 * 获得http连接
	 * 
	 * @param url
	 *            URL
	 * @return http连接
	 * @throws Exception
	 */
	public static HttpURLConnection getHttpConnection(String url)
			throws Exception {
		URL u = new URL(url);
		return (HttpURLConnection) u.openConnection();
	}
}
