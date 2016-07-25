package com.shopin.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class PromotionUtil {
	public static String getPropertity(String key) {
		String fileName = "/promotion.properties";
		Properties props = new Properties();
		InputStream in = null;
		String value = "";
		try {
			in = PromotionUtil.class.getResourceAsStream(fileName);
			props.load(in);
			value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}
}
