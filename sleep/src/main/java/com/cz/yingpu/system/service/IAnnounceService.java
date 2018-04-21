package com.cz.yingpu.system.service;

import com.cz.yingpu.system.entity.Announce;
import com.cz.yingpu.system.entity.CompanyState;
import com.cz.yingpu.system.entity.ContractSample;
import com.cz.yingpu.system.entity.ServiceIntroduce;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.service.Announce
 */
public interface IAnnounceService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Announce findAnnounceById(Object id) throws Exception;
	
	CompanyState findCompanyStateById(Object id)  throws Exception;

	com.cz.yingpu.system.entity.News findNewsById(Object id)  throws Exception;

	ContractSample findContractSampleById(Object id) throws Exception;
	
	ServiceIntroduce findServiceIntroduceById(Object id) throws Exception;
	
}
