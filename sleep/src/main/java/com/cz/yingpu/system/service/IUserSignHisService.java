package com.cz.yingpu.system.service;

import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.UserSignHis;
import com.cz.yingpu.system.service.IBaseSpringrainService;

import java.util.List;
import java.util.Map;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:50
 * @see com.cz.yingpu.system.service.UserSignHis
 */
public interface IUserSignHisService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UserSignHis findUserSignHisById(Object id) throws Exception;


	/**
	 * 签到
	 * @param userId
	 * @param type
	 * @param osType
	 * @return
	 * @throws Exception
	 */
	String sign(Integer userId,Integer type,String osType) throws Exception;

	List listUserSignHis(Page page , Map<String, Object> values) throws Exception;
	
}
