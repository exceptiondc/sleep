package com.cz.yingpu.system.app;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.frame.annotation.UserCheck;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.UserCard;
import com.cz.yingpu.system.service.IUserCardService;
import com.cz.yingpu.system.web.UserCardController;

@Controller
@RequestMapping("/app/userCard")
public class UserCardAppController extends UserCardController{
	
	
	@Resource
	private IUserCardService userCardService;
	
	@RequestMapping("list/app/json")
	@ResponseBody
	public ReturnDatas list(UserCard  card,HttpServletRequest request,Model model){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			returnDatas	=super.listjson(request, model,card);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
	
	@RequestMapping("count/json")
	@ResponseBody
	@UserCheck
	public ReturnDatas count(UserCard  card,HttpServletRequest request,Model model){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			
			returnDatas	.setData(userCardService.count(card));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
}
