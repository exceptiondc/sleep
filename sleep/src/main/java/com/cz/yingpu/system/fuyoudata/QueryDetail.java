package com.cz.yingpu.system.fuyoudata;

/**
 * 明细查询详情
 * @author aj
 *
 */
public class QueryDetail {
	private String trans_ssn="";//交易流水号
	private String rec_crt_ts="";//记账时间
	private String ct_debit_amt="";//账面余额出账金额
	private String ct_credit_amt="";//账面余额入账金额
	private String ca_debit_amt="";//可用余额出账金额
	private String ca_credit_amt="";//可用余额入账金额
	private String cu_debit_amt="";//未转结余额出账金额
	private String cu_credit_amt="";//未转结余额入账金额
	private String cf_debit_amt="";//冻结余额出账金额
	private String cf_credit_amt="";//冻结余额入账金额
	private String ct_balance="";//账面余额
	private String ca_balance="";//可用余额
	private String cu_balance="";//未转接余额
	private String cf_balance="";//冻结余额
	private String book_digest="";//摘要信息
	
	@Override
	public String toString() {
		return "QueryDetail [trans_ssn=" + trans_ssn + ", rec_crt_ts="
				+ rec_crt_ts + ", ct_debit_amt=" + ct_debit_amt
				+ ", ct_credit_amt=" + ct_credit_amt + ", ca_debit_amt="
				+ ca_debit_amt + ", ca_credit_amt=" + ca_credit_amt
				+ ", cu_debit_amt=" + cu_debit_amt + ", cu_credit_amt="
				+ cu_credit_amt + ", cf_debit_amt=" + cf_debit_amt
				+ ", cf_credit_amt=" + cf_credit_amt + ", ct_balance="
				+ ct_balance + ", ca_balance=" + ca_balance + ", cu_balance="
				+ cu_balance + ", cf_balance=" + cf_balance + ", book_digest="
				+ book_digest + "]";
	}

	public String getTrans_ssn() {
		return trans_ssn;
	}

	public void setTrans_ssn(String trans_ssn) {
		this.trans_ssn = trans_ssn;
	}

	public String getRec_crt_ts() {
		return rec_crt_ts;
	}
	public void setRec_crt_ts(String rec_crt_ts) {
		this.rec_crt_ts = rec_crt_ts;
	}
	public String getCt_debit_amt() {
		return ct_debit_amt;
	}
	public void setCt_debit_amt(String ct_debit_amt) {
		this.ct_debit_amt = ct_debit_amt;
	}
	public String getCt_credit_amt() {
		return ct_credit_amt;
	}
	public void setCt_credit_amt(String ct_credit_amt) {
		this.ct_credit_amt = ct_credit_amt;
	}
	public String getCa_debit_amt() {
		return ca_debit_amt;
	}
	public void setCa_debit_amt(String ca_debit_amt) {
		this.ca_debit_amt = ca_debit_amt;
	}
	public String getCa_credit_amt() {
		return ca_credit_amt;
	}
	public void setCa_credit_amt(String ca_credit_amt) {
		this.ca_credit_amt = ca_credit_amt;
	}
	public String getCu_debit_amt() {
		return cu_debit_amt;
	}
	public void setCu_debit_amt(String cu_debit_amt) {
		this.cu_debit_amt = cu_debit_amt;
	}
	public String getCu_credit_amt() {
		return cu_credit_amt;
	}
	public void setCu_credit_amt(String cu_credit_amt) {
		this.cu_credit_amt = cu_credit_amt;
	}
	public String getCf_debit_amt() {
		return cf_debit_amt;
	}
	public void setCf_debit_amt(String cf_debit_amt) {
		this.cf_debit_amt = cf_debit_amt;
	}
	public String getCf_credit_amt() {
		return cf_credit_amt;
	}
	public void setCf_credit_amt(String cf_credit_amt) {
		this.cf_credit_amt = cf_credit_amt;
	}
	public String getCt_balance() {
		return ct_balance;
	}
	public void setCt_balance(String ct_balance) {
		this.ct_balance = ct_balance;
	}
	public String getCa_balance() {
		return ca_balance;
	}
	public void setCa_balance(String ca_balance) {
		this.ca_balance = ca_balance;
	}
	public String getCu_balance() {
		return cu_balance;
	}
	public void setCu_balance(String cu_balance) {
		this.cu_balance = cu_balance;
	}
	public String getCf_balance() {
		return cf_balance;
	}
	public void setCf_balance(String cf_balance) {
		this.cf_balance = cf_balance;
	}
	public String getBook_digest() {
		return book_digest;
	}
	public void setBook_digest(String book_digest) {
		this.book_digest = book_digest;
	}
	
	
	
	
}
