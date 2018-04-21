package com.cz.yingpu.frame.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cz.yingpu.frame.util.ReturnDatas;
 
/**
 *  Log基类,所有的类默认继承此类,可以直接使用 logger 记录日志,例如 logger.error("error");
 * @copyright {@link 9iu.org}
 * @author springrain<9iuorg@gmail.com>
 * @version  2013-03-19 11:08:15
 * @see com.cz.yingpu.frame.common.BaseLogger
 */
public class BaseLogger {
	public   Logger logger = LoggerFactory.getLogger(getClass());

	public ReturnDatas sendsms(Map<String, String> map, String[] variable)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
