package com.cz.yingpu.system.entity;

import java.io.Serializable;

public class ConfigBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public ConfigBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String apiKey ;
	private String appId ;
	private String cardNoSelect ;
	private String cardSelect ;
	private String jpushAppKey ;
	private String linkAndroid ;
	private String linkIos ;
	private String merchantNum ;
	private String mediaNoSelect ;
	private String mediaSelect ;
	private String myNoSelect ;
	private String mySelect ;
	private String partner ;
	private String password ;
	private String posterNoSelect ;
	private String posterSelect ;
	private String publishImage ;
	private String qqkey ;
	private String qqvalue ;
	private String rsaAlipayPublic ;
	private String rsaPrivate ;
	private String seller ;
	private String serviceURL ;
	private String serviceURL2 ;
	private String sn ;
	private String umengAppkey ;
	private String weibokey ;
	private String weibovalue ;
	private String weixinkey ;
	private String weixinvalue ;
	
	private String appDownload;
	private String appExplain;
	private String appVersionNumber;
	private String appVersionQiangzhiNumber;
	private String apkUrl ;
	private String appExplainIos;
	private String appVersionNumberIos;
	private String appVersionQiangzhiNumberIos;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getLinkAndroid() {
		return linkAndroid;
	}

	public void setLinkAndroid(String linkAndroid) {
		this.linkAndroid = linkAndroid;
	}

	public String getLinkIos() {
		return linkIos;
	}

	public void setLinkIos(String linkIos) {
		this.linkIos = linkIos;
	}

	public String getAppVersionQiangzhiNumber() {
		return appVersionQiangzhiNumber;
	}

	public void setAppVersionQiangzhiNumber(String appVersionQiangzhiNumber) {
		this.appVersionQiangzhiNumber = appVersionQiangzhiNumber;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getAppExplainIos() {
		return appExplainIos;
	}

	public void setAppExplainIos(String appExplainIos) {
		this.appExplainIos = appExplainIos;
	}

	public String getAppVersionNumberIos() {
		return appVersionNumberIos;
	}

	public void setAppVersionNumberIos(String appVersionNumberIos) {
		this.appVersionNumberIos = appVersionNumberIos;
	}

	public String getAppVersionQiangzhiNumberIos() {
		return appVersionQiangzhiNumberIos;
	}

	public void setAppVersionQiangzhiNumberIos(String appVersionQiangzhiNumberIos) {
		this.appVersionQiangzhiNumberIos = appVersionQiangzhiNumberIos;
	}

	public String getAppDownload() {
		return appDownload;
	}
	public void setAppDownload(String appDownload) {
		this.appDownload = appDownload;
	}
	public String getAppExplain() {
		return appExplain;
	}
	public void setAppExplain(String appExplain) {
		this.appExplain = appExplain;
	}
	public String getAppVersionNumber() {
		return appVersionNumber;
	}
	public void setAppVersionNumber(String appVersionNumber) {
		this.appVersionNumber = appVersionNumber;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCardNoSelect() {
		return cardNoSelect;
	}
	public void setCardNoSelect(String cardNoSelect) {
		this.cardNoSelect = cardNoSelect;
	}
	public String getCardSelect() {
		return cardSelect;
	}
	public void setCardSelect(String cardSelect) {
		this.cardSelect = cardSelect;
	}
	public String getJpushAppKey() {
		return jpushAppKey;
	}
	public void setJpushAppKey(String jpushAppKey) {
		this.jpushAppKey = jpushAppKey;
	}
	public String getMerchantNum() {
		return merchantNum;
	}
	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}
	public String getMediaNoSelect() {
		return mediaNoSelect;
	}
	public void setMediaNoSelect(String mediaNoSelect) {
		this.mediaNoSelect = mediaNoSelect;
	}
	public String getMediaSelect() {
		return mediaSelect;
	}
	public void setMediaSelect(String mediaSelect) {
		this.mediaSelect = mediaSelect;
	}
	public String getMyNoSelect() {
		return myNoSelect;
	}
	public void setMyNoSelect(String myNoSelect) {
		this.myNoSelect = myNoSelect;
	}
	public String getMySelect() {
		return mySelect;
	}
	public void setMySelect(String mySelect) {
		this.mySelect = mySelect;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPosterNoSelect() {
		return posterNoSelect;
	}
	public void setPosterNoSelect(String posterNoSelect) {
		this.posterNoSelect = posterNoSelect;
	}
	public String getPosterSelect() {
		return posterSelect;
	}
	public void setPosterSelect(String posterSelect) {
		this.posterSelect = posterSelect;
	}
	public String getPublishImage() {
		return publishImage;
	}
	public void setPublishImage(String publishImage) {
		this.publishImage = publishImage;
	}
	public String getQqkey() {
		return qqkey;
	}
	public void setQqkey(String qqkey) {
		this.qqkey = qqkey;
	}
	public String getQqvalue() {
		return qqvalue;
	}
	public void setQqvalue(String qqvalue) {
		this.qqvalue = qqvalue;
	}
	public String getRsaAlipayPublic() {
		return rsaAlipayPublic;
	}
	public void setRsaAlipayPublic(String rsaAlipayPublic) {
		this.rsaAlipayPublic = rsaAlipayPublic;
	}
	public String getRsaPrivate() {
		return rsaPrivate;
	}
	public void setRsaPrivate(String rsaPrivate) {
		this.rsaPrivate = rsaPrivate;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getServiceURL() {
		return serviceURL;
	}
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
	public String getServiceURL2() {
		return serviceURL2;
	}
	public void setServiceURL2(String serviceURL2) {
		this.serviceURL2 = serviceURL2;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getUmengAppkey() {
		return umengAppkey;
	}
	public void setUmengAppkey(String umengAppkey) {
		this.umengAppkey = umengAppkey;
	}
	public String getWeibokey() {
		return weibokey;
	}
	public void setWeibokey(String weibokey) {
		this.weibokey = weibokey;
	}
	public String getWeibovalue() {
		return weibovalue;
	}
	public void setWeibovalue(String weibovalue) {
		this.weibovalue = weibovalue;
	}
	public String getWeixinkey() {
		return weixinkey;
	}
	public void setWeixinkey(String weixinkey) {
		this.weixinkey = weixinkey;
	}
	public String getWeixinvalue() {
		return weixinvalue;
	}
	public void setWeixinvalue(String weixinvalue) {
		this.weixinvalue = weixinvalue;
	}
	
}
