package com.cz.yingpu.system.service;

import java.util.List;
import java.util.Map;

import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.HotelHouse;
import com.cz.yingpu.system.entity.HotelUser;

public interface IHotelUserService extends IBaseSpringrainService {
	List<Map<String, Object>> list(Page page,HotelUser hotelUser) throws Exception;
	HotelUser findHotelUserById(Integer id) throws Exception;
}
