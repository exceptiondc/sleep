package com.cz.yingpu.system.service;

import com.cz.yingpu.system.entity.User;
import com.cz.yingpu.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.service.User
 */
public interface IUserService extends IBaseSpringrainService {

	/**
	 * 保存
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveUser(User entity) throws Exception;

	/**
	 * 更新
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	Integer updateUser(User entity) throws Exception;

	/**
	 * 根据用户Id 删除用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	String deleteUserById(String userId) throws Exception;




	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	User findUserById(Object id) throws Exception;



	void updateRoleUser(String userId,String roleIds)throws Exception;

	/**
	 * 改变借款人账户状态
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	Integer changUser(Integer hotelId) throws Exception;

	/**
	 * 判断是否开通富友
	 * @param account
	 * @throws Exception
	 */
	User  isOpenFoiou(String account)throws Exception;


}
