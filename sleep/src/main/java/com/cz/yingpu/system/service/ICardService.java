package com.cz.yingpu.system.service;

import com.cz.yingpu.system.entity.Card;
import com.cz.yingpu.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-07-12 10:28:03
 * @see com.cz.yingpu.system.service.Card
 */
public interface ICardService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Card findCardById(Object id) throws Exception;
	
	
	
}
