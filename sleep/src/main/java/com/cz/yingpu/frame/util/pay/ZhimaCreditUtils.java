package com.cz.yingpu.frame.util.pay;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthqueryRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationCertifyRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationInitializeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationQueryRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthorizeResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationCertifyResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationInitializeResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationQueryResponse;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * &nbsp;&nbsp;芝麻信用工具类
 */
final public class ZhimaCreditUtils {

	/** 商户 RSA 私钥 **/
	public final static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK1T+x+ifDvyhESndOo4RLo7XTsMXxEOezQ7g92IPe6mVkTnAHrhxKDQehZLcPgxVlqln8Ui+4qoiJnlJXBYpu/isqYzqGBxaLu5t08DdjHHzxX8SgtEs84L35zMpdbEQynTPduX9rSrLm0yLAU/gOrqozDF6fqhJogwomrzjoN3AgMBAAECgYBehSyuDLLJEjCVK+izDapVEKh/2fhB2e7QBiCb/38elLD49Cwr1tCejfH2FH/vqSAZYLanD7Oy4sNTgUWQZZfq1Y/HqNUdGAxSdxeHQvsP6SU7n13aXrpmMTjNR/yD51qRngFw4wuaXlhXBB0GTHhkLhucWvZ4h/xGPQCmKxtnuQJBANk5vE5TypkGoQHWJBmidGPyVIhQcGnCeedj5hXP7kIY+v/4Ng7RmP8oT87jyFHvUGUHxWy57NcfM207rZoTV0UCQQDMRFItnUMYu6cuvj7JhPug1xzkY27/ugB88JFpHGEI5fKuJUU7dyS59xsK0XxyQlqsk1fgCIKkz5RcqaB9yy2LAkEAhOFWmauUMYvmjhMKvkusWz9wCnr6aRe4Lp3jr9AkdXaGhHK3ztTUfLGSMIMSlVyJl2PrlxkdEnNZqCZriebu6QJBAJ8N6At6tB9TJ2f/XXafZSwltGqmfm0EEZM345I8Ndau7xvpC5K1QFTJ9DWlT0jNIDvW84sQNWJN/JF2x/CLv+cCQA6YL8hJ/Pcl2WWDtbpZ9XjwfMlaVbFeIG6wUkRGSqsyojPtCD4vpyeM1stKXUGdyUKnebCNDREC6AcKIpMZxRw=";

	/** 商户 RSA 公钥 **/
	public final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChJHDyOFz9QfzOhROSKLMlA1sAyJiGEeYMfCiO4xNbGLiiukY4+DaZgXNTJ2I4ZullfGQGfPM8/SlkzGNdnU4H+/q64+MqVorDBDoTEhCTtu96qhUUXxnAQjtfjpI/ylSjY/Hgj6HodCSve+L3MKFbu674WWRFD/rrnOdjrvuMSQIDAQAB";

	/** 商户应用 Id **/
	public final static String APP_ID = "300002377";

	/** 芝麻开放平台地址 **/
	public final static String GATEWAY_URL = "https://zmopenapi.zmxy.com.cn/openapi.do";

	/** 产品代码 **/
	public final static String PRODUCT_CODE = "w1010100000000002978";

	/** 平台代码 **/
	public final static String PLATFORM = "zmop";

	/** 频道 **/
	public final static String CHANNEL = "apppc";

	/** 认证回调URL **/
	public final static String CALLBACK_URL = "sleep://abc";

	/** transactionId 前缀 **/
	public final static String TRANSACTION_ID_PREFIX = "SP";

	/** biz_no(认证的唯一标识) 有效时间 (ms) */
	public final static int BIZ_NO_EXPIRE = 23 * 3600 * 1000;

	/** tansaction_id(业务流水凭证) 有效时间 (ms) */
	public final static int TRANSACTION_ID_EXPIRE = 24 * 3600 * 1000;

	/** openId(用户授权凭证) 有效时间  (ms) */
	public final static int OPEN_ID_EXPIRE = 24 * 3600 * 1000;

	/** JSON工具类 **/
	private static Gson gson = new Gson();


	/** 芝麻信用业务API **/
	final public static class APIs {
		/** 认证API */
		public final static String CERTIFICATION = "zhima.customer.certification.certify";

		/** 认证查询API */
		public final static String CERTIFICATION_QUERY = "zhima.customer.certification.query";

		/** 认证初始化API */
		public final static String CERTIFICATION_INITIALIZATION = "zhima.customer.certification.initialize";

		/** 授权API */
		public final static String AUTHORIZATION = "zhima.auth.info.authorize";

		/** 信用分查询API */
		public final static String CREDIT_SCORE_QUERY = "zhima.credit.score.get";

		/** 授权状态查询API */
		public final static String AUTHORIZATION_QUERY = "zhima.auth.info.authquery";
	}


