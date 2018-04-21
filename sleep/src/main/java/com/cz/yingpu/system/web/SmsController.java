package com.cz.yingpu.system.web;

import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
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

import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.service.IBaseService;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.SMSTemplate;
import com.cz.yingpu.system.entity.Sms;
import com.cz.yingpu.system.exception.NoUserException;
import com.cz.yingpu.system.exception.ParameterErrorException;
import com.cz.yingpu.system.exception.PhoneExistException;
import com.cz.yingpu.system.exception.UserExistException;
import com.cz.yingpu.system.service.ISmsService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-22 11:49:23
 * @see com.cz.yingpu.system.web.Sms
 */
@Controller
@RequestMapping(value="/system/sms")
public class SmsController  extends BaseController {
	
	@Resource
	private ISmsService smsService;
	
	@Resource
	private IBaseService smsTemplateService; 
	
	private String listurl="/system/sms/smsList";
	
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param sms
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,Sms sms) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, sms);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param sms
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,Sms sms) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<Sms> datas=smsService.findListDataByFinder(null,page,Sms.class,sms);
			returnObject.setQueryBean(sms);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,Sms sms) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = smsService.findDataExportExcel(null,listurl, page,Sms.class,sms);
		String fileName="sms"+GlobalStatic.excelext;
		downFile(response, file, fileName,true);
		return;
	}
	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/sms/smsLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		  String  strId=request.getParameter("id");
		  Integer id=null;
		  if(StringUtils.isNotBlank(strId)){
			 id= Integer.valueOf(strId.trim());
		  Sms sms = smsService.findSmsById(id);
		   returnObject.setData(sms);
		}else{
		returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
		
	}
	
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody
	ReturnDatas saveorupdate(Model model,Sms sms,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
		
		
			smsService.saveorupdate(sms);
			
		} catch (ParameterErrorException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("参数缺失");
		}catch (PhoneExistException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("该手机号已注册");
		}catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	
	}
	
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/sms/smsCru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody ReturnDatas delete(HttpServletRequest request) throws Exception {

			// 执行删除
		try {
		  String  strId=request.getParameter("id");
		  Integer id=null;
		  if(StringUtils.isNotBlank(strId)){
			 id= Integer.valueOf(strId.trim());
				smsService.deleteById(id,Sms.class);
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
	}
	
	/**
	 * 删除多条记录
	 * 
	 */
	@RequestMapping("/delete/more")
	public @ResponseBody
	ReturnDatas deleteMore(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if(StringUtils.isBlank(records)){
			 return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			smsService.deleteByIds(ids,Sms.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
		
		
	}


	/**
	 * 获取验证码
	 * @author wj
	 */
	@RequestMapping(value = "/send/json")
//	@SecurityApi
	public @ResponseBody
	ReturnDatas sendjson(Model model,HttpServletRequest request,HttpServletResponse response,Sms sms) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Sms smsRecord = null ;
		try {
			smsRecord = smsService.sendCode(sms);
		}catch (UserExistException e){
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("该用户已存在");
		}catch (NoUserException e){
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("该用户不存在");
		}
		returnObject.setData(smsRecord);
		return returnObject;

	}

	@RequestMapping(value = "/sendMessage/json")
//	@SecurityApi
	public @ResponseBody
	ReturnDatas sendMessage(Model model,HttpServletRequest request,HttpServletResponse response,Sms sms) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String content = request.getParameter("content");

		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;

	}

	@RequestMapping("/send")
	public String send(HttpServletRequest request, Model model)
			throws Exception {
		return "/send/send";
	}


	@RequestMapping("/send-sms-view/json")
	public String sendSMSMultiuser(HttpServletRequest request, Model model) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		
		try {
			rt.setData(smsTemplateService.queryForList(new Finder("SELECT * FROM t_sms_template"), SMSTemplate.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute(GlobalStatic.returnDatas, rt);
		return "send/sendSMS";
	}
	
	
	@RequestMapping("/send-sms")
	@ResponseBody
	public Object sendSMSMultiuser(HttpServletRequest request, String[] variable) {
		Map<String, String> map = new HashMap<>();
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements(); ) {
			String key = e.nextElement();
			try {
				map.put(key, request.getParameter(key));
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		try {
			smsService.sendsms(map, variable);
		}
		catch (Exception e) {
			e.printStackTrace();
			rt.setStatus(ReturnDatas.ERROR);
		}
		
		return rt;
	}
}
