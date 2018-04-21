package com.cz.yingpu.system.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.frame.annotation.UserCheck;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.UserAccountHistory;
import com.cz.yingpu.system.entity.UserCard;
import com.cz.yingpu.system.web.UserAccountHistoryController;

@Controller
@RequestMapping("/app/userAccountHistory")
public class UserAccountHistoryAppController extends UserAccountHistoryController{
		
	@RequestMapping("list/app/json")
	@ResponseBody
	@UserCheck
	public ReturnDatas list(UserAccountHistory userAccountHistory,HttpServletRequest request,Model model){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			returnDatas	=super.listjson(request, model,userAccountHistory);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
}
