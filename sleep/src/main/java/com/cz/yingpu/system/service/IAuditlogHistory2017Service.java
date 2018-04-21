package com.cz.yingpu.system.service;

import com.cz.yingpu.system.entity.AuditlogHistory2017;
import com.cz.yingpu.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:44
 * @see com.cz.yingpu.system.service.AuditlogHistory2017
 */
public interface IAuditlogHistory2017Service extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AuditlogHistory2017 findAuditlogHistory2017ById(Object id) throws Exception;
	
	
	
}
