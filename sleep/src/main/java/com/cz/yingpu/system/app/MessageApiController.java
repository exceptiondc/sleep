package com.cz.yingpu.system.app;

import com.cz.yingpu.frame.annotation.SecurityApi;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.Message;
import com.cz.yingpu.system.service.IMessageService;
import com.cz.yingpu.system.web.MessageController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:46
 * @see com.cz.yingpu.system.web.Message
 */
@Controller
@RequestMapping(value="/app/message")
public class MessageApiController extends MessageController {
	@Resource
	private IMessageService messageService;


	/**
	 * json数据,为APP提供数据
	 *
	 * @param request
	 * @param model
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@Override
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,Message message) throws Exception{
		return super.listjson(request, model, message);
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@Override
	@RequestMapping(value = "/look/json")
	@SecurityApi
	public @ResponseBody
	ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {

		return super.lookjson(model, request, response);

	}


	/**
	 * 新增/修改 操作吗,返回json格式数据
	 *
	 */
	@Override
	@RequestMapping("/update")
	@SecurityApi
	public @ResponseBody
	ReturnDatas saveorupdate(Model model,Message message,HttpServletRequest request,HttpServletResponse response) throws Exception{

		return super.saveorupdate(model, message, request, response);

	}


	/**
	 * 删除操作
	 */
	@Override
	@RequestMapping(value="/delete/json")
	@SecurityApi
	public @ResponseBody ReturnDatas delete(HttpServletRequest request) throws Exception {


		return super.delete(request);
	}

	/**
	 * 删除多条记录
	 *
	 */
	@Override
	@RequestMapping("/delete/more")
	@SecurityApi
	public @ResponseBody
	ReturnDatas deleteMore(HttpServletRequest request, Model model) {

		return super.deleteMore(request, model);


	}

	/**
	 * 将未读置为已读
	 * @author wj
	 */
	@Override
	@RequestMapping(value="/updatestatus/json")
	@SecurityApi
	public @ResponseBody ReturnDatas updateStatus(HttpServletRequest request) throws Exception {

		return super.updateStatus(request);
	}

}
