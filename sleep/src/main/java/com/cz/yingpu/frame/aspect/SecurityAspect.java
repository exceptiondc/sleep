package com.cz.yingpu.frame.aspect;

import com.cz.yingpu.frame.util.Des3;
import com.cz.yingpu.frame.util.JsonUtils;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.SecureRSA;
import net.sf.json.JSONObject;
import org.aopalliance.intercept.Joinpoint;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 对app端返回数据进行加密
 * @author Michael
 *
 */
@Component
@Aspect
public class SecurityAspect {
	
	//私钥
//	private String privateKey = "MIICWgIBAAKBgFFYdVx1HDZKaQRHBm+FkwsA8jvZQA3eAWxXie7bT+puNfKtLp5e3l/qVXjPMX31sg87Mr8OsIjE+OQIhe+/tV/u+tAAKdzkGGqVruVNMhFuqvX9Hn+/j2cwNOrQEDBly7b56/QNlHS7i6bnB4LxyD3obo7VzCMwkP1YBReh9X9/AgMBAAECgYA6dM8h+iaj/SUqpb/CNMNOjQeGasDVNkzfhqjgtUngtgKeukVeGd7EHqn9fyeZ1Q54U5pMIkpKfwI9HMLjX2j/EC6ObRYwN7YmnhOA7g6/MNi+Btvu+sgFR2Kf3Ia2DGveLwb6wmdtIPJp6CiUvvviY0me3LZnS/duTeVoX6TGAQJBAJaHBxBCLG2EW2pcp2RAvPOOKRiE0kFP+8o4n1oTosX7fs87JdlYAf43ZrxcmxEjyRO3jGL8pnl4+BFJ12tdgd8CQQCKV9/r1fY5wDTOCHO8ls7O6OJ2ydP8JOSYmnTG4Ko4hkeWoy9MyyuulwdyiS2AGdERh44XBvP7w7urbI0cpfZhAkApVEWiNykPoMmguHPVWNkIXj32V3GLMTTG3ykRiFam2ViF+Y140Wsqq3dvvFVvLU4mNb076Hak34vs40NcXT4tAkBaq5dmEViMpy20r5NzUf//WmE667LVOjTc07afthW6cD+xOgjBMxPRHMlTxxacM89zxr1Y2ETGiJWSl9WwphFhAkAPzcT9Jn53JxpN7uPwgGqYvnmlnjTGB0U/lhnNiT6u93O0UO2k8zOagx1V6fWdU6Md9AcLuP9gzttQvUOI/mbK" ;
	private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMXPSp9OoYmziCmh7gfDS6cIIkN23YSVHv2X4Xuk9Q3KPgKkV74s3xdmqPxc9XlZ5Sj8iyFycUm6znTxAgqHDeunzKH+X4Xvt3gKJDQoiKOBW+dg+bsIDp1Ci9J8r/dD3KSbHlcjFEGdI91rAXWQh+EVbIW2gvtI90LUuxUXKZdpAgMBAAECgYBYRFOMGs5CX/ZWfYd1t1O+yQZhDF10mAYoKunW/pjK+oAJNcRhfCxgiNLHKcPvzolPbMG4vxSGTfFqhVDf2bv/rX+0tx/1jGuSHf9GQ04FuTVvOM1qn7SxRft5xQayh6zTMoW5q+8aFN+otxvvTNRzC8ULjA6reFO5PdYfkko4AQJBAP+M71mMct1oJpObZ30a+JeDEbqgha1QNPkUQerZn9PHLKtfbn96pcZ3qRePeyPohXpt+7lOzz3smmOMjVPylUECQQDGKFuj1pH3F8LUihfBfF2AG1xV8l9LIfvI6vGYB+1+lgVn4GrHK1OZ8K5mzaDY7adyldkUeEgicG9aUnGy87ApAkEAm35g4QcRmWDXIDeOB9SScHaDIiCsViGYqfpGhaT3mD/4ESqXLKAvII0M6VYXomjIVw92/HFUrqQ56NrL38maQQJAIUWNfYj9oTuAHyfArWAwYt41NsknbvoZyLaKMjjCi8qsxbBMvXxs4SAkaGaGZ2YgA4Fdna5EjmPKjqPhK2b3YQJAIf+n1WxCSoOw0dYVBtjlBUo3lHtZsh92l/mcasZHgFcaFg+LGR803o+NUVJ0Mjh2EbHygqXAACYqUydcpga7+Q==";
	

