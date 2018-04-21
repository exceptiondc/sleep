package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.entity.ZhimaTransaction
 */
@Table(name="t_zhima_transaction")
public class ZhimaTransaction  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "站内公告表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ICON = "图标";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_DESCR = "公告描述";
	public static final String ALIAS_CONTENT = "公告内容";
	public static final String ALIAS_CREATETIME = "时间";
    */
	//date formats
	//public static final String FORMAT_CREATETIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * 图标
	 */
	private java.lang.String transactionId;
	
	/**
	 * 标题
	 */
	private Integer userId;
	/**
	 * 时间
	 */
	private java.util.Date createTime;
	
	private java.util.Date expiryTime;
	
	private java.lang.String apiName;

	
	//columns END 数据库字段结束
	
	//concstructor

	public ZhimaTransaction(){
	}

	public ZhimaTransaction(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:t_zhima_transaction_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
     @WhereSQL(sql="createTime=:t_zhima_transaction_createTime")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ZhimaTransaction == false) return false;
		if(this == obj) return true;
		ZhimaTransaction other = (ZhimaTransaction)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	

	@WhereSQL(sql="userId=:t_zhima_transaction_userId")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	
	@WhereSQL(sql="transactionId=:t_zhima_transaction_transactionId")
	public java.lang.String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(java.lang.String transactionId) {
		this.transactionId = transactionId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@WhereSQL(sql="expiryTime=:t_zhima_transaction_expiryTime")
	public java.util.Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(java.util.Date expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	@WhereSQL(sql="apiName=:t_zhima_transaction_apiName")
	public java.lang.String getApiName() {
		return apiName;
	}

	public void setApiName(java.lang.String apiName) {
		this.apiName = apiName;
	}
	
}

	
