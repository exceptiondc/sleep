package com.cz.yingpu.system.app;

import com.cz.yingpu.frame.annotation.UserCheck;
import com.cz.yingpu.frame.dao.IBaseJdbcDao;
import com.cz.yingpu.frame.util.CaptchaUtils;
import com.cz.yingpu.frame.util.CryptAES;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.QRcodeUtils;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.SMSUtil;
import com.cz.yingpu.frame.util.StringUtil;
import com.cz.yingpu.frame.util.Utils;
import com.cz.yingpu.servlet.AdminFileUpload;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.entity.Favorite;
import com.cz.yingpu.system.entity.Footprint;
import com.cz.yingpu.system.entity.SMSTemplate;
import com.cz.yingpu.system.entity.Sms;
import com.cz.yingpu.system.entity.Token;
import com.cz.yingpu.system.exception.NoUserException;
import com.cz.yingpu.system.exception.ParameterErrorException;
import com.cz.yingpu.system.exception.PhoneExistException;
import com.cz.yingpu.system.exception.PhoneNotExistException;
import com.cz.yingpu.system.exception.VerifyCodeFailException;
import com.cz.yingpu.system.service.IAppUserService;
import com.cz.yingpu.system.service.ISmsService;
import com.cz.yingpu.system.web.AppUserController;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/app/appUsers")
public class AppUsersAppController extends AppUserController{
	
	public final static String SMS_CODE_KEY = "SMS_CODE_KEY";
	
	@Resource
	private ISmsService smsService;
	
	@Resource
	private IBaseJdbcDao baseSpringrainDao;
	
	@Resource
	private IAppUserService appUserService;
	
	@Resource
	private CacheManager cacheManager;

	@RequestMapping("/login/json")
	@ResponseBody
	public ReturnDatas login(AppUser appUser,HttpServletRequest request, String code, String loginType) {
		ReturnDatas returnObject=new ReturnDatas(ReturnDatas.SUCCESS);
		HttpSession session = request.getSession();
		
		try {
			Page page = newPage(request);
			AppUser user = null;
			
			if (StringUtils.isNotBlank(loginType)) {
				if ("pwd".equals(loginType)) {
					user = appUserService.login(appUser, page);
				}
				else if ("sms".equals(loginType)) {
					String smsCode = (String) session.getAttribute(SMS_CODE_KEY);
					if (StringUtils.isNotBlank(smsCode) && smsCode.equalsIgnoreCase(code)) {
						user = (AppUser) appUserService.queryForObject(
								new Finder("SELECT * FROM t_app_user WHERE phone = :phone")
										.setParam("phone", appUser.getPhone()), AppUser.class);
					}
					else {
						throw new LoginException("您输入的短信验证码不正确！");
					}
				}else if ("wx".equals(loginType)) {
					user = appUserService.wxLogin(request.getParameter("wxCode"));
				}
				else if ("token".equals(loginType) && StringUtils.isNotBlank(appUser.getToken())) {
					user = appUserService.queryForObject(
							new Finder("SELECT * FROM t_app_user WHERE phone = :phone AND token = :token")
									.setParam("phone", appUser.getPhone()).setParam("token", appUser.getToken()),
											AppUser.class);
				}
			}
			else {
				throw new LoginException("缺少必要的登录信息！");
			}

			if (user == null) {
				throw new LoginException("登录失败，账号或密码错误！");
			}
			
			Cache cache = cacheManager.getCache(GlobalStatic.tokenCacheKey);
			cache.clear();
			
			//给用户生成token，并将token塞到缓存中，以验证token是否失效
			Token token = appUserService.getToken(user.getId());
			user.setToken(token.getToken());
			appUserService.updateValidValue(user);
			user.setPassword(null);
			returnObject.setData(user);
			session.removeAttribute(SMS_CODE_KEY);
		}catch (ParameterErrorException e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("参数缺失");
		}catch (PhoneNotExistException e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("用户名或密码错误");
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(e.getMessage());
		}
		return returnObject;
	}
	
	
	
