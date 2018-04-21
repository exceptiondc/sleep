package com.cz.yingpu.system.service;

import java.util.Map;

import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.Sms;
import com.cz.yingpu.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-22 11:49:23
 * @see com.cz.yingpu.system.service.Sms
 */
public interface ISmsService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Sms findSmsById(Object id) throws Exception;


	public Sms sendCode(Sms sms) throws Exception;

	//给用户发短信


	ReturnDatas sendsms(Map<String, String> map,  String[] variable) throws Exception;


}
