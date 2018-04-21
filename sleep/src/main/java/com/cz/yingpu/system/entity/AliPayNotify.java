package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cz.yingpu.frame.annotation.WhereSQL;

@Table(name="t_alipay_notify")
public class AliPayNotify {
		
	private Integer id;
	
	private String notify_time;
	
	private String notify_type;
	
	private String notify_id;
	
	private String sign_type;
	
	private String sign;
	
	private String out_trade_no;
	
	private String subject;
	
	private String payment_type;
	
	private String trade_no;
	
	private String trade_status;
	
	private String seller_id;
	
	private String seller_email;
	
	private String buyer_id;
	
	private String buyer_email;
	
	private Double total_fee;
	
	private Double quantity;
	
	private Double price;
	
	private String body;
	
	private String gmt_create;
	
	private String gmt_payment;
	
	private String is_total_fee_adjust;
	
	private String use_coupon;
	
	private String discount;
	
	private String refund_status;
	
	private String gmt_refund;
	@Id
    @WhereSQL(sql="id=:id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    @WhereSQL(sql="notify_time=:notify_time")
	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	 @WhereSQL(sql="notify_type=:notify_type")
	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	 @WhereSQL(sql="notify_id=:notify_id")
	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	 @WhereSQL(sql="sign_type=:sign_type")
	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	 @WhereSQL(sql="sign=:sign")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	 @WhereSQL(sql="out_trade_no=:out_trade_no")
	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	 @WhereSQL(sql="subject=:subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	 @WhereSQL(sql="payment_type=:payment_type")
	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	 @WhereSQL(sql="trade_no=:trade_no")
	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	 @WhereSQL(sql="trade_status=:trade_status")
	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	 @WhereSQL(sql="seller_id=:seller_id")
	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	 @WhereSQL(sql="seller_email=:seller_email")
	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	 @WhereSQL(sql="buyer_id=:buyer_id")
	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	 @WhereSQL(sql="buyer_email=:buyer_email")
	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}
	 @WhereSQL(sql="total_fee=:total_fee")
	public Double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	 @WhereSQL(sql="quantity=:quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	 @WhereSQL(sql="body=:body")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	 @WhereSQL(sql="body=:body")
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	 @WhereSQL(sql="gmt_create=:gmt_create")
	public String getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	 @WhereSQL(sql="gmt_payment=:gmt_payment")
	public String getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	 @WhereSQL(sql="use_coupon=:use_coupon")
	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}

	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}
	 @WhereSQL(sql="use_coupon=:use_coupon")
	public String getUse_coupon() {
		return use_coupon;
	}

	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}
	 @WhereSQL(sql="discount=:discount")
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	 @WhereSQL(sql="refund_status=:refund_status")
	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	 @WhereSQL(sql="gmt_refund=:gmt_refund")
	public String getGmt_refund() {
		return gmt_refund;
	}

	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
	}
	
}
