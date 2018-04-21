package com.cz.yingpu.system.service;

import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.LunboPic;
import com.cz.yingpu.system.service.IBaseSpringrainService;
import java.util.List;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:46
 * @see com.cz.yingpu.system.service.LunboPic
 */
public interface ILunboPicService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	LunboPic findLunboPicById(Object id) throws Exception;

	/**
	 * 后台轮播列表
	 * @param page
	 * @param lunboPic
	 * @return
	 * @throws Exception
	 */
	List<LunboPic> list(Page page, LunboPic lunboPic) throws  Exception ;
	
}
