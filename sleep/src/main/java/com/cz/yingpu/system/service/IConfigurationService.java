package com.cz.yingpu.system.service;

import com.cz.yingpu.system.entity.ConfigBean;
import com.cz.yingpu.system.entity.Configuration;
import com.cz.yingpu.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-02-24 15:17:26
 * @see com.cz.yingpu.system.service.Configuration
 */
public interface IConfigurationService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Configuration findConfigurationById(Object id) throws Exception;
	
	
	/**
	 * 获取app配置接口
	 * @author wj
	 * @return
	 * @throws Exception
	 */
	ConfigBean findParamBean() throws Exception;

	/**
	 * 新增或者修改公共参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	ConfigBean saveOrUpdate(Configuration config) throws  Exception ;

	/**
	 * 批量修改
	 * @param configBean
	 * @return
	 * @throws Exception
	 */
	ConfigBean remoteUpdate(ConfigBean configBean) throws Exception ;
	
}
