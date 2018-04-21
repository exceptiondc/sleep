package com.cz.yingpu.system.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.Facility;
import com.cz.yingpu.system.entity.Hotel;
import com.cz.yingpu.system.entity.HotelHouse;
import com.cz.yingpu.system.service.IHotelHouseService;
import com.cz.yingpu.system.service.IHotelUserService;
import com.cz.yingpu.system.web.HotelController;

@Controller
@RequestMapping("/app/hotel")
public class HotelAppController extends HotelController{
	

	@Resource
	private IHotelUserService hotelUserService;
	
	
	@RequestMapping("hotelFacility/list")
	@ResponseBody
	public ReturnDatas hotelFacility(HttpServletRequest request) 
			throws Exception {
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			List<Facility> flist= hotelUserService.queryForList(new Finder("select * from t_facility"), Facility.class);
			returnDatas.setData(flist);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
	
	@RequestMapping("list/app/json")
	@ResponseBody
	public ReturnDatas list(Hotel hotel,HttpServletRequest request,Date startTime,Date endTime){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			request.setAttribute("hotelHouseStatus", 1001);
			returnDatas	=super.listjson(request, hotel, startTime, endTime);
			returnDatas.setQueryBean(hotelUserService.queryForList(new Finder("select * from t_facility"), Facility.class));
			/*new Finder("select * from t_facility");*/
		} catch (Exception e) {
			// TODO: handle exception        
			e.printStackTrace();
		}
		return returnDatas;
	}
	@RequestMapping("look/app/json")
	@ResponseBody
	public ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			request.setAttribute("app", "true");
			returnDatas=super.lookjson(model, request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
	
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Hotel hotel, Date startTime, Date endTime)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		page.setOrder("`createTime`");
		page.setSort("DESC");
		
		Map<String, String> paramMap = getParameterMap();
		String priceRange = paramMap.get("priceRange");
		String keyword = paramMap.get("keyword");
		String priceAndLevel = paramMap.get("priceAndLevel");
		String roomType = paramMap.get("roomType");
		String starLevel = paramMap.get("starLevel");
		String hotelType = paramMap.get("hotelType");

		Finder finder = new Finder("SELECT h.*,hb.name brandName FROM t_hotel h, t_hotelHouse hh, t_hotel_user hu")
							.append(" left join t_hotelBrand  hb  on h.brandId=hb.id WHERE 1 = 1")
							.append(" AND hu.totelHouseId = hh.id");

		if (StringUtils.isNotBlank(hotel.getName())) {
			finder.append(" AND h.name LIKE :name");
			finder.setParam("name", "%" + hotel.getName() + "%");
		}
		if (StringUtils.isNotBlank(hotel.getPhone())) {
			finder.append(" AND h.phone = :phone");
			finder.setParam("phone", hotel.getPhone());
		}
		if (startTime != null && endTime != null) {
			finder.append(" AND ((hu.reservationStartTime < :start AND hu.reservationEndTime < :start) ")
				  .append(" OR (hu.reservationStartTime > :start AND hu.reservationEndTime > :end))")
				  .setParam("start", startTime)
				  .setParam("end", endTime);
		}
		if (StringUtils.isNotBlank(keyword)) {
			String key = "%s" + keyword + "%s";
			finder.append(" AND (h.searchKey LIKE :keyword OR hb.name LIKE :brandName OR h.name LIKE :hotelName)");
			finder.setParam("keyword", key)
				  .setParam("brandName", key)
				  .setParam("hotelName", key);
		}
		if (StringUtils.isNotBlank(priceRange) && priceRange.contains(",")) {
			String prices[] = priceRange.split(",");
			finder.append(" AND hh.price BETWEEN :minPrice AND :maxPrice");
			finder.setParam("minPrice", prices[0]);
			finder.setParam("maxPrice", prices[1]);
		}
		if (StringUtils.isNotBlank(roomType)) {
			finder.append(" AND hh.type = :roomType");
			finder.setParam("roomType", roomType);
		}
		if (StringUtils.isNotBlank(starLevel)) {
			finder.append(" AND FIND_IN_SET(h.level, :startLevel)");
			finder.setParam("startLevel", starLevel);
		}
		if (StringUtils.isNotBlank(hotelType)) {
			finder.append(" AND = :hotelType");
			finder.setParam("hotelType", hotelType);
		}
		
		List<Hotel> datas=hotelService.findListDataByFinder(finder,page,Hotel.class,Hotel.class.newInstance());
		returnObject.setQueryBean(hotel);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
}