	/**
	 * 新用户注册
	 *
	 */
	@RequestMapping("/register/json")
	public @ResponseBody
	ReturnDatas register(Model model,AppUser appUser,HttpServletRequest request,HttpServletResponse response, String regType) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.ADD_SUCCESS);
		try {
			Page page = newPage(request);
			appUserService.register(appUser,page, regType);
			AppUser au = new AppUser();
			au.setPhone(appUser.getPhone());
			au.setType("1");
			appUser = appUserService.queryForObject(au);

			Cache cache = cacheManager.getCache(GlobalStatic.tokenCacheKey);
			cache.clear();
			Token token = appUserService.getToken(appUser.getId());
			appUser.setToken(token.getToken());
			appUserService.updateValidValue(appUser);
			appUser.setPassword(null);
			returnObject.setData(appUser);

		}catch (NoUserException e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("邀请人不存在");
		}catch (ParameterErrorException e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("参数缺失");
		}catch (PhoneExistException e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("该手机号已注册");
		}catch (VerifyCodeFailException e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("验证码错误");
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
//			logger.error(errorMessage);
            e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.ADD_FAIL);
		}
		return returnObject;

	} 
	/**
	 * 新用户注册/分享页过来的
	 *
	 */
	@RequestMapping("/reHtml/json")
	public  @ResponseBody
	ReturnDatas registerHTML(Model model, HttpSession session, AppUser appUser, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = new ReturnDatas() ;
		//系统产生的验证码
		String code = (String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		if(StringUtils.isNotBlank(code)){
			code=code.toLowerCase().toString();
		}
		//用户产生的验证码
		String submitCode = WebUtils.getCleanParam(request, GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		if(StringUtils.isNotBlank(submitCode)){
			submitCode=submitCode.toLowerCase().toString();
		}
		//如果验证码不匹配,跳转到登录
		if (StringUtils.isBlank(submitCode) ||StringUtils.isBlank(code)||!code.equals(submitCode)) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("验证码错误！");
			return returnObject ;
		}
		returnObject = register(model,appUser,request,response, request.getParameter("regType")) ;
		return returnObject ;
	}
	
	
	private String getSMSTemplateId(int type) {
		String smsTemplateId = "";
		try {
			SMSTemplate template = baseSpringrainDao.queryForObject(
					new Finder("SELECT * FROM t_sms_template where type = " + type), SMSTemplate.class);
			if (template != null) {
				smsTemplateId = template.getTemplate_id();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return smsTemplateId;
	}
	
	
	@RequestMapping(value = "/send_sms_code/json")
	public @ResponseBody
	ReturnDatas sendSMSCode(HttpServletRequest request,String phone, String code, int type) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		HttpSession session = request.getSession();
		String imageCode = (String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM),
				smsCode = (900000 + (int)(Math.random() * 100000)) + "";
		Sms sms = new Sms() ;
		
		sms.setContent(smsCode);
		sms.setPhone(phone);
		sms.setType(type);
		sms.setCreateTime(new Date());
		System.out.println(session.getId());
		try {
			AppUser user = new AppUser();
			user.setPhone(phone);
			user = baseSpringrainDao.queryForObject(user);
			
			if (StringUtils.isBlank(phone)) {
				throw new IllegalArgumentException("请输入手机号！");	
			}
			
			switch(type) {
				case 1:	// 注册
					if (user != null) {
						throw new IllegalArgumentException("该用户已存在！");
					}
					break;
				
				case 4:	// 找回
				case 7: // 短信登录
					if (user == null) {
						throw new IllegalArgumentException("该用户不存在！");
					}
					break;
			}
			
			if (StringUtils.isBlank(imageCode) || !imageCode.equalsIgnoreCase(code)) {
				throw new IllegalArgumentException("图形验证码不正确！");
			}

			boolean isSuccess = "OK".equalsIgnoreCase(SMSUtil.SendSMS(smsCode, phone, type));

			sms.setSuccess(isSuccess ? 1 : 0);
			session.setAttribute(SMS_CODE_KEY, smsCode);
			session.removeAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
			smsService.save(sms);
		}
		catch(Exception e) {
			if (!(e instanceof IllegalArgumentException)) {
				e.printStackTrace();
			}
			
			rt.setStatus(ReturnDatas.ERROR);
			rt.setMessage(e.getMessage());
		}

		return rt;
	}
	
	/**
	 * 生成验证码
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/getCaptcha/json")
	public @ResponseBody ReturnDatas getCaptcha(HttpSession session,HttpServletResponse response) throws IOException {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		StringBuffer code = new StringBuffer();
		BufferedImage image = CaptchaUtils.genRandomCodeImage(code);
		session.removeAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		session.setAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM, code.toString());
		// 将内存中的图片通过流动形式输出到客户端
		ByteArrayOutputStream BOS = new ByteArrayOutputStream();
		 ImageIO.write(image, "JPEG", BOS);
		 byte[] b=BOS.toByteArray();
		 String a=  com.cz.yingpu.frame.util.Base64.encode(b);
		 returnObject.setData(a);
		// returnObject.setMessage(code+"");
		 System.out.println(session.getId());
		return returnObject;
	}
	
	/**
	 * 新用户注册/分享页过来的
	 *
	 */
	@RequestMapping("/list/app/json")
	public  @ResponseBody
	ReturnDatas list(Model model, HttpSession session, AppUser appUser, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = new ReturnDatas() ;
		returnObject=super.listjson(request, model, appUser);
		return returnObject ;
	}
	
	//统计个人中心数据
	@RequestMapping("/personal/statistics/json")
	public  @ResponseBody
	ReturnDatas statistics(Model model, HttpSession session, AppUser appUser, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = new ReturnDatas(ReturnDatas.SUCCESS) ;
		  Map<String, Object> rmap=new HashMap<>();
  	    Finder  finder=new Finder("select count(1) as num from t_favorite where userId=:userId and isCancel=0");
  	    finder.setParam("userId",appUser.getId() );
         Map<String, Object> map=     baseSpringrainDao.queryForObject(finder);
         rmap.put("favoriteCount", map.get("num"));
         finder=new Finder("select count(1) as num from t_user_card where userId=:userId and status =1041");
         finder.setParam("userId",appUser.getId() );
         map=    baseSpringrainDao.queryForObject(finder);
         rmap.put("userCardCount", map.get("num"));
         returnObject.setData(rmap);
		return returnObject ;
	}

	/** 更换头像 */
	@UserCheck
	@RequestMapping("/change_avatar/json")
	public  @ResponseBody
	ReturnDatas changeAvatar(Integer userId, String base64Image) {
		ReturnDatas returnObject = new ReturnDatas(ReturnDatas.SUCCESS) ;
		try {
			if (StringUtils.isBlank(base64Image)) {
				throw new RuntimeErrorException(null, "上传的头像无效！");
			}

			String imgUrl = AdminFileUpload.saveBase64ImageToFile(base64Image, userId);
			AppUser au = new AppUser();

			au.setId(userId);
			if ((au = appUserService.queryForObject(au)) == null) {
				throw new RuntimeErrorException(null, "用户信息异常：不存在的用户");
			}

			au.setHeader(imgUrl);
			appUserService.updateValidValue(au);
			returnObject.setData(imgUrl);
		}
		catch(Exception e) {
			if (e instanceof RuntimeErrorException) {
				returnObject.setMessage(e.getMessage());
			}
			e.printStackTrace();
		}

		return returnObject;
	}


	/** 找回密码 */
	@RequestMapping("/find_password/json")
	public  @ResponseBody
	ReturnDatas modifyPassword(AppUser appUser, String smsCode) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {
			String phone = appUser.getPhone(),
					password = appUser.getPassword();

			AppUser au = new AppUser();
			Sms sms = new Sms();

			au.setPhone(phone);
			sms.setPhone(phone);
			sms.setContent(smsCode);

			if (baseSpringrainDao.queryForObject(sms) == null) {
				throw new RuntimeErrorException(null, "无效的短信验证码！");
			}
			if (StringUtils.isBlank(phone) || (au = baseSpringrainDao.queryForObject(au)) == null) {
				throw new RuntimeErrorException(null, "该用户不存在！");
			}
			if (StringUtils.isBlank(password)) {
				throw new RuntimeErrorException(null, "密码不能为空！");
			}
			if (StringUtils.isNotBlank(au.getPassword())
					&& au.getPassword().equals(CryptAES.AES_Encrypt(appUser.getPassword()))) {
				throw new RuntimeErrorException(null, "新密码不能与旧密码相同！");
			}

			au.setPassword(CryptAES.AES_Encrypt(appUser.getPassword()));
			baseSpringrainDao.updateValidValue(au);

		}
		catch(Exception e) {
			if (e instanceof RuntimeErrorException) {
				rt.setMessage(e.getMessage());
			}
			e.printStackTrace();
		}

		return rt;
	}
	
	//加载我的足迹
	@RequestMapping("/myTracks/list/json")
	@ResponseBody
	public ReturnDatas loadMyTracks(Footprint footprint){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		Page page=newPage(request);
		try {
			returnDatas.setPage(page);
			Finder finder=new Finder("select f.*,h.name,h.img from t_footprint f inner join t_hotel h on f.hotelId=h.id  ");
			returnDatas.setData(baseSpringrainDao.findListDataByFinder(finder, page, Footprint.class, footprint));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnDatas;
	}
	
	//加载我的收藏
		@RequestMapping("/favorite/list/json")
		@ResponseBody
		public ReturnDatas favoriteList(Favorite favorite){
			ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
			Page page=newPage(request);
			try {
				returnDatas.setPage(page);
				Finder finder=new Finder("select f.*,h.name,h.img from t_favorite f inner join t_hotel h on f.hotelId=h.id  ");
				returnDatas.setData(baseSpringrainDao.findListDataByFinder(finder, page, Favorite.class, favorite));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returnDatas;
		}
		@RequestMapping("/favorite/json")
		@ResponseBody
		public ReturnDatas favorite(Favorite favorite){
			ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
			try {
				favorite.setIsCancel(0);
				List<Favorite> flist= baseSpringrainDao.findListDataByFinder(null, null, Favorite.class, favorite);
				if(flist.size()<=0){
					favorite.setIsCancel(0);
					favorite.setCreateTime(new Date());
					baseSpringrainDao.save(favorite);
				}else{
					favorite=flist.get(0);
					favorite.setCreateTime(new Date());
					favorite.setIsCancel(1);
					baseSpringrainDao.update(favorite);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returnDatas;
		}
		@RequestMapping("/favorite/cancel/json")
		@ResponseBody
		public ReturnDatas favoriteCancel(Favorite favorite){
			ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
			try {
				favorite.setIsCancel(1);
				baseSpringrainDao.update(favorite);
				returnDatas.setMessage("取消成功");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnDatas.setMessage("取消失败");
			}
			return returnDatas;
		}
		@RequestMapping("/qrcode/json")
		@ResponseBody
		public ReturnDatas qrcode(AppUser appUser){
			ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
			try {
				//保存文件的文件夹名称
				String path=  AdminFileUpload.realpath+"/qrcode/";
				String url=AdminFileUpload.httppath+"/qrcode/";
				 appUser=appUserService.findAppUserById(appUser.getId());
				 if(!StringUtil.isValid(appUser.getQrCode())){
					 String uuid=UUID.randomUUID().toString();
					 QRcodeUtils.createCode("www.zhuce.com"+appUser.getId(), path+".jpg", 200, 200);
					 appUser.setQrCode(uuid+".jpg");
					 appUserService.updateValidValue(appUser);
				 }
				 returnDatas.setData(url+appUser.getQrCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnDatas.setMessage("取消失败");
			}
			return returnDatas;
		}
}
