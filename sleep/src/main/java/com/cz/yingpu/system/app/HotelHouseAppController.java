package com.cz.yingpu.system.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.HotelHouse;

@Controller
@RequestMapping("/app/hotelHouse")
public class HotelHouseAppController extends com.cz.yingpu.system.web.HotelHouseController{
	@RequestMapping("list/app/json")
	@ResponseBody
	public ReturnDatas list(HotelHouse hotelHouse,HttpServletRequest request,Model model){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			returnDatas	=super.listjson(request,model, hotelHouse);
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
			returnDatas=super.lookjson( request);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
}
