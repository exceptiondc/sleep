package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cz.yingpu.frame.util.ExcelUtils;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.exception.CardTimeException;
import com.cz.yingpu.system.exception.NoUserException;
import com.cz.yingpu.system.service.IAppUserService;
import com.cz.yingpu.system.service.JPushService;

import jxl.Cell;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cz.yingpu.system.entity.UserCard;
import com.cz.yingpu.system.service.IUserCardService;
import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;

import javax.annotation.Resource;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-06-21 09:28:19
 * @see com.cz.yingpu.system.service.impl.UserCard
 */
@Service("userCardService")
public class UserCardServiceImpl extends BaseSpringrainServiceImpl implements IUserCardService {

	@Resource
	private IAppUserService appUserService;
	@Resource
	private JPushService jPushService;
   
    @Override
	public String  save(Object entity ) throws Exception{
	      UserCard userCard=(UserCard) entity;
	       return super.save(userCard).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      UserCard userCard=(UserCard) entity;
		 return super.saveorupdate(userCard).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 UserCard userCard=(UserCard) entity;
	return super.update(userCard);
    }
    @Override
	public UserCard findUserCardById(Object id) throws Exception{
	 return super.findById(id,UserCard.class);
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
        	UserCard userCard = (UserCard) o;
        	
        	finder = new Finder("SELECT tuc.*,tau.realName AS userName FROM t_user_card tuc LEFT JOIN t_app_user tau ON tuc.userId = tau.id WHERE 1=1");
        	if(StringUtils.isNotBlank(userCard.getUserName())){
        		finder.append(" and tau.realName=:userName ");
        		finder.setParam("userName",userCard.getUserName());
			}
			if(null!=userCard.getStatus()){
				finder.append(" and tuc.status=:status ");
				finder.setParam("status",userCard.getStatus());
			}
			if(null!=userCard.getType()){
				finder.append(" and tuc.type=:type ");
				finder.setParam("type",userCard.getType());
			}
			
			Date start = userCard.getStartTime(), end = userCard.getEndTime();
			if (null != userCard.getStartTime() && null != userCard.getEndTime()) {
				finder.append(" AND tuc.createTime BETWEEN :s AND :e");
				finder.setParam("s", userCard.getStartTime());
				finder.setParam("e", userCard.getEndTime());
			}
			
			
			 return super.findListDataByFinder(finder,page,clazz,new UserCard());
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
	public List<UserCard> findUserCard(Page page,UserCard userCard)throws Exception {
			Finder finder=new Finder("select * from t_user_card where userId=:userId ");
			if( userCard.getStatus()!=null&&!"".equals( userCard.getStatus())){
				finder.append("and status=:status ");
				finder.setParam("status", userCard.getStatus());
				finder.append(" and endTime>:endTime ");
				finder.setParam("endTime", new Date());
			}
			List<UserCard> userCards =queryForList(finder, UserCard.class, page);
			//List<UserCard> userCards = queryForListByEntity(userCard,page);
			//若是传有期限期限限制，则需要把无期限限制的加入返回数据中
			return userCards;
	}
	@Override
	public String sendUserCard(UserCard userCard) throws Exception {
		String result = "";
		if(new Date().before(userCard.getStartTime())  ){
			if("1".equals(userCard.getInputType())){//给单个用户发红包
				Finder finder = new Finder("select * from t_app_user where phone=:phone ");
				finder.setParam("phone",userCard.getPhone());
				List<AppUser> list =  appUserService.queryForList(finder,AppUser.class);
				if(null!=list&&list.size()>0){
					UserCard uc = new UserCard();
					uc.setUserId(list.get(0).getId());
					uc.setStatus(1);
					uc.setCreateTime(new Date());
					uc.setName(userCard.getName());
					uc.setType(userCard.getType());
					uc.setStartTime(userCard.getStartTime());
					uc.setEndTime(userCard.getEndTime());
					uc.setLimitMoney(userCard.getLimitMoney());
					if(1==userCard.getType()){
						uc.setMoney(userCard.getMoney());
					}
					uc.setHotelId(userCard.getHotelId());
					uc.setBrandId(userCard.getBrandId());
					uc.setCardType(1);
					result = save(uc);
					List<Integer> userIds = new ArrayList<Integer>();
					userIds.add(list.get(0).getId());
					jPushService.notify(12,null,userIds);
				}else {
					throw new NoUserException();
				}
			}else if("3".equals(userCard.getInputType())){//后台导入excel,读取里面的手机号
					List<String> phones = new ArrayList<>();
					List<Integer> userIds = new ArrayList<Integer>();
					//读取excel文件，将文件里的手机号读取出来
					File file = new File(userCard.getFileUrl().replace("http://114.215.130.18:22222","/webdata/app"));
					List<Cell[]> cells = ExcelUtils.getExcle(file);
					if(null!=cells){
						for (int i=1;i<cells.size();i++){
							phones.add(cells.get(i)[0].getContents());
						}
					}
					Finder finder = new Finder("select * from t_app_user where phone in (:phones) ");
					finder.setParam("phones",phones);
					List<AppUser> list =  appUserService.queryForList(finder,AppUser.class);
					List<UserCard> userCards = new ArrayList<>();
					if(null!=list && list.size()>0){
//						if(list.size()!=phones.size()){
//							throw new NoUserException();
//						}
						for (AppUser appUser:list){
							UserCard uc = new UserCard();
							uc.setUserId(appUser.getId());
							uc.setStatus(1);
							uc.setCreateTime(new Date());
							uc.setName(userCard.getName());
							uc.setType(userCard.getType());
							uc.setStartTime(userCard.getStartTime());
							uc.setEndTime(userCard.getEndTime());
							uc.setLimitMoney(userCard.getLimitMoney());
							if(1==userCard.getType()){
								uc.setMoney(userCard.getMoney());
							}
							uc.setBrandId(userCard.getBrandId());
							uc.setHotelId(userCard.getHotelId());
							uc.setCardType(1);
							userCards.add(uc);
							userIds.add(appUser.getId());
						}
						save(userCards);
						jPushService.notify(12,null,userIds);
					}

			}else{//全部发放
				Finder finder = new Finder("select * from t_app_user ");
				List<AppUser> list =  appUserService.queryForList(finder,AppUser.class);
				if(null!=list && list.size()>0){
					for (AppUser appUser:list){
						UserCard uc = new UserCard();
						uc.setUserId(appUser.getId());
						uc.setStatus(1);
						uc.setCreateTime(new Date());
						uc.setName(userCard.getName());
						uc.setType(userCard.getType());
						uc.setStartTime(userCard.getStartTime());
						uc.setEndTime(userCard.getEndTime());
						uc.setLimitMoney(userCard.getLimitMoney());
						if(1==userCard.getType()){
							uc.setMoney(userCard.getMoney());
						}
						uc.setBrandId(userCard.getBrandId());
						uc.setHotelId(userCard.getHotelId());
						uc.setCardType(1);
						result =save(uc);
					}
					jPushService.notify(12,null,null);

				}
			}
		}else{
			throw  new CardTimeException();
		}
		return result;
	}

	@Override
	public Map<String, Object> count(UserCard userCard) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();

		try {
			Finder finder=new Finder("select count(1) num from t_user_card where userId=:userId and status=1041 and hotelId is null ");
			finder.setParam("userId", userCard.getUserId());
			map.put("cardNum", queryForObject(finder).get("num"))  	;
			
			finder=new Finder("select count(1) num from t_user_card where userId=:userId and status=1041 and hotelId is  not null ");
			finder.setParam("userId", userCard.getUserId());
			map.put("hotelCardNum", queryForObject(finder).get("num")) ;
			
			finder=new Finder("select count(1) num from t_app_orders where userid=:userId and status=1033 and orderType =4 and isInvoice=0 ");
			finder.setParam("userId", userCard.getUserId());
			map.put("invoiceNum", queryForObject(finder).get("num")) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

}
