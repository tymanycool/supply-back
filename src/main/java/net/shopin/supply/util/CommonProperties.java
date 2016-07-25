package net.shopin.supply.util;

import java.io.IOException;
import java.util.Properties;

public class CommonProperties {

	private static Properties properties = new Properties();
	static {
		try {
			properties.load(CommonProperties.class.getClassLoader()
					.getResourceAsStream("common.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
