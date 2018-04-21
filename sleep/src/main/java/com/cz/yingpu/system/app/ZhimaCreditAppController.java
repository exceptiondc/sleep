package com.cz.yingpu.system.app;

import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthorizeResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationCertifyResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationInitializeResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationQueryResponse;
import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.dao.IBaseJdbcDao;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.ImgCompress;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.StringUtil;
import com.cz.yingpu.frame.util.pay.ZhimaCreditUtils;
import com.cz.yingpu.servlet.AdminFileUpload;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.entity.ZhimaCertificationInfo;
import com.cz.yingpu.system.entity.ZhimaTransaction;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

@Controller()
@RequestMapping(value="/app/zhima_credit")
public class ZhimaCreditAppController extends BaseController{
	
	@Resource
	private IBaseJdbcDao baseSpringrainDao;
	
	
	/** 芝麻信用认证初始化 */
	@RequestMapping("/zhima/certification/init")
	@ResponseBody
	public ReturnDatas initialize(String type, String name, String card, String mobile, int userId)  {
		Map<String, Object> identityParams = new HashMap<>();
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		String transactionId = getTransactionId();

		try {
			if (type == null) {
				throw new IllegalArgumentException("Type不能为空");
			}
			
			switch(type) {
				case ZhimaCreditUtils.IdentityType.CERT_INFO:
					identityParams.put("identity_type", "CERT_INFO");
					identityParams.put("cert_type", "IDENTITY_CARD");
					identityParams.put("cert_name", name);
					identityParams.put("cert_no", card);
					break;
					
				case ZhimaCreditUtils.IdentityType.SMS_MOBILE_NO:
					identityParams.put("identity_type", "SMS_MOBILE_NO");
					identityParams.put("cert_type", "IDENTITY_CARD");
					identityParams.put("mobile_no", mobile);
					break;

				case ZhimaCreditUtils.IdentityType.USER_ID: break;
					
				default:
					throw new IllegalArgumentException("无效的类型");
			}

			ZhimaCertificationInfo info = new ZhimaCertificationInfo();
			info.setUserId(userId);
			ZhimaCertificationInfo infoInDB = baseSpringrainDao.queryForObject(info);

			ZhimaCustomerCertificationInitializeResponse resp = ZhimaCreditUtils.Certification.initialize(
					transactionId, ZhimaCreditUtils.BizCode.SMART_FACE, identityParams);
			if (resp.isSuccess()) {
				info.setBizNo(resp.getBizNo());
				info.setCreateTime(new Date());
				info.setUserId(userId);
				info.setBizNoExpiryTime(new Date(new Date().getTime() + ZhimaCreditUtils.BIZ_NO_EXPIRE));
				info.setIdCard(card);
				info.setRealName(name);
				if (infoInDB != null) {
					info.setId(infoInDB.getId());
					baseSpringrainDao.updateValidValue(info);
				}
				else {
					baseSpringrainDao.save(info);
				}
			}
			else {
				throw new Exception("芝麻信用认证初始化失败！");
			}

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}
		
		return rt;
	}
	
	
	/** 芝麻信用用户认证并返回H5网页认证URL */
	@RequestMapping("/zhima/certification/certify/json")
	@ResponseBody
	public ReturnDatas certify(int userId) {
		ZhimaCertificationInfo info = null;
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		
		try {
			Finder finder = new Finder("SELECT * FROM t_zhima_certification_info WHERE userId = :id");
			finder.setParam("id", userId);
			finder.setEscapeSql(false);
			List<ZhimaCertificationInfo> list = baseSpringrainDao.queryForList(finder, ZhimaCertificationInfo.class);
			if (list.size() == 0) {
				throw new RuntimeException("您的认证信息已过期，需要重新认证！");
			}
			
			info = list.get(0);
			if (info.getBizNoExpiryTime().before(new Date())) {
				throw new RuntimeException("您的认证信息已过期，需要重新认证！");
			}
			
			ZhimaCustomerCertificationCertifyResponse rsp = ZhimaCreditUtils.Certification.certify(info.getBizNo());
			/*if (!"true".equals(rsp.getPassed())) {
				throw new RuntimeException("芝麻信用认证失败，原因：" + rsp.getFailedReason());
			}*/
			
			rt.setData(rsp.getBody());
			/*info.setCertificationState(1);
			baseSpringrainDao.update(info, true);*/
		}
		catch (Exception e) {
			e.printStackTrace();
			rt.setStatus(ReturnDatas.ERROR);
			rt.setMessage(e.getMessage());
		}

		return rt;
	}
	

