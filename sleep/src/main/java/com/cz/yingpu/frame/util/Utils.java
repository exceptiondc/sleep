package com.cz.yingpu.frame.util;

public class Utils {
	

	/**
	 * 字符转义
	 * 
	 * @param str
	 * @return
	 */
	public static String charEscape(String str) {
		// str = str.replace("<", "&lt;");
		// str = str.replace(">", "&gt;");
		str = str.replace("\\", "\\\\");
		// str = str.replace(",", "&sbquo;");
		// str = str.replace("\n", "&lt;br /&gt;");
		// str = str.replace("\r", "&lt;br /&gt;");
		// str = str.replace("\t", "&lt;br /&gt;");
		str = str.replace("\r\n", "");
		return str;
	}

}