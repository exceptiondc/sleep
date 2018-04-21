package com.cz.yingpu.frame.util.pay;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.Cipher;

import com.cz.yingpu.frame.util.Base64;

public class AliPayUtil {
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("alipay");
	
	
	
	
	
	
	
	/**
	 * 支付宝支付
	 * @param tradeNo
	 * @param subject
	 * @param body
	 * @param total
	 * @param callback
	 * @return
	 */
	public static  Map<String, String> createAliPayOrder(String tradeNo, String subject, String body, String total) {
		Map<String, String> _map = new HashMap<String, String>();
		_map.put("partner",  RESOURCE_BUNDLE.getString("partner"));
		_map.put("seller_id", RESOURCE_BUNDLE.getString("seller_id"));
		_map.put("out_trade_no", tradeNo);
		_map.put("subject", subject);
		_map.put("body", body);
		_map.put("total_fee", total);
		_map.put("notify_url", RESOURCE_BUNDLE.getString("ali_pay_back_url"));
		_map.put("service", "mobile.securitypay.pay");
		_map.put("payment_type", 1 + "");
		_map.put("_input_charset", "utf-8");
		_map.put("it_b_pay", "20m");
		//过滤参数并且按一定要求拼接支付宝支付参数
		String params = AliPayUtil.createAliPaylink(AliPayUtil.paraFilter(_map));
		//签名参数
		String signedParams = "";
		try {
			signedParams = URLEncoder.encode(AliPayUtil.sign(params, RESOURCE_BUNDLE.getString("ali_private_key"), "utf-8"), RESOURCE_BUNDLE.getString("input_charset"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_map.put("sign", signedParams);
		_map.put("sign_type", "RSA");
		return _map;
	}
	
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
	
	
	
	
	public static String createAliPaylink(Map<String, String> params) {
		System.out.println("调试信息"+params.toString());
		 List<String> keys = new ArrayList<String>(params.keySet());
	        Collections.sort(keys);

	        String prestr = "";

	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = (String) params.get(key.toString());

	            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
	                prestr = prestr + key + "=\"" + value + "\"";
	            	//prestr = prestr + key + "=" + value + "";
	            } else {
	                prestr = prestr + key + "=\"" + value + "\"&";
	               // prestr = prestr + key + "=" + value + "&";
	            }
	        }
	        return prestr;
	}
	
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    private static boolean verify(Map<String, String> params) {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
    	String responseTxt = "false";
		if(params.get("notify_id") != null) {
			String notify_id = params.get("notify_id");
			responseTxt = verifyResponse(notify_id);
			System.out.println("发送者验证结果：" + responseTxt);
		}
//	    String sign = "";
//	    if(params.get("sign") != null) {sign = params.get("sign");}
//	    boolean isSign = getSignVeryfy(params, sign);

        //写日志记录（若要调试，请取消下面两行注释）
//        String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AliPayCore.createAliPaylink(params);
//	    AlipayCore.logResult(sWord);

        if (responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
	public static boolean getSignVeryfy(Map<String, String> Params, String sign) {
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = paraFilter(Params);
        //获取待签名字符串
        String preSignStr = createAliPaylink(sParaNew);
        //获得签名验证结果
        boolean isSign = false;
        
        if(  RESOURCE_BUNDLE.getString("sign_type").equals("RSA")){
        	isSign = verify(preSignStr, sign,  RESOURCE_BUNDLE.getString("ali_public_key"),  RESOURCE_BUNDLE.getString("input_charset"));
        }
        return isSign;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param ali_public_key 支付宝公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	private static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
			boolean bverify = signature.verify(Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	* 解密
	* @param content 密文
	* @param private_key 商户私钥
	* @param input_charset 编码格式
	* @return 解密后的字符串
	*/
	public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

	
	/**
	* 得到私钥
	* @param key 密钥字符串（经过base64编码）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64.decode(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;
	}
	
	 /**
	    * 获取远程服务器ATN结果,验证返回URL
	    * @param notify_id 通知校验ID
	    * @return 服务器ATN结果
	    * 验证结果集：
	    * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
	    * true 返回正确信息
	    * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	    */
	    private static String verifyResponse(String notify_id) {
	        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
	    	System.out.println("验证是否是支付宝发送的信息");
	        String partner =  RESOURCE_BUNDLE.getString("partner");
	        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;
	        
	        return checkUrl(veryfy_url);
	    }

	    /**
	    * 获取远程服务器ATN结果
	    * @param urlvalue 指定URL路径地址
	    * @return 服务器ATN结果
	    * 验证结果集：
	    * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
	    * true 返回正确信息
	    * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	    */
	    private static String checkUrl(String urlvalue) {
	        String inputLine = "";

	        try {
	            URL url = new URL(urlvalue);
	            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
	                .getInputStream()));
	            inputLine = in.readLine().toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	            inputLine = "";
	        }

	        return inputLine;
	    }
	    
	    public static boolean verifyCallback(Map reqarg) {
//	    	try {
				//获取支付宝POST过来反馈信息
				Map<String,String> params = new HashMap<String,String>();
				for (Iterator iter = reqarg.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String[] values = (String[]) reqarg.get(name);
					String valueStr = "";
					for (int i = 0; i < values.length; i++) {
						valueStr = (i == values.length - 1) ? valueStr + values[i]
								: valueStr + values[i] + ",";
					}
					//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//					valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
					params.put(name, valueStr);
				}
				
				//验证成功，是支付宝发送过来得数据并且签名校检正确
				return AliPayUtil.verify(params);
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    	return false;
	    }
}
