package com.cz.yingpu.system.service;

import com.cz.yingpu.system.entity.UserRole;
import com.cz.yingpu.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:50
 * @see com.cz.yingpu.system.service.UserRole
 */
public interface IUserRoleService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UserRole findUserRoleById(Object id) throws Exception;
	
	
	
}
