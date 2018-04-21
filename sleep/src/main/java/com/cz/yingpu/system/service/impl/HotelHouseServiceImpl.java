package com.cz.yingpu.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.StringUtil;
import com.cz.yingpu.system.entity.HotelHouse;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.IHotelHouseService;
@Service("hotelHouseService")
public class HotelHouseServiceImpl extends BaseSpringrainServiceImpl  implements IHotelHouseService{

	@Override
	public List<Map<String, Object>> list(Page page, HotelHouse hotelHouse) throws Exception{
		Finder finder=new Finder("select hh.*,h.name,s.name  statusName from t_hotelHouse hh  inner join t_hotel h on hh.hotelid=h.id  inner join t_status s on hh.status=s.statusCode   where 1=1");
		//用描述传递酒店名称
		if(StringUtil.isValid(hotelHouse.getDescr())){
			finder.append(" and h.name like :name");
			finder.setParam("name", "%"+hotelHouse.getDescr()+"%");
		}
		if(hotelHouse.getHotelid()!=null&&hotelHouse.getHotelid()!=0){
			finder.append(" and hh.hotelid =:hotelid");
			finder.setParam("hotelid",hotelHouse.getHotelid() );
		}
		if(hotelHouse.getStatus()!=null&&hotelHouse.getStatus()!=0){
			finder.append(" and hh.status =:status");
			finder.setParam("status",hotelHouse.getStatus() );
		}
		return	queryForList(finder, page);
	}

	@Override
	public HotelHouse findHotelHouseById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return super.findById(id, HotelHouse.class);
	}

}
