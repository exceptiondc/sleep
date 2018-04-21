package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cz.yingpu.frame.util.CheckSumBuilder;


public class SMSUtil {
	
//	public static final String serviceURL = "http://sdk.entinfo.cn:8060/z_mdsmssend.aspx";
	public static final String serviceURL = "http://114.55.88.173:9988/sms.aspx";
//	public static final String userid = "78";
//	public static final String password = "CJKFC123";
//	public static final String account = "CJKFC";
	
	
	
	//发送验证码的请求路径URL
    private static final String
            SERVER_URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String 
            APP_KEY="fe698e1854d7894a67e23ebf94511df3";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="35525dcf7ad7";
    //随机数
    private static final String NONCE="123456";
    //短信模板ID
    private static final String TEMPLATEID="3139274";
    //手机号
    private static final String MOBILE="18792546455";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN="4";
    
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
			.setConnectionRequestTimeout(15000).build();
    @SuppressWarnings("deprecation")
	public static void main(String[] args)  {
    	try {
    		HttpPost httpPost = new HttpPost(SERVER_URL);// 创建httpPost
    		  String curTime = String.valueOf((new Date()).getTime() / 1000L);
    		httpPost.addHeader("AppKey", APP_KEY);
            httpPost.addHeader("Nonce", NONCE);
            httpPost.addHeader("CurTime", curTime);
            String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            
    		// 创建参数队列
    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		nameValuePairs.add(new BasicNameValuePair("templateid", TEMPLATEID));
    		nameValuePairs.add(new BasicNameValuePair("mobile", MOBILE));
    		nameValuePairs.add(new BasicNameValuePair("codeLen", CODELEN));

    		try {
    			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		
    		CloseableHttpClient httpClient = null;
    		CloseableHttpResponse response = null;
    		HttpEntity entity = null;
    		String responseContent = null;
    		try {
    			// 创建默认的httpClient实例.
    			httpClient = HttpClients.createDefault();
    			
    			httpPost.setConfig(requestConfig);
    			// 执行请求
    			response = httpClient.execute(httpPost);
    			entity = response.getEntity();
    			responseContent = EntityUtils.toString(entity, "UTF-8");
    		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			try {
    				// 关闭连接,释放资源
    				if (response != null) {
    					response.close();
    				}
    				if (httpClient != null) {
    					httpClient.close();
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		System.out.println(responseContent); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    }

    
	//发送短信
	public static String SendSMS(String userid ,String account,String password ,String mobile, String content) throws Exception {
		String smsUrl = serviceURL+"?action=send&password="+password+"&account="+account+"&userid="+userid+"&mobile="+mobile+"&content="+URLEncoder.encode(content,"UTF-8");
		URL url = new URL(smsUrl);
 		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 		conn.setConnectTimeout(5000);
 		conn.setRequestMethod("GET");
 		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded,charset=UTF-8");
 		conn.setRequestProperty("Accept-Charset", "UTF-8");
 		conn.setRequestProperty("contentType", "UTF-8");
 		String json = "";
 		if (conn.getResponseCode() == 200) {
 			InputStream inStream = conn.getInputStream();
 			byte[] data = readInputStream(inStream);
 	 		 json = new String(data);
 		}
		System.out.println(smsUrl);
 		return json;
	}
	
	public  static String SendSMS1(){
		
		return "";
	}

	  public static byte[] readInputStream(InputStream instream) throws Exception {
	 		ByteArrayOutputStream outStream = new ByteArrayOutputStream();//读到的数据放到内存中
	 		byte []buffer = new  byte[1024];
	 		int len = 0;
	 		while((len = instream.read(buffer)) !=-1){
	 			outStream.write(buffer, 0, len);//往内存中写入数据
	 		}
	 		instream.close();
	 	 return outStream.toByteArray();
	 	}

	  
	  
	  

	/*public static void main(String[] args) throws Exception {
		SMSUtil smsUtil = new SMSUtil();
//		File file =
//		String status =  smsUtil.SendSMS("178", "yingpucaifu","yingpucaifu",
//				"18538036976,15638110867,15675874592,13552489405,13051958919,15574982687,18866718554,17603880567,15874184619,13552489381,13161635258,15100681603,13121672610,15714166111,15612833359,15978679728,15069546315,13667314388,15679453839,17773417392,13889330405,18673166626,15224202010,15942570778,13125240153,18684882955,18890693611,13322448884,15017473016,13310623051,15066536991,13011828472,15526447209,13077347312,15116285857,15106812712,13170949290,13161626569,15804067200,18842531447" ,
//				"【贾克斯金服】尊敬的贾克斯金服投资者您好，您所投资的D计划10008期已进行第三期还款，本息已到您的账户内，请您及时查收，期待您的再次投资！如有疑问，请联系客服：4009691811\n");
		String status =  SMSUtil.SendSMS("178", "yingpucaifu","yingpucaifu",
				"18538036976,15638110867,18089332255,13548583124,18650311737,13272317029,18570623033,13789293417,18163744136,15093179367",
				"【贾克斯金服】尊敬的贾克斯金服投资者您好，您所投资的D计划已进行第三期还款，本息已到您的账户内，请您及时查收，期待您的再次投资");

				System.out.println(status);
	}*/
}