	/** 认证的唯一标识 **/
	final public static class BizCode {
		/** 多因子活体人脸认证 */
		public final static String FACE = "FACE";

		/** 多因子快捷活体人脸认证 */
		public final static String SMART_FACE = "SMART_FACE";

		/** 活体人脸认证 */
		public final static String FACE_SDK = "FACE_SDK";

		/** 多因子证件照片认证 */
		public final static String CERT_PHOTO = "CERT_PHOTO";

		/** 多因子证件照片和人脸认证 */
		public final static String CERT_PHOTO_FACE = "CERT_PHOTO_FACE";
	}


	final public static class IdentityType {
		/** 证件信息 */
		public final static String CERT_INFO = "CERT_INFO";

		/** 短信认证 */
		public final static String SMS_MOBILE_NO = "SMS_MOBILE_NO";

		/** 支付宝UID */
		public final static String USER_ID = "FACE_SDK";
	}


	/**
	 * 认证工具
	 */
	final public static class Certification {
	    //芝麻开放平台地址
	    private static String gatewayUrl     = GATEWAY_URL;
	    //商户应用 Id
	    private static String appId          = APP_ID;
	    //商户 RSA 私钥
	    private static String privateKey     = PRIVATE_KEY;
	    //芝麻 RSA 公钥
	    private static String zhimaPublicKey = PUBLIC_KEY;


