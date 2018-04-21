package com.cz.yingpu.system.service;

import com.cz.yingpu.system.entity.RoleMenu;
import com.cz.yingpu.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:48
 * @see com.cz.yingpu.system.service.RoleMenu
 */
public interface IRoleMenuService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	RoleMenu findRoleMenuById(Object id) throws Exception;
	
	
	
}