	@Pointcut("@annotation(com.cz.yingpu.frame.annotation.SecurityApi)")
	public void securityAop(){}
	
	
	
	@Before("securityAop() && args(joinpoint)")
	public void  securityBefore(Joinpoint joinpoint){
		
	}
	@AfterReturning(pointcut = "securityAop()",
			returning = "returnDatas")
	public void securityAfter(ReturnDatas returnDatas){
		
		if(returnDatas != null && returnDatas.getData() != null){
			//返回数据
			String result = JsonUtils.writeValueAsString(returnDatas.getData()) ;

			String encodeData = null;
			try {
				encodeData = Des3.encode(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnDatas.setStatus(ReturnDatas.WARNING);
				returnDatas.setMessage("加密失败！");
			}
			returnDatas.setData(encodeData);
//			returnDatas.setData(result);
		}
	}
	
	@Autowired  
	HttpServletRequest request; 
	
	@Around("securityAop()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint){
		Object object = null ;
		Object[] args = proceedingJoinPoint.getArgs() ;
//		HttpServletRequest request =  (HttpServletRequest) args[0] ;

		Map<String,String[]> paramMap = new HashMap(request.getParameterMap()) ;
//		ServletRequestAttributes attr = (ServletRequestAttributes)
//	               RequestContextHolder.currentRequestAttributes();
//	       HttpServletRequest request = attr.getRequest();
//	    Map<String,String[]> paramMap = HttpUtil.getRequestMap(request) ;

		if(!paramMap.containsKey("signCode")){  //说明是非法请求
			return new ReturnDatas(ReturnDatas.ERROR, "非法请求") ;
		}else {
			try {
				String[] sign = paramMap.get("signCode") ;
				//解密
				String params= SecureRSA.decrypt(sign[0], privateKey, "UTF-8") ;   //公钥
				JSONObject json = JSONObject.fromObject(params) ;
				//验证时间戳，防止爬虫请求
				if(!json.containsKey("T")){
					return new ReturnDatas(ReturnDatas.ERROR, "非法请求") ;
				}else {

        			Long T = json.getLong("T") ;
					Date legalTime = DateUtils.addMinutes(new Date(), -10) ;
					if(T < Double.valueOf(DateFormatUtils.format(legalTime, "yyyyMMddHHmmss"))) {  //说明请求时间差超过10分钟，不是合法的
						return new ReturnDatas(ReturnDatas.ERROR, "通讯超时") ;
					}

//					if(json.containsKey("sessionId")){
//						Integer userId = json.getInt("sessionId") ;
//						AppUser user = appUserService.findAppUserById(userId) ;
//						if(user != null){
//							if(null != user.getIsBlack() && user.getIsBlack() == 1){  //黑名单
//								return new ReturnDatas(ReturnDatas.Black, "黑名单成员！") ;
//							}
//						}
//					}


//        			Iterator<String> keys = json.keys() ;
//        			String key  = "" ;
//        			while(keys.hasNext()){
//        				key = keys.next() ;
//        				if(key.equals("T")){
//        					Long T = json.getLong(key) ;
//        					Date legalTime = DateUtils.addMinutes(new Date(), -10) ;
//        					if(T < Double.valueOf(DateFormatUtils.format(legalTime, "yyyyMMddHHmmss"))) {  //说明请求时间差超过10分钟，不是合法的
////        						return new ReturnDatas(ReturnDatas.ERROR, "通讯超时") ;
//        					}
//        				}
//        				paramMap.put(key, new String[]{json.get(key).toString()}) ; ;
//        			}
//        			ac.setParameters(parameters);
//        			paramMap.put("sign",new String[]{"1"}) ;
//        			HttpServletRequest req = new ParameterRequestWrapper(request, paramMap) ;
//        			args[0] = req ;
					try {
						object = proceedingJoinPoint.proceed() ;
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//	    		object = proceedingJoinPoint.proceed() ;
			}catch ( BadPaddingException e){
				return new ReturnDatas(ReturnDatas.ERROR, "非法请求") ;
			}catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return object ;
	}
}
