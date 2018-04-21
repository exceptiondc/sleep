package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:50
 * @see com.cz.yingpu.system.entity.UserSignHis
 */
@Table(name="t_user_sign_his")
public class UserSignHis  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户签到记录表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "用户";
	public static final String ALIAS_MONEY = "金额";
	public static final String ALIAS_ISSEND = "是否发奖励";
	public static final String ALIAS_CREATETIME = "签到时间";
	public static final String ALIAS_TYPE = "类型";
	public static final String ALIAS_OSTYPE = "ios/android";
    */
	//date formats
	//public static final String FORMAT_CREATETIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * 用户
	 */
	private java.lang.Integer userId;
	/**
	 * 金额
	 */
	private java.lang.Double money;
	/**
	 * 是否发奖励
	 */
	private java.lang.String isSend;
	/**
	 * 签到时间
	 */
	private java.util.Date createTime;
	/**
	 * 类型
	 */
	private java.lang.Integer type;
	/**
	 * ios/android
	 */
	private java.lang.String osType;
	//columns END 数据库字段结束
	private String userName ;
	private String userPhone ;
	@Transient
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	//concstructor

	public UserSignHis(){
	}

	public UserSignHis(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:UserSignHis_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:UserSignHis_userId")
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setMoney(java.lang.Double value) {
		this.money = value;
	}
	
     @WhereSQL(sql="money=:UserSignHis_money")
	public java.lang.Double getMoney() {
		return this.money;
	}
	public void setIsSend(java.lang.String value) {
		this.isSend = value;
	}
	
     @WhereSQL(sql="isSend=:UserSignHis_isSend")
	public java.lang.String getIsSend() {
		return this.isSend;
	}
		/*
	public String getcreateTimeString() {
		return DateUtils.convertDate2String(FORMAT_CREATETIME, getcreateTime());
	}
	public void setcreateTimeString(String value) throws ParseException{
		setcreateTime(DateUtils.convertString2Date(FORMAT_CREATETIME,value));
	}*/
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
     @WhereSQL(sql="createTime like :%UserSignHis_createTime%")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
     @WhereSQL(sql="type=:UserSignHis_type")
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setOsType(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.osType = value;
	}
	
     @WhereSQL(sql="osType=:UserSignHis_osType")
	public java.lang.String getOsType() {
		return this.osType;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("用户[").append(getUserId()).append("],")
			.append("金额[").append(getMoney()).append("],")
			.append("是否发奖励[").append(getIsSend()).append("],")
			.append("签到时间[").append(getCreateTime()).append("],")
			.append("类型[").append(getType()).append("],")
			.append("ios/android[").append(getOsType()).append("],")
			.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserSignHis == false) return false;
		if(this == obj) return true;
		UserSignHis other = (UserSignHis)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
