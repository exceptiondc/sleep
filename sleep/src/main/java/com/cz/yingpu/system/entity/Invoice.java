package com.cz.yingpu.system.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.entity.Invoice
 */
@Table(name="t_invoice")
public class Invoice  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer userId;
	private Integer orderId;
	private Integer riseType;
	
	private String rise;
	private String dutyparagraph;
	private String content;
	private Double amount;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@WhereSQL(sql="createTime=:createTime")
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Id
	@WhereSQL(sql="id=:id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@WhereSQL(sql="userId=:userId")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@WhereSQL(sql="orderId=:orderId")
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	@WhereSQL(sql="riseType=:riseType")
	public Integer getRiseType() {
		return riseType;
	}
	public void setRiseType(Integer riseType) {
		this.riseType = riseType;
	}
	@WhereSQL(sql="rise=:rise")
	public String getRise() {
		return rise;
	}
	public void setRise(String rise) {
		this.rise = rise;
	}
	@WhereSQL(sql="dutyparagraph=:dutyparagraph")
	public String getDutyparagraph() {
		return dutyparagraph;
	}
	public void setDutyparagraph(String dutyparagraph) {
		this.dutyparagraph = dutyparagraph;
	}
	@WhereSQL(sql="content=:content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@WhereSQL(sql="amount=:amount")
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@WhereSQL(sql="email=:email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@WhereSQL(sql="descr=:descr")
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	private String email;
	private String descr;
	
	
}

	
