package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.service.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cz.yingpu.system.entity.UserAccountHistory;
import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;

import javax.annotation.Resource;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.service.impl.UserAccountHistory
 */
@Service("userAccountHistoryService")
public class UserAccountHistoryServiceImpl extends BaseSpringrainServiceImpl implements IUserAccountHistoryService {


	@Resource
	private IAppUserService appUserService ;

   
    @Override
	public String  save(Object entity ) throws Exception{
	      UserAccountHistory userAccountHistory=(UserAccountHistory) entity;
	       return super.save(userAccountHistory).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      UserAccountHistory userAccountHistory=(UserAccountHistory) entity;
		 return super.saveorupdate(userAccountHistory).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 UserAccountHistory userAccountHistory=(UserAccountHistory) entity;
	return super.update(userAccountHistory);
    }
    @Override
	public UserAccountHistory findUserAccountHistoryById(Object id) throws Exception{
	 return super.findById(id,UserAccountHistory.class);
	}
	
/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param o
 * @return
 * @throws Exception
 */
        @Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception{
        	List list = super.findListDataByFinder(finder,page,clazz,o);
        	if(list != null && list.size() != 0){
				Iterator<UserAccountHistory> iter = list.iterator() ;
				UserAccountHistory uah = null ;  //交易记录
				AppUser user = null ;   //用户信息
			
			}


			 return list;
			}
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
		@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o)
			throws Exception {
			 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
		}


	@Override
	public List<Map<String, Object>> fenxiaoList(Page page, UserAccountHistory userAccountHistory) throws Exception {
//			Finder finder = new Finder("SELECT uah.userId as userId,CONVERT(SUM(uah.money),DECIMAL) as money,au.`name` as name,au.header as header,au.phone as phone FROM `t_user_account_history` AS uah LEFT JOIN t_app_user au ON uah.fenxiaoUserId = au.id WHERE uah.userId = :userId AND uah.type=8 GROUP BY uah.fenxiaoUserId") ;
			Finder finder = new Finder("SELECT uah.fenxiaoUserId as userId,SUM(uah.money) as money,au.`name` as name,au.header as header,au.phone as phone FROM t_user_yeb_history AS uah LEFT JOIN t_app_user au ON uah.fenxiaoUserId = au.id WHERE uah.userId =:userId AND uah.type in (6,7,8) GROUP BY uah.fenxiaoUserId");
			finder.setParam("userId",userAccountHistory.getUserId()) ;
			page.setOrder("uah.id");
			List<Map<String,Object>> list = super.queryForList(finder,page) ;

		return list;
	}
	@Override
	public List<UserAccountHistory> fenxiaoDetailList(Page page, UserAccountHistory userAccountHistory) throws Exception {
//		Finder finder = new Finder("SELECT uah.* ,ap.money as projectMoney FROM `t_user_account_history` AS uah LEFT JOIN t_user_project ap ON uah.userProjectId = ap.id WHERE uah.fenxiaoUserId = :fenxiaoUserId AND uah.type=8 ") ;
		Finder finder = new Finder("SELECT uah.money as money,uah.fenxiaoUserId as fenxiaoUserId ,uah.createTime as createtime ,ap.money as projectMoney FROM t_user_yeb_history AS uah LEFT JOIN t_user_project ap ON uah.userProjectId = ap.id WHERE uah.fenxiaoUserId =:fenxiaoUserId AND uah.type in (6,7) ");
		finder.setParam("fenxiaoUserId",userAccountHistory.getFenxiaoUserId()) ;
		page.setOrder("uah.id");
		List<UserAccountHistory> list = super.queryForList(finder,UserAccountHistory.class,page) ;
		return list;
	}


	@Override
	public List<UserAccountHistory> investIncome(Page page, UserAccountHistory userAccountHistory) throws Exception {
		Finder finder = new Finder("select * from t_user_account_history where type=4 and status=2 and userId=:userId ");
		finder.setParam("userId",userAccountHistory.getUserId());
		List<UserAccountHistory> list = super.queryForList(finder,UserAccountHistory.class,page) ;
		return list;
	}

	@Override
	public List<Map<String, Object>> repaymentDetailList(Page page,
			UserAccountHistory userAccountHistory) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}







}
