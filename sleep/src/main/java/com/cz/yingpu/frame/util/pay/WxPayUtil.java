package com.cz.yingpu.frame.util.pay;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cz.yingpu.frame.util.DateUtils;
import com.cz.yingpu.system.entity.AccessToken;






public class WxPayUtil {
	  private static AccessToken accesstoken;	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("wechatpay");
	
	 public static synchronized  AccessToken getAccessToken(String code) {
	    	try {
	    		if(accesstoken==null){
	    			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"
	    					.replace("APPID",  RESOURCE_BUNDLE.getString("AppId")).replace("SECRET",  RESOURCE_BUNDLE.getString("AppSecre")).replace("CODE", code);  
	    			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
	    			accesstoken=new AccessToken();
	    			accesstoken.setToken(jsonObject.getString("access_token"));
	    			accesstoken.setGetdate(DateUtils.convertDate2String("yyyy-MM-dd HH:mm:ss", new Date()));
	    			
	    		}else{
	    			if(gettimecha(accesstoken.getGetdate())>7100){
	    				String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"
		    					.replace("APPID",  RESOURCE_BUNDLE.getString("AppId")).replace("SECRET",  RESOURCE_BUNDLE.getString("AppSecre")).replace("CODE", code);  
	        			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
	        			accesstoken=new AccessToken();
	        			accesstoken.setToken(jsonObject.getString("access_token"));
	        			accesstoken.setGetdate(DateUtils.convertDate2String("yyyy-MM-dd HH:mm:ss", new Date()));
	    			}
	    		}
	    		System.out.println(accesstoken.getToken()+"   "+accesstoken.getGetdate());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    	return accesstoken;
	    }
	 
	   /** 
	     * 发起https请求并获取结果 
	     *  
	     * @param requestUrl 请求地址 
	     * @param requestMethod 请求方式（GET、POST） 
	     * @param outputStr 提交的数据 
	     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
	     */  
	    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
	        JSONObject jsonObject = null;  
	        StringBuffer buffer = new StringBuffer();  
	        try {  
	            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
	            TrustManager[] tm = { new com.cz.yingpu.system.entity.MyX509TrustManager() };  
	            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
	            sslContext.init(null, tm, new java.security.SecureRandom());  
	            // 从上述SSLContext对象中得到SSLSocketFactory对象  
	            SSLSocketFactory ssf = sslContext.getSocketFactory();  
	  
	            URL url = new URL(requestUrl);  
	            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
	            httpUrlConn.setSSLSocketFactory(ssf);
	            httpUrlConn.setConnectTimeout(5000);
	            httpUrlConn.setReadTimeout(4000);
	  
	            httpUrlConn.setDoOutput(true);  
	            httpUrlConn.setDoInput(true);  
	            httpUrlConn.setUseCaches(false);  
	            // 设置请求方式（GET/POST）  
	            httpUrlConn.setRequestMethod(requestMethod);  
	            if ("GET".equalsIgnoreCase(requestMethod))  
	                httpUrlConn.connect();  
	  
	            // 当有数据需要提交时  
	            if (null != outputStr) {  
	                OutputStream outputStream = httpUrlConn.getOutputStream();  
	                // 注意编码格式，防止中文乱码  
	                outputStream.write(outputStr.getBytes("UTF-8"));  
	                outputStream.close();  
	            }  
	  
	            // 将返回的输入流转换成字符串  
	            InputStream inputStream = httpUrlConn.getInputStream();  
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
	  
	            String str = null;  
	            while ((str = bufferedReader.readLine()) != null) {  
	                buffer.append(str);  
	            }  
	            bufferedReader.close();  
	            inputStreamReader.close();  
	            // 释放资源  
	            inputStream.close();  
	            inputStream = null;  
	            httpUrlConn.disconnect();
	            jsonObject = JSONObject.fromObject(buffer.toString()); 
	        } catch (Exception e) {  
	        	e.printStackTrace();
	        }  
	        return jsonObject;  
	    }
	 /**
		 * 根据传入的时间获取和现在相差多少秒
		 * 
		 * @param 按照一定格式获取当前时间
		 * 
		 */
		public static long gettimecha(String datestr) {
			long hour = 0;
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1 = new Date();
				Date d2 = df.parse(datestr);
				long diff = d1.getTime() - d2.getTime();
				hour = diff / 1000;
			} catch (Exception e) {
			}
			return hour;
		}
	//微信带证书的请求
 	 public static String httprequestbyUtf(String requestUrl,String xml,String certurl,String payid){
		 StringBuffer buffer = new StringBuffer();  
		 try {
			 KeyStore keyStore = KeyStore.getInstance("PKCS12");
			 FileInputStream instream = new FileInputStream(new File(certurl));
			 
			 try {
				 keyStore.load(instream, payid.toCharArray());
			 } finally {
				 instream.close();
			 }
			 // Trust own CA and all self-signed certs
			 SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, payid.toCharArray()).build();
			 // Allow TLSv1 protocol only
			 SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
					 SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			 CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			 try {
				 HttpPost httpPost = new HttpPost(requestUrl);
				 //中文不成功是因为没加编码
				 StringEntity myEntity = new StringEntity(xml, "UTF-8");
				 httpPost.setEntity(myEntity);
				 CloseableHttpResponse response = httpclient.execute(httpPost);
				 try {
					 HttpEntity entity1 = response.getEntity();
					 if (entity1 != null) {
						 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity1.getContent()));
						 String text;
						 while ((text = bufferedReader.readLine()) != null) {
							 buffer.append(new String(text.getBytes("GBK"), "UTF-8"));
						 }
					 }
					 EntityUtils.consume(entity1);
				 } catch (Exception e) {
					 e.printStackTrace();
				 } finally {
					 response.close();
				 }
			 } finally {
				 httpclient.close();
			 }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return buffer.toString();
	 }
	
 	/**
 	 *微信退款接口 
 	 * @param out_refund_no 商户退款订单号
 	 * @param out_trade_no 商户订单号
 	 * @param total_fee 退款总金额
 	 * @param refund_fee 需要退款的金额，该金额不能大于订单金额,单位为分，支持两位小数
 	 * @param refund_desc 退款原因
 	 * @author dc
 	 * @return
 	 */
 	 public static Map<String, String> refund(String out_refund_no,String out_trade_no,String total_fee,String refund_fee,String refund_desc){
		 try {
			 String randomuuid=UUID.randomUUID().toString().replace("-", "");
			 
			 HashMap<String,String> map=new HashMap<String,String>();
			 //AppId
			 map.put("appid", RESOURCE_BUNDLE.getString("AppId"));
			 //商户号
			 map.put("mch_id", RESOURCE_BUNDLE.getString("PayId"));
		    	//随机字符串
    	    	map.put("nonce_str", randomuuid);
		   	 //商户退款单号
    	    	map.put("out_refund_no", out_refund_no);
	    	 // 订单总金额
    	    	map.put("total_fee", total_fee);
    	    	 // 退款金额
    	    	map.put("refund_fee", refund_fee);
    	    	 // 货币种类(一般是人民币)
    	    	map.put("refund_fee_type", "CNY");
    	    	 // 商户用来退款订单号
    	    	map.put("out_trade_no", out_trade_no);
    	    	 // 商户用来退款订单号
    	    	map.put("refund_desc", refund_desc);
	    		//签名
	    		map.put("sign", getSign(map,RESOURCE_BUNDLE.getString("paykey"),false));
	    		//String restultxml=(httpRequest("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", "POST", MyUtil.toXML(map),wemap.get("cert_url"),wemap.get("payid")));
	    		String restultxml=httprequestbyUtf("https://api.mch.weixin.qq.com/secapi/pay/refund",toXML(map),RESOURCE_BUNDLE.getString("cert_path"),RESOURCE_BUNDLE.getString("PayId"));
	    		return parseXml(restultxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return null;
	 }
	
 	 
 	/**
  	 *微信退款查询接口 
  	 * @param out_refund_no 商户退款订单号
  	 * @author dc
  	 * @return
  	 */
  	 public static Map<String, String> refundQuery(String out_refund_no){
 		 try {
 			 String randomuuid=UUID.randomUUID().toString().replace("-", "");
 			 
 			 HashMap<String,String> map=new HashMap<String,String>();
 			 //AppId
 			 map.put("appid", RESOURCE_BUNDLE.getString("AppId"));
 			 //商户号
 			 map.put("mch_id", RESOURCE_BUNDLE.getString("PayId"));
 		    	//随机字符串
     	    	map.put("nonce_str", randomuuid);
     	    	 // 商户用来退款订单号
     	    	map.put("out_refund_no", out_refund_no);
     	    	
     	    	
     	    	map.put("out_refund_no", "");
 	    		//签名
 	    		map.put("sign", getSign(map,RESOURCE_BUNDLE.getString("paykey"),false));
 	    		
 	    		String xmlResult = sendRequest("https://api.mch.weixin.qq.com/pay/refundquery", map, 20);
 	    		return parseXml(xmlResult);
 		} catch (Exception e) {
 			// TODO: handle exception
 			e.printStackTrace();
 		}
 		 return null;
 	 }
	
 	public static String sendRequest(String Url, Map<String, String> param,int timeout) {
		String complete = null;
		URL url = null;
		try {
			url = new URL(Url);
			//建立连接
			HttpURLConnection connect = (HttpURLConnection)url.openConnection();
			connect.setRequestMethod("POST");
			connect.setRequestProperty("Content-Type", 
			        "application/x-www-form-urlencoded");
			
			//设置输入输出参数
			connect.setDoInput(true);
			connect.setDoOutput(true);
			connect.setConnectTimeout(timeout*1000);
			DataOutputStream out = new DataOutputStream(connect.getOutputStream());
			//输入参数
			String pv = "";
			StringBuilder sbr = new StringBuilder();
			if(param != null) {
				Set<String> paramNames = param.keySet();
				Iterator<String> iterator = paramNames.iterator();
				while(iterator.hasNext()) {
					String paramName = iterator.next();
					sbr.append(paramName + "=");
					sbr.append(param.get(paramName));
					sbr.append("&");
				}
				pv = sbr.substring(0, sbr.length()-1).toString();
			}
			out.writeBytes(pv);
			
			//发起请求
			InputStream is = connect.getInputStream();
			
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8"));
		    StringBuilder result = new StringBuilder(); // or StringBuffer if not Java 5+ 
		    String line;
		    while((line = rd.readLine()) != null) {
		    	result.append(line);
		    }
		    rd.close();
			out.flush();
			out.close();
			complete = result.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(Url);
			System.out.println("上面请求路径出错");
		} 
		
		return complete;
	}
	
	//生成sign map
	public static Map<String,Object> getSignMap(String device,String title,String attach,String orderNumber,String totalFee,HttpServletRequest request){
		
		Map<String, String> map=new HashMap<String,String>();
		map.put("ordernumber", orderNumber);
		map.put("totalFee",totalFee);
		map.put("title", title);
		map.put("attach", attach);
		map.put("device_info",device);
		
		Map<String, Object> Signmap=new HashMap<String, Object>();
		Signmap.put("AppId", RESOURCE_BUNDLE.getString("AppId"));
		Signmap.put("partnerid",  RESOURCE_BUNDLE.getString("PayId"));
		
		try {
			Signmap.put("prepayid", parseXml(appWxPay(map,
							getIpAddr(request),false)).get("prepay_id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Signmap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		Signmap.put("noncestr", ((int)(Math.random()*900000+100000))+"");
		Signmap.put("package", "Sign=WXPay");
		Signmap.put("sign", getSign(Signmap));
		
		return null;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getSign(Map<String, Object> map) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + RESOURCE_BUNDLE.getString("paykey");
		// com.pay.Util.log("Sign Before com.pay.MD5:" + result);
		result = new WXMD5().MD5Encode(result).toUpperCase();
		// com.pay.Util.log("Sign Result:" + result);
		return result;
	}
	  //APP微信下单接口
	 public static String appWxPay(Map<String, String> pamap,String ip,Boolean isTranscod){
		String prepay_id="FAIL";
		Map<String, String> map=new HashMap<String, String>();
		try {
			String randomuuid=UUID.randomUUID().toString().replace("-", "");
			map.put("appid",  RESOURCE_BUNDLE.getString("AppId"));
			map.put("mch_id", RESOURCE_BUNDLE.getString("PayId"));
			//map.put("device_info", pamap.get("device_info"));

			//map.put("device_info", "WEB");
			map.put("nonce_str", randomuuid);
			
			if(isTranscod==false){
				//商品描述
				map.put("body",  pamap.get("title"));
				//附加数据，微信通知原样返回
				map.put("attach", pamap.get("attach"));
				
			}else{
				//签名
				//商品描述
				map.put("body",  new String(pamap.get("title").getBytes("utf-8"),"iso8859-1"));
				//附加数据，微信通知原样返回
				map.put("attach", new String(pamap.get("attach").getBytes("utf-8"),"iso8859-1"));
				
			}
			
			
			//根据商户更改（商户订单号）
			map.put("out_trade_no", pamap.get("ordernumber"));
			map.put("fee_type", "CNY");
			map.put("total_fee", (int)Double.parseDouble(pamap.get("totalFee"))+"");
			//map.put("openid", pamap.get("openid"));
			map.put("notify_url", RESOURCE_BUNDLE.getString("wx_pay_back_url"));
			//根据商户更改（调用服务的ip）
			map.put("spbill_create_ip", ip);
			//根据商户更改（调用服务的支付结果通知路径）
			map.put("trade_type", "APP");
			map.put("sign", getSign(map,RESOURCE_BUNDLE.getString("paykey") ,true));
			//Map<String, String> wxpaymap=MyUtil.parseXml(sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder",toXML(map)));
			//prepay_id=wxpaymap.get("prepay_id");
			System.out.println(toXML(map));
			prepay_id=sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder",toXML(map));
			System.out.println(new String (prepay_id.getBytes("iso-8859-1"),"utf-8"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "FAIL";
		}
		return prepay_id;
	 }
	  	public static String sendPost(String url, String xml) {
	        String result = null;
	       DefaultHttpClient client = new DefaultHttpClient();
	       HttpPost httpPost = new HttpPost(url);
	       //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
	       StringEntity postEntity = null;
	       try {
	    	 //  xml=new String(xml.getBytes(),"UTF-8");
	       postEntity = new StringEntity(xml);
	       httpPost.addHeader("Content-Type", "text/xml");
	       httpPost.setEntity(postEntity);
	       //设置请求器的配置
//	       httpPost.setConfig(requestConfig);
	           HttpResponse response = client.execute(httpPost);
	           // 若状态码为200 ok
	           // 取出回应字串
	           result = EntityUtils.toString(response.getEntity());
	       } catch (Exception e) {
	           e.printStackTrace();
	       }


	       return result;
	   }
	 
	 	public static String getSign(Map<String,String> map,String Secretkey,Boolean iszhuanma) throws Exception{
	        ArrayList<String> list = new ArrayList<String>();
	        for(Map.Entry<String,String> entry:map.entrySet()){
	            if(entry.getValue()!=""){
	                list.add(entry.getKey() + "=" + entry.getValue()+ "&");
	            }
	        }
	        int size = list.size();
	        String [] arrayToSort = list.toArray(new String[size]);
	        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
	        StringBuilder sb = new StringBuilder();
	        for(int i = 0; i < size; i ++) {
	            sb.append(arrayToSort[i]);
	        }
	        String result = sb.toString();
	        result += "key=" + Secretkey;
	        //com.pay.Util.log("Sign Before com.pay.MD5:" + result);
	        if(iszhuanma){
	        	 result=new String(result.getBytes("ISO-8859-1"),"UTF-8");
	        }
	       
	        result = WXMD5.MD5Encode(result).toUpperCase();
	        //com.pay.Util.log("Sign Result:" + result);
	        return result;
	    }
	 	
	 	public static String toXML(Map map) {
	 		StringBuffer xml = new StringBuffer();
	 		xml.append("<xml>\n");
	 		for (Object key:map.keySet()) {
	 			xml.append("<"+key.toString()+">" + map.get(key) + "</"+key.toString()+">\n");
	 		}
	 		xml.append("</xml>");
	 		return xml.toString();
	    }
		public static Map<String, String> parseXml(String request) throws Exception {
			// 将解析结果存储在HashMap中
			Map<String, String> map = new HashMap<String, String>();
			// 从request中取得输入流
			InputStream inputStream = new ByteArrayInputStream(request.getBytes("UTF-8"));
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

			// 释放资源
			inputStream.close();
			inputStream = null;

			return map;
		}

		

		
		public static Map<String, String> parseXml(javax.servlet.http.HttpServletRequest request) throws Exception {
			// 将解析结果存储在HashMap中
			Map<String, String> map = new HashMap<String, String>();

			// 从request中取得输入流
			InputStream inputStream = request.getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

			// 释放资源
			inputStream.close();
			inputStream = null;

			return map;
		}

}
