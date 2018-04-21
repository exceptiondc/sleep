package com.cz.yingpu.system.service;

import com.cz.yingpu.system.entity.SysParamBean;
import com.cz.yingpu.system.entity.SysSysparam;
import com.cz.yingpu.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.service.SysSysparam
 */
public interface ISysSysparamService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	SysSysparam findSysSysparamById(Object id) throws Exception;

	SysParamBean findParamBean() throws Exception;

	/**
	 * 新增或者修改公共参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SysParamBean saveOrUpdate(SysSysparam param) throws  Exception ;


}
