package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.Org;
import com.cz.yingpu.system.entity.Role;
import com.cz.yingpu.system.entity.User;
import com.cz.yingpu.system.entity.UserOrg;
import com.cz.yingpu.system.entity.UserRole;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.IUserOrgService;
import com.cz.yingpu.system.service.IUserRoleMenuService;
import com.cz.yingpu.system.service.IUserService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.cz.yingpu.springrain.service.impl.User
 */
@Service("userService")
public class UserServiceImpl extends BaseSpringrainServiceImpl implements IUserService {
	
	@Resource
	private IUserOrgService userOrgService;
	
	@Resource
	private IUserRoleMenuService userRoleMenuService;

	@Override
	public String saveUser(User entity) throws Exception {
		String id=super.save(entity).toString();
		updateUserInfo(entity);
		
		return id;
	}

	@Override
	@CacheEvict(value=GlobalStatic.qxCacheKey,allEntries=true)  
	public Integer updateUser(User entity) throws Exception {
		 Integer update = super.update(entity,true);
		 updateUserInfo(entity);
		return update;
	}
	
	private void  updateUserInfo(User user)throws Exception {
		String userId=user.getId();
		Finder f_del=Finder.getDeleteFinder(UserOrg.class).append(" WHERE userId=:userId ");
		f_del.setParam("userId",userId);
		super.update(f_del);
		
		Finder f_del_role=Finder.getDeleteFinder(UserRole.class).append(" WHERE userId=:userId ");
		f_del_role.setParam("userId",userId);
		super.update(f_del_role);
		
		
		
		List<Org> list = user.getUserOrgs();
		List<UserOrg> listuo=new ArrayList<UserOrg>();
		for(Org org:list){
			UserOrg uo=new UserOrg();
			uo.setUserId(userId);
			uo.setOrgId(org.getId());
			listuo.add(uo);
		}
		if(CollectionUtils.isEmpty(listuo)){
			return;
		}
		
		super.save(listuo);
		
		
		
		List<Role> listRole = user.getUserRoles();
		List<UserRole> listur=new ArrayList<UserRole>();
		for(Role role:listRole){
			UserRole ur=new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(role.getId());
			listur.add(ur);
		}
		if(CollectionUtils.isEmpty(listur)){
			return;
		}
		
		super.save(listur);
		
		
		
		
		
		
	}
	
	

	@Override
	public User findUserById(Object id) throws Exception {
		
		User u=super.findById(id, User.class);
		
		String userId=u.getId();
		
		List<Org> listOrg = userOrgService.findOrgByUserId(userId);
		u.setUserOrgs(listOrg);
		List<Role> roleByUserId = userRoleMenuService.findRoleByUserId(userId);
		u.setUserRoles(roleByUserId);
		
		
		return u;
	}

	
	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object o)
			throws Exception {
		User user = (User) o;
		// ==执行分页查询
		// user.setFrameTableAlias("tu");
		// finder=Finder.getSelectFinder(User.class,"tu.*,tg.name as gradeName ").append(" tu,").append(Finder.getTableName(DicData.class)).append(" tg WHERE tu.gradeId=tg.id and tg.typekey='grade' ");

		finder = Finder.getSelectFinder(User.class).append(" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, user);
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);
		
		return queryForList;
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * 
	 * @param finder
	 *            为空则只查询 clazz表
	 * @param ftlurl
	 *            类表的模版宏
	 * @param page
	 *            分页对象
	 * @param clazz
	 *            要查询的对象
	 * @param o
	 *            querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl, Page page, Class<T> clazz,
			Object o) throws Exception {
		return super.findDataExportExcel(finder, ftlurl, page, clazz, o);
	}

	@Override
	
	@Caching(evict = {
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findRoleByUserId_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'getRolesAsString_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findUserByRoleId_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findAllRoleAndMenu'") })
	
	public void updateRoleUser(String userId, String roleId) throws Exception {
		// 删除
		// Finder finder=new
		// Finder("delete from t_user_role where userId=:userId");
		Finder finder = Finder.getDeleteFinder(UserRole.class).append(" WHERE userId=:userId");

		finder.setParam("userId", userId);
		this.update(finder);
		// 添加
		String[] roleIds = roleId.split(",");
		// finder=new
		// Finder("insert into t_user_role(id,userId,roleId) values(:id,:userId,:roleId)");
		// finder.setParam("userId", userId);

		List<UserRole> list = new ArrayList<UserRole>();

		for (String str : roleIds) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(str);
			list.add(ur);
		}

		if (!CollectionUtils.isEmpty(list)) {
			super.save(list);
		}

	}

	@Override
	public String deleteUserById(String userId) throws Exception {
		if(StringUtils.isBlank(userId)){
			return null;
		}
		
		
		Finder f_del_role=Finder.getDeleteFinder(UserRole.class).append(" WHERE userId=:userId ").setParam("userId", userId);
		super.update(f_del_role);
		
		Finder f_del_org=Finder.getDeleteFinder(UserOrg.class).append(" WHERE userId=:userId ").setParam("userId", userId);
		super.update(f_del_org);
		
		
		Finder f_update=Finder.getUpdateFinder(User.class," state=:state ").append(" WHERE id=:id ").setParam("id", userId).setParam("state", "否");
		
		super.update(f_update);
		
		
		return null;
	}

	@Override
	public  Integer changUser(Integer hotelId) throws Exception {
		Finder finder =  Finder.getSelectFinder(User.class).append(" WHERE hotelId=:hotelId ");
		finder.setParam("hotelId",hotelId);
		User user = queryForObject(finder,User.class);
//		user.setState("是");
		return update(user);
	}

	@Override
	public  User  isOpenFoiou(String account)throws Exception {
		Finder finder =  Finder.getSelectFinder(User.class).append(" WHERE account=:account ");
		finder.setParam("account",account);
		User user = queryForObject(finder,User.class);
//		if ("否".equals(user.getIsOpenFuiou())) throw new NotOpenFoiouException();
		return user;
	}

}
