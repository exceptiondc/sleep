package com.cz.yingpu.frame.util.fuyou;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientHelper{
    
    public static String doHttp(String urlStr,String charSet,Object parameters, String timeOut) throws Exception{
    	String responseString="";
    	PostMethod xmlpost;
 	    int statusCode = 0;
 	    HttpClient httpclient = new HttpClient();
// 	    httpclient.setConnectionTimeout(new Integer(timeOut).intValue());
// 	    httpclient.getParams().setConnectionManagerTimeout(new Long(timeOut));
 	   httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(new Integer(timeOut).intValue());
 	   httpclient.getHttpConnectionManager().getParams().setSoTimeout(new Integer(timeOut).intValue());
 	    xmlpost = new PostMethod(urlStr);
 	    httpclient.getParams().setParameter(
 	    		HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);
        try{
        	//组合请求参数
        	List<NameValuePair> list=new ArrayList<NameValuePair>();
			Method[] ms=parameters.getClass().getMethods();
			for(int i=0;i<ms.length;i++){
				Method m=ms[i];
				String name=m.getName();
				if(name.startsWith("get")){
					String param=name.substring(3,name.length());
					param=param.substring(0,1).toLowerCase()+param.substring(1,param.length());
					if(param.equals("class")){
						continue;
					}
					Object value="";
					try {
						value = m.invoke(parameters, null);
//						LogWriter.error("=====>name="+m.getName()+",value="+value);
						NameValuePair nvp=new NameValuePair(param,(value==null?"":value.toString()));
						list.add(nvp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw e;
					}
				}
			}
			NameValuePair[] nvps=new NameValuePair[list.size()];
        	xmlpost.setRequestBody(list.toArray(nvps)); 
        	statusCode = httpclient.executeMethod(xmlpost);
	    	responseString = xmlpost.getResponseBodyAsString();
//        	statusCode=HttpURLConnection.HTTP_OK;
//	    	responseString = "";
            if(statusCode<HttpURLConnection.HTTP_OK || statusCode>=HttpURLConnection.HTTP_MULT_CHOICE){
                throw new Exception("请求接口失败，失败码[ "+ statusCode +" ]");
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        } finally{
        	xmlpost.releaseConnection();
        	httpclient.getHttpConnectionManager().closeIdleConnections(0);
        }
        return responseString;
    }
    
    public static String doHttp(String urlStr,String charSet,Map parameters, String timeOut) throws Exception{
    	String responseString="";
    	PostMethod xmlpost;
 	    int statusCode = 0;
 	    HttpClient httpclient = new HttpClient();
// 	    httpclient.setConnectionTimeout(new Integer(timeOut).intValue());
// 	   httpclient.getParams().setConnectionManagerTimeout(new Long(timeOut));
 	   httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(new Integer(timeOut).intValue());
 	   httpclient.getHttpConnectionManager().getParams().setSoTimeout(new Integer(timeOut).intValue());
 	    xmlpost = new PostMethod(urlStr);
 	    httpclient.getParams().setParameter(
 	    		HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);
        try{
        	//组合请求参数
        	List<NameValuePair> list=new ArrayList<NameValuePair>();
			Iterator it=parameters.keySet().iterator();
			while(it.hasNext()){
				String key=(String)it.next();
				NameValuePair nvp=new NameValuePair(key,(parameters.get(key)==null?"":parameters.get(key).toString()));
				list.add(nvp);
			}
			
			NameValuePair[] nvps=new NameValuePair[list.size()];
        	xmlpost.setRequestBody(list.toArray(nvps)); 
        	statusCode = httpclient.executeMethod(xmlpost);
	    	responseString = xmlpost.getResponseBodyAsString();
            if(statusCode<HttpURLConnection.HTTP_OK || statusCode>=HttpURLConnection.HTTP_MULT_CHOICE){
                throw new Exception("请求接口失败，失败码[ "+ statusCode +" ]");
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        } finally{
        	xmlpost.releaseConnection();
        	httpclient.getHttpConnectionManager().closeIdleConnections(0);
        }
        return responseString;
    }
    
    @SuppressWarnings("deprecation")
    public static String httpFile(String url,Map<String, String> pmap,String[] files) throws Exception{
    	HttpPost httpPost = new HttpPost(url);
    	httpPost.setHeader("User-Agent","SOHUWapRebot");
    	httpPost.setHeader("Accept-Language","zh-cn,zh;q=0.5");
    	httpPost.setHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.7");
    	httpPost.setHeader("Connection","keep-alive");
		MultipartEntity mutiEntity = new MultipartEntity();
		
    	for (Map.Entry<String,String> o :pmap.entrySet() ) {
    		mutiEntity.addPart(o.getKey(),new StringBody( o.getValue().replaceAll("(\\.0*)?", "")));
    		System.out.println(o.getKey() + " => " + o.getValue().replaceAll("(\\.0*)?", ""));
		}
    	for (int i = 0; i < files.length; i++) {
    		File file = new File(files[i]);
    		mutiEntity.addPart("file"+(i+1), new FileBody(file));
    		System.out.println("file"+(i+1) + " => " + file.getName());
		}
    	
    	
    	httpPost.setEntity(mutiEntity);
    	@SuppressWarnings("resource")
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
    	HttpEntity httpEntity =httpResponse.getEntity();
    	String content = EntityUtils.toString(httpEntity);
    	return content;
    }

}
