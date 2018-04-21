package com.cz.yingpu.frame.util.fuyou;



import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class HttpX509TrustManager implements X509TrustManager{

    public HttpX509TrustManager(){
    }

    @Override
	public void checkClientTrusted(X509Certificate ax509certificate[], String s){
    }

    @Override
	public void checkServerTrusted(X509Certificate ax509certificate[], String s){
    }

    @Override
	public X509Certificate[] getAcceptedIssuers(){
        return null;
    }
}
