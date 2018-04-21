package com.cz.yingpu.system.service;

import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.UserCard;
import com.cz.yingpu.system.service.IBaseSpringrainService;

import java.util.List;
import java.util.Map;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-06-21 09:28:19
 * @see com.cz.yingpu.system.service.UserCard
 */
public interface IUserCardService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UserCard findUserCardById(Object id) throws Exception;


	/**
	 * 获取用户的优惠券列表
	 * @param page
	 * @param userCard
	 * @return
	 * @throws Exception
	 */
	List<UserCard> findUserCard(Page page, UserCard userCard)throws Exception;


	/**
	 * 发放优惠券
	 * @param userCard
	 * @return
	 * @throws Exception
	 */
	String sendUserCard(UserCard userCard) throws Exception;
	
	//统计我的钱包中优惠券数量，店家优惠券数量  发票数量
	Map<String, Object> count(UserCard userCard);
	
}
