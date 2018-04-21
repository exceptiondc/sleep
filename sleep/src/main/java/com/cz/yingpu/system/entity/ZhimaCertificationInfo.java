package com.cz.yingpu.system.entity;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.entity.ZhimaCertificationInfo
 */
@Table(name="t_zhima_certification_info")
public class ZhimaCertificationInfo  extends BaseEntity {
	
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
	private java.lang.String bizNo;
	
	/**
	 * 标题
	 */
	private Integer userId;
	/**
	 * 公告描述
	 */
	private java.lang.String openId;
	
	private java.lang.String idCard;
	
	private java.lang.String realName;
	
	/**
	 * 时间
	 */
	private java.util.Date createTime;
	
	private java.util.Date bizNoExpiryTime;

	private Integer certificationState;

	private Integer authorizationState;
	
	private Integer score;

	private String idCardImgFace;

	private String idCardImgBack;

	
	//columns END 数据库字段结束
	
	//concstructor

	public ZhimaCertificationInfo(){
	}

	public ZhimaCertificationInfo(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:zhima_cert_info_cache_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
     @WhereSQL(sql="createTime=:zhima_cert_info_cache_createTime")
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
		if(obj instanceof ZhimaCertificationInfo == false) return false;
		if(this == obj) return true;
		ZhimaCertificationInfo other = (ZhimaCertificationInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@WhereSQL(sql="bizNo=:zhima_cert_info_bizNo")
	public java.lang.String getBizNo() {
		return bizNo;
	}

	public void setBizNo(java.lang.String bizNo) {
		this.bizNo = bizNo;
	}

	@WhereSQL(sql="userId=:zhima_cert_info_userId")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	 @WhereSQL(sql="openId=:zhima_cert_info_openId")
	public java.lang.String getOpenId() {
		return openId;
	}

	public void setOpenId(java.lang.String openId) {
		this.openId = openId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @WhereSQL(sql="bizNoExpiryTime=:zhima_cert_info_bizNoExpiryTime")
	public java.util.Date getBizNoExpiryTime() {
		return bizNoExpiryTime;
	}

	public void setBizNoExpiryTime(java.util.Date bizNoExpiryTime) {
		this.bizNoExpiryTime = bizNoExpiryTime;
	}

	@WhereSQL(sql="idCard=:zhima_cert_info_idCard")
	public java.lang.String getIdCard() {
		return idCard;
	}

	public void setIdCard(java.lang.String idCard) {
		this.idCard = idCard;
	}

	@WhereSQL(sql="realName=:zhima_cert_info_realName")
	public java.lang.String getRealName() {
		return realName;
	}

	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}

	@WhereSQL(sql="certificationState=:zhima_cert_info_certificationState")
	public Integer getCertificationState() {
		return certificationState;
	}

	public void setCertificationState(Integer certificationState) {
		this.certificationState = certificationState;
	}

	@WhereSQL(sql="authorizationState=:zhima_cert_info_authorizationState")
	public Integer getAuthorizationState() {
		return authorizationState;
	}

	public void setAuthorizationState(Integer authorizationState) {
		this.authorizationState = authorizationState;
	}

	@WhereSQL(sql="score=:zhima_cert_info_score")
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@WhereSQL(sql="idCardImgFace=:zhima_cert_info_idCardImgFace")
	public String getIdCardImgFace() {
		return idCardImgFace;
	}


	public void setIdCardImgFace(String idCardImgFace) {
		this.idCardImgFace = idCardImgFace;
	}

	@WhereSQL(sql="idCardImgBack=:zhima_cert_info_idCardImgBack")
	public String getIdCardImgBack() {
		return idCardImgBack;
	}


	public void setIdCardImgBack(String idCardImgBack) {
		this.idCardImgBack = idCardImgBack;
	}
}

	
