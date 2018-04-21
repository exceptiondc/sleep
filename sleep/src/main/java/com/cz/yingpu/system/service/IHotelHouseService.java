package com.cz.yingpu.system.service;

import java.util.List;
import java.util.Map;

import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.HotelHouse;

public interface IHotelHouseService extends IBaseSpringrainService {
	List<Map<String, Object>> list(Page page,HotelHouse hotelHouse) throws Exception;
	HotelHouse findHotelHouseById(Integer id) throws Exception;
}
