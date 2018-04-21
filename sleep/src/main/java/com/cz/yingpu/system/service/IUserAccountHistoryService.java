package com.cz.yingpu.system.service;

import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.UserAccountHistory;
import com.cz.yingpu.system.service.IBaseSpringrainService;

import java.util.List;
import java.util.Map;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.service.UserAccountHistory
 */
public interface IUserAccountHistoryService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UserAccountHistory findUserAccountHistoryById(Object id) throws Exception;

	/**
	 * 分销总和列表
	 * @param page
	 * @param userAccountHistory
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> fenxiaoList(Page page,UserAccountHistory userAccountHistory) throws  Exception ;


	/**
	 * 分销明细列表
	 * @param page
	 * @param userAccountHistory
	 * @return
	 * @throws Exception
	 */
	List<UserAccountHistory> fenxiaoDetailList(Page page,UserAccountHistory userAccountHistory) throws  Exception ;

	/**
	 * 获取用户的投资收益
	 * @param page
	 * @param userAccountHistory
	 * @return
	 * @throws Exception
	 */
	List<UserAccountHistory> investIncome(Page page, UserAccountHistory userAccountHistory) throws Exception;
	

	
	/**
	 * 获取还款流水
	 * @param page
	 * @param userAccountHistory
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> repaymentDetailList(Page page, UserAccountHistory userAccountHistory) throws Exception;
}
