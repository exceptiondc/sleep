package com.cz.yingpu.system.service;

import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.entity.Token;
import com.cz.yingpu.system.fuyoudata.AppTransReqData;
import com.cz.yingpu.system.fuyoudata.AppTransRspData;
import com.cz.yingpu.system.fuyoudata.QueryBalanceResultData;
import com.cz.yingpu.system.fuyoudata.QueryUserInfs_v2RspDetailData;
import com.cz.yingpu.system.service.IBaseSpringrainService;

import java.util.List;
import java.util.Map;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:44
 * @see com.cz.yingpu.system.service.AppUser
 */
public interface IAppUserService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AppUser findAppUserById(Object id) throws Exception;

	/**
	 * 新用户注册
	 * @param appUser
	 * @param page
	 * @return
	 * @throws Exception
	 */


	/**
	 * 获取缓存中的token
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Token getToken(Integer userId) throws Exception;

	AppUser modify(AppUser appUser) throws Exception;

	void saveDetail(Integer type, AppUser appUser, AppTransReqData appTransReqData) throws Exception;

	void saveDetailChange(AppTransRspData appTransRspData)  throws Exception;

	/**
	 * 查询用户富友账户余额
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	QueryBalanceResultData queryUserBalance(String phone) throws Exception;

	AppUser isFirst(Integer id) throws Exception;

	/**
	 * 查看手机号是否注册富友
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	List<QueryUserInfs_v2RspDetailData> queryUserInfs(String phone)throws Exception;

	void changeEWM()throws Exception;

	/**
	 * 统计金额
	 * @param appUser
	 * @return
	 * @throws Exception
	 */
	AppUser getTotalBalance(AppUser appUser)throws Exception;

	/**
	 * 群发信息
	 * @throws Exception
	 */
	void sendMessage()throws Exception;


	/**
	 * 设置合伙人
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	Integer isPartner(Integer id, String type) throws Exception;

	/**
	 * 获取合伙人列表
	 * @param page
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List partnerList(Page page,Map<String, Object> map)throws Exception;

	/**
	 * 获取合伙人的下级列表
	 * @param userId 用户的ID
	 * @param level 下级的等级，一共分6级，获取第一级用户不传，每获取一级都要加一，直到第6级
	 * @return
	 * @throws Exception
	 */
	List partnerFenxiaoList(Integer userId, String month)throws Exception;

	/**
	 * 获取合伙人的总分成
	 * @param userId
	 * @param month
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> fenxiaoTotalList(Integer userId, String month)throws Exception;

	/**
	 * 给合伙人发放奖励
	 * @param id
	 * @param money
	 * @return
	 * @throws Exception
	 */
	Integer sendMoney(Integer id,Integer orderId) throws Exception;

	/**
	 * 给合伙人下级用户列表
	 * @param id
	 * @param money
	 * @return
	 * @throws Exception
	 */
	List fenxiaoList(Integer userId,Map<String,Object> map)throws Exception;

	/**
	 * 给用户增加天天存吧金额
	 * @param userId
	 * @param money
	 * @return
	 * @throws Exception
	 */
	Integer addYebBalance(int userId,Double money)throws Exception;

	AppUser findAppUserByphone(Object id) throws Exception;
	
	
	List<AppUser> findUserRecommend(AppUser appUser,int type,Page pge)  throws Exception;

	AppUser register(AppUser appUser, Page page, String regType)
			throws Exception;

	AppUser login(AppUser appUser, Page page)
			throws Exception;

	AppUser wxLogin(String code) throws Exception;
}