	    /**
	     * 芝麻认证初始化
	     * @param transactionId
	     * @param bizCode
	     * @param identityParams
	     */
	    public static ZhimaCustomerCertificationInitializeResponse initialize(String transactionId, String bizCode,
	    		Map<String, Object> identityParams/*, Map<String, Object> merchantConfig*/) {
	        ZhimaCustomerCertificationInitializeRequest req = new ZhimaCustomerCertificationInitializeRequest();
	        req.setChannel(CHANNEL);
	        req.setPlatform(PLATFORM);
	        req.setTransactionId(transactionId);// 必要参数
	        req.setProductCode(PRODUCT_CODE);// 必要参数
	        req.setBizCode(bizCode);// 必要参数
	        req.setIdentityParam(toJsonString(identityParams));// 必要参数
	        req.setMerchantConfig("{}");//
	        req.setExtBizParam("{}");// 必要参数
	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
	        try {
	            ZhimaCustomerCertificationInitializeResponse response =(ZhimaCustomerCertificationInitializeResponse)client.execute(req);
	            System.out.println(response.isSuccess());
	            System.out.println(response.getErrorCode());
	            System.out.println(response.getErrorMessage());
	            return response;
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


	    /** 取得芝麻用户授权 */
	    public static ZhimaAuthInfoAuthorizeResponse authorize(
	    		String identityType, Map<String, Object> identityParams, Map<String, Object> bizParams) {
	        ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
	        req.setChannel(CHANNEL);
	        req.setPlatform(PLATFORM);
	        req.setIdentityType(identityType);// 必要参数
	        req.setIdentityParam(toJsonString(identityParams).toString());// 必要参数
	        req.setBizParams(toJsonString(bizParams).toString());//
	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);

	        try {
	        	ZhimaAuthInfoAuthorizeResponse rep = new ZhimaAuthInfoAuthorizeResponse();
	            String url = client.generatePageRedirectInvokeUrl(req);
	            rep.setBody(url);
	            return rep;
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


	    /** 对芝麻信用授权返回的参数及签名进行验证 */
	    public static String authorizationVerifySign(String params, String sign) throws UnsupportedEncodingException {
	    	String charset = "UTF-8";
	        //从回调URL中获取sign参数，此处为示例值
	        //判断串中是否有%，有则需要decode
	        if(params.indexOf("%") != -1) {
	            params = URLDecoder.decode(params, charset);
	        }
	        if(sign.indexOf("%") != -1) {
	            sign = URLDecoder.decode(sign, charset);
	        }

	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
	        try {
	            String result = client.decryptAndVerifySign(params, sign);
	            return result;
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


	    /** 从芝麻信用查询用户授权信息 */
	    public static ZhimaAuthInfoAuthqueryResponse queryAuthorizationStatus(
	    		String identityType, Map<String, Object> identityParams, String authCategory) {
	        ZhimaAuthInfoAuthqueryRequest req = new ZhimaAuthInfoAuthqueryRequest();
	        req.setChannel(CHANNEL);
	        req.setPlatform(PLATFORM);
	        req.setIdentityType(identityType);// 必要参数
	        req.setIdentityParam(toJsonString(identityParams));// 必要参数
	        req.setAuthCategory(authCategory);// 必要参数
	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
	        try {
	            ZhimaAuthInfoAuthqueryResponse response =(ZhimaAuthInfoAuthqueryResponse)client.execute(req);
	            System.out.println(response.isSuccess());
	            System.out.println(response.getErrorCode());
	            System.out.println(response.getErrorMessage());
	            return response;
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


	    /** 从芝麻信用查询用户认证信息 */
	    public static ZhimaCustomerCertificationQueryResponse queryCertificationStatus(
	    		String bizNo) {
	    	ZhimaCustomerCertificationQueryRequest req = new ZhimaCustomerCertificationQueryRequest();
	        req.setChannel(CHANNEL);
	        req.setPlatform(PLATFORM);
	        req.setBizNo(bizNo);// 必要参数
	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
	        try {
	        	ZhimaCustomerCertificationQueryResponse response =(ZhimaCustomerCertificationQueryResponse)client.execute(req);
	            System.out.println(response.isSuccess());
	            System.out.println(response.getErrorCode());
	            System.out.println(response.getErrorMessage());
	            return response;
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


	    /** 获取芝麻信用认证URL */
	    public static String generateCertificationURL(String bizNo) {
	        ZhimaCustomerCertificationCertifyRequest req = new ZhimaCustomerCertificationCertifyRequest();
	        req.setChannel(CHANNEL);
	        req.setPlatform(PLATFORM);
	        req.setBizNo(bizNo);// 必要参数
	        req.setReturnUrl(CALLBACK_URL);// 必要参数
	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
	        try {
	            String url = client.generatePageRedirectInvokeUrl(req);
	            System.out.println(url);
	            return url;
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


	    /** 芝麻信用认证 */
	    public static ZhimaCustomerCertificationCertifyResponse certify(String bizNo) {
	        ZhimaCustomerCertificationCertifyRequest req = new ZhimaCustomerCertificationCertifyRequest();
	        req.setChannel(CHANNEL);
	        req.setPlatform(PLATFORM);
	        req.setBizNo(bizNo);// 必要参数
	        req.setReturnUrl(CALLBACK_URL);// 必要参数
	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
	        try {
	            ZhimaCustomerCertificationCertifyResponse resp = new ZhimaCustomerCertificationCertifyResponse();
				String url = client.generatePageRedirectInvokeUrl(req);
				resp.setBody(url);
	            return resp;
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


	    /** 芝麻信用分查询 */
	    public static ZhimaCreditScoreGetResponse queryCreditScore(String transactionId, String openId) {
	    	ZhimaCreditScoreGetRequest req = new ZhimaCreditScoreGetRequest();
	        req.setChannel(CHANNEL);
	        req.setPlatform(PLATFORM);
	        req.setTransactionId(transactionId);// 必要参数
	        req.setProductCode(PRODUCT_CODE);// 必要参数
	        req.setOpenId(openId);// 必要参数
	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
	        try {
	            ZhimaCreditScoreGetResponse response =(ZhimaCreditScoreGetResponse)client.execute(req);
	            System.out.println(response.isSuccess());
	            System.out.println(response.getErrorCode());
	            System.out.println(response.getErrorMessage());
	            return response;
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	}


	/** 识别身份证照片信息 */
	public static Map<String, Object> idCardPicRecognition(String base64Image, String idCardSide) throws Exception {
		String host = "https://dm-51.data.aliyun.com";
		String path = "/rest/160601/ocr/ocr_idcard.json";
		String method = "POST";
		String appcode = "5f6c06f912134827bc2d82b4a368ed6e";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, String> side = new HashMap<String, String>();

		body.put("image", base64Image);
		side.put("side", idCardSide);
		body.put("configure", side);
		CloseableHttpClient client = null;
		try {
			client = HttpClients.custom()
				.setHostnameVerifier(new AllowAllHostnameVerifier())
				.setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
						return true;
					}
				}).build())
				.build();

			HttpPost httpPost = new HttpPost(host + path);
			HttpEntity httpEntity = new StringEntity(toJsonString(body));
			httpPost.addHeader("Authorization", "APPCODE " + appcode);
			httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
			httpPost.setEntity(httpEntity);

			HttpResponse httpResponse = client.execute(httpPost);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			InputStream is = httpResponse.getEntity().getContent();
			byte[] buff = new byte[8192];
			int read = 0;
			while((read = is.read(buff)) != -1) {
				os.write(buff, 0, read);
			}

			String json = new String(os.toByteArray(), "UTF-8");
			if (StringUtils.isNotBlank(json)) {
				return new Gson().fromJson(json, Map.class);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}


	/** 生成唯一的transactonId(业务流水凭证) */
	public static synchronized String generateTansactionId() {
		return TRANSACTION_ID_PREFIX + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	
	/** 对象转JSON */
	public static String toJsonString(Object obj) {
		if (gson == null) {
			gson = new Gson();
		}
		return gson.toJson(obj).toString();
	}
}
