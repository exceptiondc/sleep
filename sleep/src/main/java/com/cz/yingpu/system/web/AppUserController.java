package com.cz.yingpu.system.web;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.cz.yingpu.frame.util.CopyUtil;
import com.cz.yingpu.frame.util.CryptAES;
import com.cz.yingpu.frame.util.ExportExcelUtil;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.entity.ExportAppUserBean;
import com.cz.yingpu.system.entity.Sms;
import com.cz.yingpu.system.entity.SysSysparam;
import com.cz.yingpu.system.exception.ParameterErrorException;
import com.cz.yingpu.system.exception.PasswordFailException;
import com.cz.yingpu.system.exception.PhoneExistException;
import com.cz.yingpu.system.exception.PhoneNotExistException;
import com.cz.yingpu.system.exception.VerifyCodeFailException;
import com.cz.yingpu.system.service.IAppUserService;
import com.cz.yingpu.system.service.ISmsService;
import com.cz.yingpu.system.service.ISysSysparamService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:44
 * @see com.cz.yingpu.system.web.AppUser
 */
@Controller
@RequestMapping(value="/system/appuser")
public class AppUserController  extends BaseController {
	@Resource
	private IAppUserService appUserService;
	@Resource
	private ISysSysparamService sysSysparamService ;
	@Resource
	private ISmsService smsService ;
	
	private String listurl="/appuser/appuserList";
	   

	
	
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param appUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,AppUser appUser) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, appUser);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param appUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,AppUser appUser) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<AppUser> datas=appUserService.findListDataByFinder(null,page,AppUser.class,appUser);
		//查询所有的或者筛选的用户总金额，将用户的总金额和天天存吧的总金额返回给页面
			/*AppUser au = appUserService.getTotalBalance(appUser);
			appUser.setTotalBalance(au.getTotalBalance());
			appUser.setTotalYebBalance(au.getTotalYebBalance());
			appUser.setTotalFreezeYebBalance(au.getTotalFreezeYebBalance());*/
			returnObject.setQueryBean(appUser);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * json数据,为APP提供数据
	 *
	 * @param request
	 * @param model
	 * @param appUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/partnerList")
	public String partnerList(HttpServletRequest request, Model model,AppUser appUser) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(appUser.getPhone())){
			map.put("phone",appUser.getPhone());
		}
		if(StringUtils.isNotBlank(appUser.getRealName())){
			map.put("userName",appUser.getRealName());
		}
		List<AppUser> datas=appUserService.partnerList(page,map);
		returnObject.setQueryBean(appUser);
		returnObject.setPage(page);
		returnObject.setData(datas);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/appuser/partnerList";
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,AppUser appUser) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
		page.setPageSize(1000000000);
