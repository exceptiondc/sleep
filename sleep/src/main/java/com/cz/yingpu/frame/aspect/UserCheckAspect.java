package com.cz.yingpu.frame.aspect;

import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.Joinpoint;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.service.IAppUserService;

/**
 * 根据token检查用户有效性
 *
 */
@Component
@Aspect
public class UserCheckAspect {

	@Resource  
	HttpServletRequest request; 
	
	@Resource
	private IAppUserService appUserService;
	
	@Resource
	private CacheManager cacheManager;

	@Pointcut("@annotation(com.cz.yingpu.frame.annotation.UserCheck)")
	public void UserCheckAop() {}
	
	
	@Before("UserCheckAop() && args(joinpoint)")
	public void userCheckBefore(Joinpoint joinpoint){
		System.out.println("----------------------------------------------------------");
	}
	
	
	@Around("UserCheckAop()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) {
		Map<String, String[]> map = request.getParameterMap();
		String userIdKey = map.containsKey("userId") ? "userId" : 
								(map.containsKey("userid") ? "userid" : null),
			   userId = userIdKey == null ? null : request.getParameter(userIdKey),
			   token = request.getParameter("token");
		
		Object obj = ReturnDatas.getErrorReturnDatas();
		
		try {
			if (StringUtils.isBlank(userId) || StringUtils.isBlank(token)) {
				throw new RuntimeErrorException(null, "用户信息丢失！");
			}
			
			Cache cache = cacheManager.getCache(GlobalStatic.tokenCacheKey);
			String realToken = "";
			if (StringUtils.isBlank(realToken = cache.get(userId, String.class))) {
				AppUser user = appUserService.findById(userId, AppUser.class);
				if (user != null) {
					realToken = user.getToken();
				}
			}
			
			if (StringUtils.isBlank(realToken) || !realToken.equals(token)) {
				throw new RuntimeErrorException(null, "用户信息验证失败！");
			}

			obj = proceedingJoinPoint.proceed();
		}
		catch(Throwable e) {
			if (e instanceof RuntimeErrorException) {
				System.err.println(e.getMessage());
			}
			else {
				e.printStackTrace();
			}
			((ReturnDatas) obj).setMessage(e.getMessage());
		}
		
		return obj;
	}
	
	
	@AfterReturning(pointcut = "UserCheckAop()", returning = "returnDatas")
	public void checkUserReturn(ReturnDatas returnDatas) {
		if (returnDatas == null) {
			
		}
	}
	
}
