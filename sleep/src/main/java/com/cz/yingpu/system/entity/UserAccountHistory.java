package com.cz.yingpu.system.entity;

import java.util.Date;

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
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.entity.UserAccountHistory
 */
@Table(name="t_user_account_history")
public class UserAccountHistory  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户交易流水表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "用户";
	public static final String ALIAS_TYPE = "类型";
	public static final String ALIAS_MONEY = "金额";
	public static final String ALIAS_AFTERMONEY = "剩余金额";
	public static final String ALIAS_CREATETIME = "时间";
	public static final String ALIAS_POUNDAGE = "手续费";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_REMARKERS = "备注";
	public static final String ALIAS_PROJECTID = "项目Id";
	public static final String ALIAS_ORDERID = "总记录id";
	public static final String ALIAS_USERPROJECTID = "用户投资项目id";
	public static final String ALIAS_TRADENO = "交易号";
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
	 * 类型
	 */
	private java.lang.Integer type;
	/**
	 * 金额
	 */
	private java.lang.Double money;
	/**
	 * 剩余金额
	 */
	private java.lang.Double afterMoney;
	/**
	 * 时间
	 */
	private java.util.Date createtime;
	/**
	 * 手续费
	 */
	private java.lang.Double poundage;
	/**
	 * 状态
	 */
	private java.lang.Integer status;
	/**
	 * 备注
	 */
	private java.lang.String remarkers;
	/**
	 * 总记录id
	 */
	private java.lang.Integer orderId;
	/**
	 * 分销人id
	 */
	private java.lang.Integer fenxiaoUserId;
	/**
	 * 交易号
	 */
	private java.lang.String tradeNo;
	//columns END 数据库字段结束



	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:UserAccountHistory_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:UserAccountHistory_userId")
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
     @WhereSQL(sql="type=:UserAccountHistory_type")
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setMoney(java.lang.Double value) {
		this.money = value;
	}
	
     @WhereSQL(sql="money=:UserAccountHistory_money")
	public java.lang.Double getMoney() {
		return this.money;
	}
	public void setAfterMoney(java.lang.Double value) {
		this.afterMoney = value;
	}

     @WhereSQL(sql="afterMoney=:UserAccountHistory_afterMoney")
	public java.lang.Double getAfterMoney() {
		return this.afterMoney;
	}
		/*
	public String getcreatetimeString() {
		return DateUtils.convertDate2String(FORMAT_CREATETIME, getcreatetime());
	}
	public void setcreatetimeString(String value) throws ParseException{
		setcreatetime(DateUtils.convertString2Date(FORMAT_CREATETIME,value));
	}*/
	
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
     @WhereSQL(sql="createtime=:UserAccountHistory_createtime")
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	public void setPoundage(java.lang.Double value) {
		this.poundage = value;
	}
	
     @WhereSQL(sql="poundage=:UserAccountHistory_poundage")
	public java.lang.Double getPoundage() {
		return this.poundage;
	}
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
     @WhereSQL(sql="status=:UserAccountHistory_status")
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setRemarkers(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.remarkers = value;
	}
	
     @WhereSQL(sql="remarkers=:UserAccountHistory_remarkers")
	public java.lang.String getRemarkers() {
		return this.remarkers;
	}

	public void setOrderId(java.lang.Integer value) {
		this.orderId = value;
	}
	
     @WhereSQL(sql="orderId=:UserAccountHistory_orderId")
	public java.lang.Integer getOrderId() {
		return this.orderId;
	}
	@WhereSQL(sql="fenxiaoUserId=:UserAccountHistory_fenxiaoUserId")
	public Integer getFenxiaoUserId() {
		return fenxiaoUserId;
	}

	public void setFenxiaoUserId(Integer fenxiaoUserId) {
		this.fenxiaoUserId = fenxiaoUserId;
	}

	
	public void setTradeNo(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.tradeNo = value;
	}
	
     @WhereSQL(sql="tradeNo=:UserAccountHistory_tradeNo")
	public java.lang.String getTradeNo() {
		return this.tradeNo;
	}

	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("用户[").append(getUserId()).append("],")
			.append("类型[").append(getType()).append("],")
			.append("金额[").append(getMoney()).append("],")
			.append("剩余金额[").append(getAfterMoney()).append("],")
			.append("时间[").append(getCreatetime()).append("],")
			.append("手续费[").append(getPoundage()).append("],")
			.append("状态[").append(getStatus()).append("],")
			.append("备注[").append(getRemarkers()).append("],")
			.append("总记录id[").append(getOrderId()).append("],")
			.append("交易号[").append(getTradeNo()).append("],")
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
		if(obj instanceof UserAccountHistory == false) return false;
		if(this == obj) return true;
		UserAccountHistory other = (UserAccountHistory)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	private String userName;
	
	private String userPhone;
	
	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Transient
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	@Transient
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Transient
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private Date startTime;
	
	private Date endTime;
	
	
}

	
