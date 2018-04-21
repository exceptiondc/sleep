package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;

import java.util.List;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.entity.User
 */
@Table(name="t_user")
public class User  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_NAME = "姓名";
	public static final String ALIAS_ACCOUNT = "账号";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_MOBILE = "手机号码";
	public static final String ALIAS_EMAIL = "邮箱";
	public static final String ALIAS_WEIXINID = "微信Id";
	public static final String ALIAS_USERTYPE = "0后台管理员|/system/,1借款人";
	public static final String ALIAS_STATE = "是否有效,是/否";
	public static final String ALIAS_hotelId = "借款人ID";
	public static final String ALIAS_ISOPENFUIOU = "是否开通金账户";
    */
	//date formats
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 账号
	 */
	private java.lang.String account;
	/**
	 * 密码
	 */
	private java.lang.String password;
	/**
	 * 性别
	 */
	private java.lang.String sex;
	/**
	 * 手机号码
	 */
	private java.lang.String mobile;
	/**
	 * 邮箱
	 */
	private java.lang.String email;
	/**
	 * 微信Id
	 */
	private java.lang.String weixinId;
	/**
	 * 0后台管理员|/system/,1借款人
	 */
	private java.lang.Integer userType;
	/**
	 * 是否有效,是/否
	 */
	private java.lang.String state;
	/**
	 * 借款人ID
	 */
	private java.lang.Integer hotelId;
	
	//columns END 数据库字段结束


	private List<Org> userOrgs;


	private List<Role> userRoles;
	@Transient
	public List<Org> getUserOrgs() {
		return userOrgs;
	}

	public void setUserOrgs(List<Org> userOrgs) {
		this.userOrgs = userOrgs;
	}
	@Transient
	public List<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
	}
	//concstructor

	public User(){
	}

	public User(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:User_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:User_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setAccount(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.account = value;
	}
	
     @WhereSQL(sql="account=:User_account")
	public java.lang.String getAccount() {
		return this.account;
	}
	public void setPassword(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.password = value;
	}
	
     @WhereSQL(sql="password=:User_password")
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setSex(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.sex = value;
	}
	
     @WhereSQL(sql="sex=:User_sex")
	public java.lang.String getSex() {
		return this.sex;
	}
	public void setMobile(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.mobile = value;
	}
	
     @WhereSQL(sql="mobile=:User_mobile")
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setEmail(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.email = value;
	}
	
     @WhereSQL(sql="email=:User_email")
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setWeixinId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.weixinId = value;
	}
	
     @WhereSQL(sql="weixinId=:User_weixinId")
	public java.lang.String getWeixinId() {
		return this.weixinId;
	}
	public void setUserType(java.lang.Integer value) {
		this.userType = value;
	}
	
     @WhereSQL(sql="userType=:User_userType")
	public java.lang.Integer getUserType() {
		return this.userType;
	}
	public void setState(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.state = value;
	}
	
     @WhereSQL(sql="state=:User_state")
	public java.lang.String getState() {
		return this.state;
	}
	public void setHotelId(java.lang.Integer value) {
		this.hotelId = value;
	}
	
     @WhereSQL(sql="hotelId=:User_hotelId")
	public java.lang.Integer getHotelId() {
		return this.hotelId;
	}

	@Override
	public String toString() {
		return new StringBuffer()
			.append("编号[").append(getId()).append("],")
			.append("姓名[").append(getName()).append("],")
			.append("账号[").append(getAccount()).append("],")
			.append("密码[").append(getPassword()).append("],")
			.append("性别[").append(getSex()).append("],")
			.append("手机号码[").append(getMobile()).append("],")
			.append("邮箱[").append(getEmail()).append("],")
			.append("微信Id[").append(getWeixinId()).append("],")
			.append("0后台管理员|/system/,1酒店[").append(getUserType()).append("],")
			.append("是否有效,是/否[").append(getState()).append("],")
			.append("酒店ID[").append(getHotelId()).append("],")
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
		if(obj instanceof User == false) return false;
		if(this == obj) return true;
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
