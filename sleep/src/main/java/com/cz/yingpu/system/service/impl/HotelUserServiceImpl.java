package com.cz.yingpu.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.StringUtil;
import com.cz.yingpu.system.entity.HotelUser;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.IHotelUserService;
@Service("hotelUserService")
public class HotelUserServiceImpl extends BaseSpringrainServiceImpl  implements IHotelUserService{

	@Override
	public List<Map<String, Object>> list(Page page, HotelUser hotelUser) throws Exception{
		page.setOrder("hu.createTime");
		page.setSort("DESC");
		Finder finder=new Finder("SELECT  hu.*,au.`phone`,au.`realName`,au.`idCard`,ao.`orderNumber`,s.`name` statusName,hh.*,h.`name` hotelName FROM `t_hotel_user`  hu,`t_app_user` au,`t_app_orders` ao,`t_status` s,`t_hotelHouse` hh,`t_hotel` h WHERE hu.userid=au.`id` AND hu.`orderid`= ao.`id` AND hu.`hotelHouseId`= hh.id AND hu.`status`= s.`statusCode` AND hu.`hotelid`=h.`id` ");
		if(StringUtil.isValid(hotelUser.getPhone())){
			finder.append(" and au.phone=:phone");
			finder.setParam("phone", hotelUser.getPhone());
		}
		if(StringUtil.isValid(hotelUser.getHotelName())){
			finder.append(" and h.name =:name");
			finder.setParam("name", "%"+hotelUser.getHotelName()+"%");
		}
		if(hotelUser.getStatus()!=null&&hotelUser.getStatus()>0){
			finder.append(" and hu.status=:status");
			finder.setParam("status", hotelUser.getStatus());
		}
		if(hotelUser.getStartTime()!=null){
			finder.append(" and hu.createTime >= :startTime");
			finder.setParam("startTime", hotelUser.getStartTime());
		}
		if(hotelUser.getEndTime()!=null){
			finder.append(" and hu.createTime <= :endTime");
			finder.setParam("endTime", hotelUser.getEndTime());
		}
		if(hotelUser.getHotelid()!=null&&hotelUser.getHotelid()>0){
			finder.append(" and hu.hotelid = :hotelid");
			finder.setParam("hotelid", hotelUser.getHotelid());
		}
		return	queryForList(finder, page);
	}



	@Override
	public HotelUser findHotelUserById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return super.findById(id, HotelUser.class);
	}

}