	/** 芝麻认证结果处理回调方法 */
	@RequestMapping("/zhima/certification/callback/json")
	public String handleZhimaCertificationResult(int userId) {
		Finder finder = new Finder("SELECT * FROM t_zhima_certification_info WHERE userId = " + userId);
		String viewName = "certification_failure";
		
		try {
			ZhimaCertificationInfo info = baseSpringrainDao.queryForObject(finder, ZhimaCertificationInfo.class);
			if (info == null) {
				throw new RuntimeException("该用户还未进行芝麻信用认证！");
			}
			
			if (info.getCertificationState() == 1) {
				viewName = "certification_success";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:" + viewName;
	}
	
	
	/** 芝麻信用用户授权并生成授权URL */
	@RequestMapping("/zhima/certification/authorize/json")
	@ResponseBody
	public ReturnDatas authorize(int identityType, Integer userId, String phone, String name, String card) {
		Map<String, Object> identityParams = new HashMap<>();
		Map<String, Object> bizParams = new HashMap<>();
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		
		bizParams.put("auth_code", "M_H5");
		bizParams.put("channelType", "app");
		bizParams.put("state", new Date().toString());

		try {
			if (identityType == 1) {
				if (StringUtils.isBlank(phone)) {
					throw new RuntimeErrorException(null, "手机号不能为空！");
				}

				identityParams.put("mobileNo", phone);
			}
			else if (identityType == 2){
				if (StringUtils.isBlank(card) || StringUtils.isBlank(name)) {
					throw new RuntimeErrorException(null, "真实姓名和身份证不能为空！");
				}

				identityParams.put("certNo", card);
				identityParams.put("name", name);
				identityParams.put("certType", "IDENTITY_CARD");
			}
		
			ZhimaAuthInfoAuthorizeResponse rsp = ZhimaCreditUtils.Certification.authorize(
					identityType + "", identityParams, bizParams);
			
			rt.setData(rsp.getBody() + "&userId=" + userId);
		}
		catch (Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}
		
		return rt;
	}
	
	
	/** 芝麻信用授权回调处理 */
	@RequestMapping("/zhima/certification/authorize/callback/json")
	public String authorizationCallback(String params, String sign, Integer userId) {
		try {
			String openId = "";
			if ((openId = ZhimaCreditUtils.Certification.authorizationVerifySign(params, sign)) == null
					|| openId.trim().length() == 0) {
				throw new RuntimeException("验签失败！未获取到openId！");
			}
			if (userId == null) {
				throw new RuntimeException("用户信息丢失！");
			}
			
			Finder finder = new Finder("SELECT * FROM t_zhima_certification_info WHERE userId = " + userId);
			ZhimaCertificationInfo info = baseSpringrainDao.queryForObject(finder, ZhimaCertificationInfo.class);
			boolean hasInit = info != null;
			if (info == null) {
				info = new ZhimaCertificationInfo();
			}
			
			info.setOpenId(openId);
			info.setAuthorizationState(1);
			if (hasInit) {
				baseSpringrainDao.update(info, true);
			}
			else {
				info.setUserId(userId);
				info.setCreateTime(new Date());
				baseSpringrainDao.save(info);
			}
			
			return "redirect:authorization_success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:authorization_failure";
		}
	}
	
	
	/** 芝麻信用认证状态查询 */
	@RequestMapping("/zhima/certification/query/json")
	@ResponseBody
	public ReturnDatas queryCertificationStatus(Integer userId) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		
		try {
			Finder finder = new Finder("SELECT * FROM t_zhima_certification_info WHERE userId = " + userId);
			ZhimaCertificationInfo info = baseSpringrainDao.queryForObject(finder, ZhimaCertificationInfo.class);
			String bizNo = null;
			if ((bizNo = info.getBizNo()) == null || StringUtils.isBlank(info.getRealName()) ||
					StringUtils.isBlank(info.getIdCard())) {
				throw new RuntimeException("用户暂未进行认证！");
			}

			if (info.getBizNoExpiryTime().before(new Date())) {
				initialize(ZhimaCreditUtils.IdentityType.CERT_INFO, info.getRealName(),
						info.getIdCard(), null, userId);
			}

			ZhimaCustomerCertificationQueryResponse rsp =
					ZhimaCreditUtils.Certification.queryCertificationStatus(bizNo);
			
			rt.setData(rsp);
			if ("true".equals(rsp.getPassed())) {
				AppUser au = new AppUser();
				au.setId(userId);
				au.setIsIdCard("是");
				baseSpringrainDao.updateValidValue(au);
				info.setCertificationState(1);
				baseSpringrainDao.update(info, true);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			rt.setStatus(ReturnDatas.ERROR);
			rt.setMessage(e.getMessage());
		}
		
		return rt;
	}
	
	
	/** 芝麻信用认证状态查询 */
	@RequestMapping("/zhima/certification/authorization/query/json")
	@ResponseBody
	public ReturnDatas queryAuthorizationStatus(Integer userId, String identityType) {
		String authCategory = "";
		Map<String, Object> identityParams = new HashMap<>();
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		
		try {
			Finder finder = new Finder("SELECT * FROM t_zhima_certification_info WHERE userId = " + userId);
			ZhimaCertificationInfo info = baseSpringrainDao.queryForObject(finder, ZhimaCertificationInfo.class);
			
			if ("0".equals(identityType)) {
				String openId = info.getOpenId(); 
				if (openId == null || openId.trim().length() == 0) {
					throw new RuntimeException("用户暂未授权！");
				}
				
				identityParams.put("openId", openId);
			}
			else if ("1".equals(identityType)) {
				String idCard = info.getIdCard();
				String realName = info.getRealName();
				if (StringUtils.isBlank(idCard) || StringUtils.isBlank(realName)) {
					throw new RuntimeException("用户暂未认证！");
				}

				identityParams.put("certNo", idCard);
				identityParams.put("name", realName);
				identityParams.put("certType", "IDENTITY_CARD");
			}

			ZhimaAuthInfoAuthqueryResponse rsp = ZhimaCreditUtils.Certification.queryAuthorizationStatus(
					identityType, identityParams, authCategory);
			
			rt.setData(rsp);
			info.setAuthorizationState(rsp.getAuthorized() ? 1 : 0);
			baseSpringrainDao.update(info, true);
		}
		catch(Exception e) {
			e.printStackTrace();
			rt.setStatus(ReturnDatas.ERROR);
			rt.setMessage(e.getMessage());
		}
		
		return rt;
	}

	/** 芝麻信用分查询 */
	@RequestMapping("/zhima/certification/score_query/json")
	@ResponseBody
	 public ReturnDatas creditScoreQuery(Integer userId) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		
		try {
			Finder finder = new Finder("SELECT * FROM t_zhima_certification_info WHERE userId = " + userId);
			ZhimaCertificationInfo info = baseSpringrainDao.queryForObject(finder, ZhimaCertificationInfo.class);
			if (info == null || info.getAuthorizationState() != 1) {
				throw new RuntimeException("用户暂未认证或授权！");
			}
			
			String openId = info.getOpenId();
			ZhimaTransaction transaction = getCachedTransaction(userId, ZhimaCreditUtils.APIs.CREDIT_SCORE_QUERY);
			String transactionId = transaction != null ? transaction.getTransactionId() : getTransactionId();

			ZhimaCreditScoreGetResponse rsp = ZhimaCreditUtils.Certification.queryCreditScore(
					openId, transactionId);
			
			rt.setData(rsp.getZmScore());
			info.setScore(StringUtil.str2Int(rsp.getZmScore(), 0));
			baseSpringrainDao.update(info, true);
			if (transaction == null) {
				saveTransactionId(userId, ZhimaCreditUtils.APIs.CREDIT_SCORE_QUERY, transactionId);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			rt.setStatus(ReturnDatas.ERROR);
			rt.setMessage(e.getMessage());
		}
		
		return rt;
    }

	/** 获取业务流水凭证 */
	private String getTransactionId() {
		return ZhimaCreditUtils.generateTansactionId();
	}

	/** 获取在缓存有效期内的业务流水凭证 */
	private ZhimaTransaction getCachedTransaction(Integer userId, String apiName) {
		Finder finder = new Finder("SELECT * FROM t_zhima_transaction WHERE userId = " + userId)
						.append(" AND apiName = '" + apiName +"' ORDER BY createTime");
		ZhimaTransaction transaction = null;
		finder.setEscapeSql(false);

		try {
			transaction = baseSpringrainDao.queryForObject(finder, ZhimaTransaction.class);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return transaction;
	}

	/** 保存业务流水凭证 */
	private void saveTransactionId(Integer userId, String apiName, String tid) {
		String transactionId = "";
		ZhimaTransaction transaction = getCachedTransaction(userId, apiName);

		try {
			if (transaction == null) {
				transaction = new ZhimaTransaction();
				transaction.setUserId(userId);
				transaction.setCreateTime(new Date());
				transaction.setApiName(apiName);
				transaction.setExpiryTime(new Date(new Date().getTime() + ZhimaCreditUtils.TRANSACTION_ID_EXPIRE));
			}

			transaction.setTransactionId(transactionId);
			if (transaction.getId() != null) {
				baseSpringrainDao.update(transaction, true);
			}
			else {
				baseSpringrainDao.save(transaction);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** 身份证照片信息识别 */
	@RequestMapping("/idcard/recognition/json")
	@ResponseBody
	public ReturnDatas idCardPicRecognition(
			Integer userId, @RequestParam(value = "images[]") String[] images,
			@RequestParam(value = "sides[]") String[] sides) {
		ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
		Map<String, Map<?, ?>> result = new HashMap<>();
		String uploadFilePath = AdminFileUpload.realfilepath + "/" + userId,
				fileUrlPath = AdminFileUpload.httpfilepath  + "/" + userId;

		try {
			for (int i = 0; i < images.length; i++) {
				String image = images[i],
						side = sides[i],
						imagePath = uploadFilePath + "/" + image,
						base64Image = "",
						imgExt = "";

				if (StringUtils.isBlank(image)) {
					throw new RuntimeErrorException(null, "身份证照片不能为空！");
				}
				if (!new File(imagePath).isFile()) {
					throw new RuntimeErrorException(null, "身份证照片上传失败！！");
				}

				imgExt = image.substring(image.lastIndexOf(".") + 1);
				base64Image = ImgCompress.ImageToBase64(imagePath, StringUtils.isBlank(imgExt) ? "jpg" : imgExt);
				result.put(side, ZhimaCreditUtils.idCardPicRecognition(base64Image, side));
				if (result.get(side) == null) {
					returnDatas.setStatus(ReturnDatas.ERROR);
				}
			}

			baseSpringrainDao.update(new Finder("UPDATE t_zhima_certification_info SET idCardImgFace = :face,")
					.append(" idCardImgBack = :back WHERE userId = :uid")
					.setParam("face", fileUrlPath + "/" + images[0])
					.setParam("back", fileUrlPath + "/" + images[1])
					.setParam("uid", userId));

			returnDatas.setData(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			returnDatas.setStatus(ReturnDatas.ERROR);
			if (e instanceof RuntimeErrorException) {
				returnDatas.setMessage(e.getMessage());
			}
		}

		return returnDatas;
	}

	/** 获取用户认证信息 */
	@RequestMapping("/status/query/json")
	@ResponseBody
	public ReturnDatas certificationStatusQuery(Integer userId) {
		ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
		Map<String, Object> result = new HashMap<>();

		try {
			if (userId == null) {
				throw new RuntimeErrorException(null, "无法获取此用户认证信息！");
			}

			ZhimaCertificationInfo info = new ZhimaCertificationInfo();
			info.setUserId(userId);
			if ((info = baseSpringrainDao.queryForObject(info)) != null) {
				result.put("isIdCardRecognition",
						StringUtils.isNotBlank(info.getRealName())
								&& StringUtils.isNotBlank(info.getIdCard()));
				result.put("isCertification", new Integer(1).equals(info.getCertificationState()));
				result.put("isAuthorization",
						StringUtils.isNotBlank(info.getOpenId())
								&& "1".equals(info.getAuthorizationState()));
			}

			returnDatas.setData(info);
			returnDatas.setMap(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			returnDatas.setStatus(ReturnDatas.ERROR);
			if (e instanceof RuntimeErrorException) {
				returnDatas.setMessage(e.getMessage());
			}
		}

		return returnDatas;
	}
}
