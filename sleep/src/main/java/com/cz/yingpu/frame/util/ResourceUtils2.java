package com.cz.yingpu.frame.util;

import java.util.Properties;

public class ResourceUtils2 {
	private static Properties Properties;

	public ResourceUtils2() {
	}

/*	static {
		Properties = new Properties();
		try {
			Properties.load(new FileInputStream(
					ClassLoader.getSystemClassLoader().getResource("SystemComfig.properties").getPath()));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 根据key获得对应的value
	 * 
	 * @param strPropertyName
	 *            key
	 * @return String
	 */
	public static String getString(String strPropertyName) {
		try {
			return Properties.getProperty(strPropertyName);
		} catch (Exception e) {
			return strPropertyName;
		}
	}


	public static void main(String[] args) {
		
		System.out.print(ResourceUtils2.getString("templatepath"));
		
	}
	
}