//		File file = appUserService.findDataExportExcel(null,listurl, page,AppUser.class,appUser);
//		String fileName="appUser"+GlobalStatic.excelext;
//		downFile(response, file, fileName,true);
		ExportExcelUtil excel=new ExportExcelUtil();
		List<Object> objects = new ArrayList<>();
		List<AppUser> datas=appUserService.findListDataByFinder(null,page,AppUser.class,appUser);
		if(null != datas && datas.size() > 0){
			for(AppUser au :datas){
				au.setTotalInvestAmount(au.getTotalAmount());
				ExportAppUserBean exportAppUserBean = (ExportAppUserBean) CopyUtil.copyAndCreate(ExportAppUserBean.class,au);
				Object object = new Object();
				object = exportAppUserBean;
				objects.add(object);
			}
		}
		String[] Title={"用户id","手机号","头像","性别","昵称","邀请码","邀请码网页","微信号","QQ号",
				"真实姓名","身份证号","email","是否实名认证","账户余额","注册时间",
				"实名认证时间","最后登录时间","余额1","余额2","是否开启推送","邀请人","是否一周免签到",
				"免费签到到期时间","Ios/android","所属城市","城市id",
				"开户行行别ID","银行卡号","邀请人数"};
		ExportExcelUtil.exportExcel(response,"appUser.xls",Title, objects,1);
		return;
	}
	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/appuser/appuserLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		try{
			String  strId=request.getParameter("id");
			Integer id=null;
			if(StringUtils.isNotBlank(strId)){
				id= Integer.valueOf(strId.trim());
				AppUser appUser = appUserService.findAppUserById(id);
				returnObject.setData(appUser);
			}else{
				throw  new ParameterErrorException();
			}
		}catch (ParameterErrorException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("参数缺失");
		}catch (Exception e){
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
		
	}
	
	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/txzt/json")
	public @ResponseBody
	ReturnDatas txzt(Model model,HttpServletRequest request,HttpServletResponse response, AppUser user) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		try{
//			Finder f = new Finder("select IF(SUM(money) IS NULL,0.00,SUM(money)) as total from t_user_account_history where 1=1 and type=:type and status=:status and userId=:userId");
//			f.setParam("userId", user.getId());
//			f.setParam("type", "2");
//			f.setParam("status", "1");
//			List<Map<String, Object>> list = userAccountHistoryService.queryForList(f);

			HashMap<String, Object> map = new HashMap<String, Object>();
			
			//累计收益
//			map.put("tixian", list.get(0).get("total")==null ? "0.00" :list.get(0).get("total"));
			map.put("tixian", "0.00");
			returnObject.setData(map);
		}catch (ParameterErrorException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("参数缺失");
		}catch (Exception e){
			e.printStackTrace();
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
	ReturnDatas saveorupdate(Model model,AppUser appUser,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage("修改成功");
		try {


			returnObject.setData(appUserService.modify(appUser));
			
		}catch (PasswordFailException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("密码错误");
		}catch (PhoneExistException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("该号已被绑定");
		}catch (PhoneNotExistException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("该用户不存在");
		} catch (VerifyCodeFailException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("验证码错误！！");
		} catch (ParameterErrorException e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("参数缺失");
		} catch (Exception e) {
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
		return "/system/appuser/appuserCru";
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
				appUserService.deleteById(id,AppUser.class);
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
			appUserService.deleteByIds(ids,AppUser.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
		
	}


	@RequestMapping(value = "/changePwd/json")
	public @ResponseBody
	ReturnDatas changePwd(HttpServletRequest request,Integer id,String oldPwd,String newPwd) throws  Exception {
			if(id == null)
				return  new ReturnDatas(ReturnDatas.ERROR,"内部错误") ;
			if(StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd))
				return  new ReturnDatas(ReturnDatas.ERROR,"请填写完整信息") ;
			AppUser user = appUserService.findAppUserById(id) ;
			if(user == null)
				return  new ReturnDatas(ReturnDatas.ERROR,"该用户不存在") ;
			if(user.getPassword() != null && user.getPassword().equals(CryptAES.AES_Encrypt(oldPwd))){  //原密码匹配
				user.setPassword(CryptAES.AES_Decrypt(newPwd));
				appUserService.update(user,true) ;
			}else {
				return  new ReturnDatas(ReturnDatas.ERROR,"原密码不正确") ;
			}
			return  new ReturnDatas(ReturnDatas.SUCCESS,"成功") ;

	}
	@RequestMapping(value = "/findPwd/json")
	public @ResponseBody
	ReturnDatas findPwd(HttpServletRequest request,String phone,String code,String newPwd) throws  Exception {
			if(StringUtils.isBlank(code) || StringUtils.isBlank(newPwd) || StringUtils.isBlank(phone))
				return  new ReturnDatas(ReturnDatas.ERROR,"请填写完整信息") ;
			AppUser userQuery = new AppUser() ;
			userQuery.setPhone(phone);
			AppUser user = appUserService.queryForObject(userQuery) ;
			if(user == null)
				return  new ReturnDatas(ReturnDatas.ERROR,"该用户不存在") ;
			Sms sms = new Sms() ;
			sms.setContent(code);
			sms.setPhone(user.getPhone());
			sms.setType(4);
			Sms smsResult = smsService.queryForObject(sms) ;
			if(smsResult == null)
				return  new ReturnDatas(ReturnDatas.ERROR,"验证码错误") ;
			user.setPassword(CryptAES.AES_Encrypt(newPwd));
			appUserService.update(user,true) ;
			return  new ReturnDatas(ReturnDatas.SUCCESS,"成功") ;

	}

	@RequestMapping(value = "/changeEWM/json")
	public void changeEWM(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		appUserService.changeEWM();
	}

	@RequestMapping(value = "/sendMessage/json")
	public void sendMessage(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		appUserService.sendMessage();
	}


	/**
	 * 设置合伙人操作
	 */
	@RequestMapping(value="/isPartner")
	public @ResponseBody ReturnDatas isPartner(HttpServletRequest request) throws Exception {

		// 执行删除
		try {
			String  strId=request.getParameter("id");
			String  type=request.getParameter("type");
			Integer id=null;
			if(StringUtils.isNotBlank(strId)){
				id= Integer.valueOf(strId.trim());
				Integer result =appUserService.isPartner(id,type);
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@RequestMapping(value = "/partnerFenxiaoList/json")
	public @ResponseBody
	ReturnDatas partnerFenxiaoList(HttpServletRequest request,Integer userId,String month) throws  Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			List list = appUserService.partnerFenxiaoList(userId,month);
			if(null!=list && list.size()>0){
				returnObject.setData(list);
			}else{
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage(MessageUtils.UPDATE_ERROR);
			}

		}catch (Exception e){
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}


	@RequestMapping(value = "/fenxiaoTotalList/json")
	public @ResponseBody
	ReturnDatas fenxiaoTotalList(HttpServletRequest request,Integer userId,String month) throws  Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			Map<String,Object> list = appUserService.fenxiaoTotalList(userId,month);
			if(null!=list && list.size()>0){
				returnObject.setData(list);
			}else{
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage(MessageUtils.UPDATE_ERROR);
			}

		}catch (Exception e){
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}


	/**
	 * 设置合伙人操作
	 */
	@RequestMapping(value="/sendMoney")
	public @ResponseBody ReturnDatas sendMoney(HttpServletRequest request) throws Exception {

		// 执行删除
		try {
			String  strId=request.getParameter("id");
			String  orderId=request.getParameter("orderId");
			Integer id=null;
			if(StringUtils.isNotBlank(strId)){
				id= Integer.valueOf(strId.trim());
				Integer result =appUserService.sendMoney(id,Integer.parseInt(orderId));
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@RequestMapping(value = "/fenxiaoList/json")
	public @ResponseBody
	ReturnDatas fenxiaoList(HttpServletRequest request,Integer userId,String phone,String userName) throws  Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(StringUtils.isNotBlank(phone)){
				map.put("phone",phone);
			}
			if(StringUtils.isNotBlank(userName)){
				map.put("userName",userName);
			}
			String level = request.getParameter("level");
			if(StringUtils.isNotBlank(level)){
				map.put("level",level);
			}
			List list = appUserService.fenxiaoList(userId,map);
			if(null!=list && list.size()>0){
				returnObject.setData(list);
			}else{
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage(MessageUtils.UPDATE_ERROR);
			}

		}catch (Exception e){
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

/*	@RequestMapping("/exportFenxiaoList/export")
	public void exportFenxiaoList(HttpServletRequest request,HttpServletResponse response,Integer userId,String phone,String userName) throws  Exception {
		Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(phone)){
				map.put("phone",phone);
			}
			if(StringUtils.isNotBlank(userName)){
				map.put("userName",userName);
			}
			String level = request.getParameter("level");
			if(StringUtils.isNotBlank(level)){
				map.put("level",level);
			}
			List list = appUserService.fenxiaoList(userId,map);
			ExportExcelUtil excel=new ExportExcelUtil();
			List<Object> objects = new ArrayList<>();
			if(null != list && list.size() > 0){
				for(int i=0;i<list.size();i++){
					ExportFenxiaoUserBean exportFenxiaoUserBean = (ExportFenxiaoUserBean) CopyUtil.copyAndCreate(ExportFenxiaoUserBean.class,list.get(i));
					Object object = new Object();
					object = exportFenxiaoUserBean;
					objects.add(object);
				}
			}
			String[] Title={"真实姓名","手机号","级别","邀请人数"};
			ExportExcelUtil.exportExcel(response,"fenxiaoUser.xls",Title, objects,1);

	}*/

	@RequestMapping(value = "/getInfo/json")
	public @ResponseBody
	ReturnDatas getInfo(Model model,HttpServletRequest request) throws  Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			//获取投资协议模板
			SysSysparam sysparam = new SysSysparam() ;
			sysparam.setCode("protocolTemplate");
			sysparam = sysSysparamService.queryForObject(sysparam) ;
			map.put("protocolTemplate",sysparam.getValue().replace("微软雅黑","Microsoft YaHei"));
			//获取用户信息
			String  strId=request.getParameter("userId");
			if(StringUtils.isNotBlank(strId)){
				AppUser appUser = appUserService.findById(Integer.parseInt(strId),AppUser.class);
				if(null != appUser){
					map.put("realName",appUser.getRealName());
					map.put("idCard",appUser.getIdCard());
				}
			}
			returnObject.setData(map);
		}catch (Exception e){
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
//		model.addAttribute(GlobalStatic.returnDatas, returnObject);
//		return "/web/template";
	}


	@RequestMapping(value = "/addYebBalance/json")
	public @ResponseBody
	ReturnDatas addYebBalance(Model model,HttpServletRequest request,Integer userId,Double money) throws  Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			Integer result = appUserService.addYebBalance(userId,money);
			if(null==result){
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage("修改失败");
			}
//			returnObject.setData(map);
		}catch (Exception e){
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}





}
