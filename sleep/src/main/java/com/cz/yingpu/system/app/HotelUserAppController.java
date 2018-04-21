package com.cz.yingpu.system.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.web.HotelUserController;

@Controller
@RequestMapping("/app/houseUser")
public class HotelUserAppController extends HotelUserController{
	
	@RequestMapping("look/json")
	@ResponseBody
	public ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			returnDatas=super.lookjson(model, request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
}
