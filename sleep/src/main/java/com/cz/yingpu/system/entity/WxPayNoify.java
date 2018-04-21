package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cz.yingpu.frame.annotation.WhereSQL;

@Table(name="t_wxpay_notify")
public class WxPayNoify {

		private Integer id;
		
		private String appid;
		
		private String mch_id;
		
		private String device_info;
		
		private String nonce_str;

		private String sign;

		private String result_code;

		private String err_code;

		private String err_code_des;

		private String openid;

		private String is_subscribe;

		private String trade_type;

		private String bank_type;

		private Integer total_fee;
		

		private String fee_type;

		private Integer cash_fee;
		
		@Id
	    @WhereSQL(sql="id=:id")
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
		@WhereSQL(sql="appid=:appid")
		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}
		@WhereSQL(sql="mch_id=:mch_id")
		public String getMch_id() {
			return mch_id;
		}

		public void setMch_id(String mch_id) {
			this.mch_id = mch_id;
		}
		@WhereSQL(sql="device_info=:device_info")
		public String getDevice_info() {
			return device_info;
		}

		public void setDevice_info(String device_info) {
			this.device_info = device_info;
		}
		@WhereSQL(sql="nonce_str=:nonce_str")
		public String getNonce_str() {
			return nonce_str;
		}

		public void setNonce_str(String nonce_str) {
			this.nonce_str = nonce_str;
		}
		@WhereSQL(sql="sign=:sign")
		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}
		@WhereSQL(sql="err_code=:err_code")
		public String getResult_code() {
			return result_code;
		}

		public void setResult_code(String result_code) {
			this.result_code = result_code;
		}
		@WhereSQL(sql="err_code=:err_code")
		public String getErr_code() {
			return err_code;
		}

		public void setErr_code(String err_code) {
			this.err_code = err_code;
		}
		@WhereSQL(sql="err_code_des=:err_code_des")
		public String getErr_code_des() {
			return err_code_des;
		}

		public void setErr_code_des(String err_code_des) {
			this.err_code_des = err_code_des;
		}
		@WhereSQL(sql="openid=:openid")
		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}
		@WhereSQL(sql="is_subscribe=:is_subscribe")
		public String getIs_subscribe() {
			return is_subscribe;
		}

		public void setIs_subscribe(String is_subscribe) {
			this.is_subscribe = is_subscribe;
		}
		@WhereSQL(sql="trade_type=:trade_type")
		public String getTrade_type() {
			return trade_type;
		}

		public void setTrade_type(String trade_type) {
			this.trade_type = trade_type;
		}
		@WhereSQL(sql="bank_type=:bank_type")
		public String getBank_type() {
			return bank_type;
		}

		public void setBank_type(String bank_type) {
			this.bank_type = bank_type;
		}
		@WhereSQL(sql="total_fee=:total_fee")
		public Integer getTotal_fee() {
			return total_fee;
		}

		public void setTotal_fee(Integer total_fee) {
			this.total_fee = total_fee;
		}
		@WhereSQL(sql="fee_type=:fee_type")
		public String getFee_type() {
			return fee_type;
		}

		public void setFee_type(String fee_type) {
			this.fee_type = fee_type;
		}
		@WhereSQL(sql="cash_fee=:cash_fee")
		public Integer getCash_fee() {
			return cash_fee;
		}

		public void setCash_fee(Integer cash_fee) {
			this.cash_fee = cash_fee;
		}
		@WhereSQL(sql="cash_fee_type=:cash_fee_type")
		public String getCash_fee_type() {
			return cash_fee_type;
		}

		public void setCash_fee_type(String cash_fee_type) {
			this.cash_fee_type = cash_fee_type;
		}
		@WhereSQL(sql="transaction_id=:transaction_id")
		public String getTransaction_id() {
			return transaction_id;
		}

		public void setTransaction_id(String transaction_id) {
			this.transaction_id = transaction_id;
		}
		@WhereSQL(sql="attach=:attach")
		public String getOut_trade_no() {
			return out_trade_no;
		}

		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}
		@WhereSQL(sql="attach=:attach")
		public String getAttach() {
			return attach;
		}

		public void setAttach(String attach) {
			this.attach = attach;
		}
		@WhereSQL(sql="time_end=:time_end")
		public String getTime_end() {
			return time_end;
		}

		public void setTime_end(String time_end) {
			this.time_end = time_end;
		}

		private String cash_fee_type;

		private String transaction_id;

		private String out_trade_no;

		private String attach;

		private String time_end;

		
}
