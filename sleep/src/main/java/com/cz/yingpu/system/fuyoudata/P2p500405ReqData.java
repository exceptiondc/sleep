package com.cz.yingpu.system.fuyoudata;

import java.io.Serializable;

import com.fuiou.util.SecurityUtils;

/**
 * PC端个人用户免登录快捷充值
 * @author aj
 *
 */
public class P2p500405ReqData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mchnt_cd="";
	private String mchnt_txn_ssn="";
	private String login_id="";
	private String amt="";
	private String page_notify_url="";
	private String back_notify_url="";
	private String signature="";
	
	public String createSignValue(){
		String src =amt + "|" + back_notify_url + "|" + login_id+"|"+ mchnt_cd + "|" + mchnt_txn_ssn+ "|" + page_notify_url;
		System.out.println("请求接口验证签名明文>>>>"+src);
		return src;
	}
	
	public String getMchnt_cd() {
		return mchnt_cd;
	}
	public void setMchnt_cd(String mchnt_cd) {
		this.mchnt_cd = mchnt_cd;
	}
	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}
	public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
		this.mchnt_txn_ssn = mchnt_txn_ssn;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getPage_notify_url() {
		return page_notify_url;
	}
	public void setPage_notify_url(String page_notify_url) {
		this.page_notify_url = page_notify_url;
	}
	public String getBack_notify_url() {
		return back_notify_url;
	}
	public void setBack_notify_url(String back_notify_url) {
		this.back_notify_url = back_notify_url;
	}
	public String getSignature() {
		signature = SecurityUtils.sign(this.createSignValue());
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
}
