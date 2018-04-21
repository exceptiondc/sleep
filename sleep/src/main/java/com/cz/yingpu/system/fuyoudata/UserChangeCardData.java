package com.cz.yingpu.system.fuyoudata;

import java.io.File;
import java.io.Serializable;

/**
 * 
 * @author aj
 *
 */
public class UserChangeCardData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resp_code="";
	private String mchnt_cd="";
	private String mchnt_txn_ssn="";
	private String login_id="";
	private String city_id="";
	private String bank_cd="";
	private String card_no="";
	private File file1;
	private File file2;
	
	
	public File getFile1() {
		return file1;
	}

	public void setFile1(File file1) {
		this.file1 = file1;
	}

	public File getFile2() {
		return file2;
	}

	public void setFile2(File file2) {
		this.file2 = file2;
	}

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
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

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getBank_cd() {
		return bank_cd;
	}

	public void setBank_cd(String bank_cd) {
		this.bank_cd = bank_cd;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	private String signature="";
	
	public String createSignValue(){
		String src = bank_cd +"|"+ card_no + "|"+ city_id +"|" +login_id +"|"+mchnt_cd +"|" +mchnt_txn_ssn;
		return src;
	}
	
	
	
}
