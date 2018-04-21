package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:44
 * @see AppUser
 */
@Table(name="t_app_user")
public class AppUser  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "手机用户表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PHONE = "手机号";
	public static final String ALIAS_HEADER = "头像";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_NAME = "昵称";
	public static final String ALIAS_PASSWORD = "password";
	public static final String ALIAS_INVITECODE = "邀请码";
	public static final String ALIAS_INVITECODEURL = "邀请码网页";
	public static final String ALIAS_WXCODE = "微信号";
	public static final String ALIAS_QQCODE = "QQ号";
	public static final String ALIAS_REALNAME = "真实姓名";
	public static final String ALIAS_IDCARD = "身份证号";
	public static final String ALIAS_EMAIL = "email";
	public static final String ALIAS_ISIDCARD = "是否实名认证";
	public static final String ALIAS_TRADING = "交易密码";
	public static final String ALIAS_BALANCE = "账户余额";
	public static final String ALIAS_QUOTA = "天天存吧免费额度";
	public static final String ALIAS_YEBBALANCE = "天天存吧金额";
	public static final String ALIAS_DATELINE = "注册时间";
	public static final String ALIAS_CARDTIME = "实名认证时间";
	public static final String ALIAS_LASTLOGIN = "最后登录时间";
	public static final String ALIAS_TOTALINVESTAMOUNT = "总累计投资金额";
	public static final String ALIAS_NOWINVESTAMOUNT = "现有投资金额";
	public static final String ALIAS_ISPUSH = "是否开启推送";
	public static final String ALIAS_PARENTID = "邀请人";
	public static final String ALIAS_ISWEEKSIGN = "是否一周免签到";
	public static final String ALIAS_WEEKSIGNENDTIME = "免费签到到期时间";
	public static final String ALIAS_CTBALANCE = "账面总余额";
	public static final String ALIAS_CABALANCE = "可用余额";
	public static final String ALIAS_CFBALANCE = "冻结余额";
	public static final String ALIAS_CUBALANCE = "未转接余额";
	public static final String ALIAS_OSTYPE = "Ios/android";
	public static final String ALIAS_CITYNAME = "所属城市";
	public static final String ALIAS_CITYID = "城市id";
	public static final String ALIAS_BANKID = "开户行行别ID";
	public static final String ALIAS_BANKNUM = "银行卡号";
	public static final String ALIAS_INVITENUM = "邀请人数";
    */
	//date formats
	//public static final String FORMAT_DATELINE = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_CARDTIME = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_LASTLOGIN = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_WEEKSIGNENDTIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String header;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * password
	 */
	private String password;
	/**
	 * 邀请码
	 */
	private String inviteCode;
	/**
	 * 邀请码网页
	 */
	private String inviteCodeUrl;
	/**
	 * 微信号
	 */
	private String wxCode;
	/**
	 * QQ号
	 */
	private String qqCode;
	/**
	 * 真实姓名
	 */
	private String realName;
	
	
	private String qrCode;
	
	@WhereSQL(sql="qrCode=:qrCode")
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * email
	 */
	private String email;
	/**
	 * 是否实名认证(0否1是)
	 */
	private String isIdCard;
	
	private String isRecharge;
	
	private String isFirstOrder;
	
	
	@WhereSQL(sql="isRecharge=:isRecharge")
	public String getIsRecharge() {
		return isRecharge;
	}

	public void setIsRecharge(String isRecharge) {
		this.isRecharge = isRecharge;
	}
	@WhereSQL(sql="isFirstOrder=:isFirstOrder")
	public String getIsFirstOrder() {
		return isFirstOrder;
	}

	public void setIsFirstOrder(String isFirstOrder) {
		this.isFirstOrder = isFirstOrder;
	}

	/**
	 * 账户余额
	 */
	private Double balance;
	@WhereSQL(sql="balance=:AppUser_balance")
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * 注册时间
	 */
	private java.util.Date dateline;
	/**
	 * 实名认证时间
	 */
	private java.util.Date cardTime;
	/**
	 * 最后登录时间
	 */
	private java.util.Date lastLogin;
	/**
	 * 总累计投资金额
	 */
	private Double totalInvestAmount;
	/**
	 * 用户总消费
	 */
	private Double totalConsume;
	/**
	 * 用户押金
	 */
	private Double cashPledge;
	@WhereSQL(sql="totalConsume=:totalConsume")
	public Double getTotalConsume() {
		return totalConsume;
	}

	public void setTotalConsume(Double totalConsume) {
		this.totalConsume = totalConsume;
	}
	@WhereSQL(sql="cashPledge=:cashPledge")
	public Double getCashPledge() {
		return cashPledge;
	}

	public void setCashPledge(Double cashPledge) {
		this.cashPledge = cashPledge;
	}

	/**
	 * 是否开启推送(0否1是)
	 */
	private String isPush;
	/**
	 * 邀请人
	 */
	private Integer parentId;
	/**
	 * 是否一周免签到
	 */
	private String isWeekSign;
	/**
	 * 免费签到到期时间
	 */
	private java.util.Date weekSignEndTime;

	/**
	 * Ios/android
	 */
	private String osType;
	/**
	 * 所属城市
	 */
	private String cityName;
	/**
	 * 城市id
	 */
	private Integer cityId;
	/**
	 * 开户行行别ID
	 */
	private Integer bankId;
	/**
	 * 银行卡号
	 */
	private String bankNum;
	
	
	@WhereSQL(sql="errorNum=:errorNum")
	public Integer getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}

	private Integer errorNum;
	
	private String tradPassword;
	
	@WhereSQL(sql="tradPassword=:tradPassword")
	public String getTradPassword() {
		return tradPassword;
	}

	public void setTradPassword(String tradPassword) {
		this.tradPassword = tradPassword;
	}

	
	/**
	 * 是否提交了更换银行卡申请
	 */
	
	/*public String getIsreplacebank() {
		return isreplacebank;
	}

	public void setIsreplacebank(String isreplacebank) {
		this.isreplacebank = isreplacebank;
	}*/
	//剩余抽奖次数
	
		
		/* private Double prizeamount;
			@WhereSQL(sql="prizeamount=:prizeamount")
		 public Double getPrizeamount() {
			return prizeamount;
		}

		public void setPrizeamount(Double prizeamount) {
			this.prizeamount = prizeamount;
		}*/

		/*@WhereSQL(sql="prizenum=:prizenum")
		 public Integer getPrizenum() {
			return prizenum;
		}

		public void setPrizenum(Integer prizenum) {
			this.prizenum = prizenum;
		}
		
		private Double totalcommission;
		private Double commission;
		@WhereSQL(sql="commission=:commission")
		public Double getCommission() {
			return commission;
		}

		public void setCommission(Double commission) {
			this.commission = commission;
		}
		
		@WhereSQL(sql="totalcommission=:totalcommission")
		public Double getTotalcommission() {
			return totalcommission;
		}

		public void setTotalcommission(Double totalcommission) {
			this.totalcommission = totalcommission;
		}*/

	/**
	 * 邀请人数
	 */
	private Integer inviteNum;
	/**
	 * 是否第一次登陆
	 */
	private String isFirst;
	/**
	 * 是否是合伙人
	 */
	private String isPartner;
	/**
	 * 合伙人开始时间
	 */
	private java.util.Date partnerStartTime;
	/**
	 * 合伙人取消时间
	 */
	
	
	private java.util.Date partnerCancelTime;
	//columns END 数据库字段结束
	private String oldpassword;
	private String code;
	private String type;
	private String token;
	private Double money;
	private Double income;//已收益
	private Double paid;//待收益
	private Double totalAmount;//总资产
	private String bankName;
	private String bankLog;
	private Double totalBalance;
	private Double totalYebBalance;
	private Double totalFreezeYebBalance;
	private String level;
	private Integer orderId;
	@Transient
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Transient
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Transient
	public Double getTotalFreezeYebBalance() {
		return totalFreezeYebBalance;
	}

	public void setTotalFreezeYebBalance(Double totalFreezeYebBalance) {
		this.totalFreezeYebBalance = totalFreezeYebBalance;
	}

	@Transient
	public Double getTotalYebBalance() {
		return totalYebBalance;
	}

	public void setTotalYebBalance(Double totalYebBalance) {
		this.totalYebBalance = totalYebBalance;
	}

	@Transient
	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}

	@Transient
	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	@Transient
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Transient
	public String getBankLog() {
		return bankLog;
	}

	public void setBankLog(String bankLog) {
		this.bankLog = bankLog;
	}

	@Transient
	public Double getPaid() {
		return paid;
	}

	public void setPaid(Double paid) {
		this.paid = paid;
	}
	@Transient
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Transient
	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	@Transient
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@WhereSQL(sql="token=:AppUser_token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Transient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Transient
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	//concstructor

	public AppUser(){
	}

	public AppUser(
		Integer id
	){
		this.id = id;
	}


	public void setId(Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:AppUser_id")
	public Integer getId() {
		return this.id;
	}
	public void setPhone(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.phone = value;
	}
	
     @WhereSQL(sql="phone=:AppUser_phone")
	public String getPhone() {
		return this.phone;
	}
	public void setHeader(String value) {
	    if(StringUtils.isNotBlank(value)){
		 value=value.trim();
		}
		this.header = value;
	}
	
     @WhereSQL(sql="header=:AppUser_header")
	public String getHeader() {
		return this.header;
	}
	public void setSex(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.sex = value;
	}
	
     @WhereSQL(sql="sex=:AppUser_sex")
	public String getSex() {
		return this.sex;
	}
	public void setName(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:AppUser_name")
	public String getName() {
		return this.name;
	}
	public void setPassword(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.password = value;
	}
	
     @WhereSQL(sql="password=:AppUser_password")
	public String getPassword() {
		return this.password;
	}
	public void setInviteCode(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.inviteCode = value;
	}
	
     @WhereSQL(sql="inviteCode=:AppUser_inviteCode")
	public String getInviteCode() {
		return this.inviteCode;
	}
	public void setInviteCodeUrl(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.inviteCodeUrl = value;
	}
	
     @WhereSQL(sql="inviteCodeUrl=:AppUser_inviteCodeUrl")
	public String getInviteCodeUrl() {
		return this.inviteCodeUrl;
	}
	public void setWxCode(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.wxCode = value;
	}
	
     @WhereSQL(sql="wxCode=:AppUser_wxCode")
	public String getWxCode() {
		return this.wxCode;
	}
	public void setQqCode(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.qqCode = value;
	}
	
     @WhereSQL(sql="qqCode=:AppUser_qqCode")
	public String getQqCode() {
		return this.qqCode;
	}
	public void setRealName(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.realName = value;
	}
	
     @WhereSQL(sql="realName=:AppUser_realName")
	public String getRealName() {
		return this.realName;
	}
	public void setIdCard(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.idCard = value;
	}
	
     @WhereSQL(sql="idCard=:AppUser_idCard")
	public String getIdCard() {
		return this.idCard;
	}
	public void setEmail(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.email = value;
	}
	
     @WhereSQL(sql="email=:AppUser_email")
	public String getEmail() {
		return this.email;
	}
	public void setIsIdCard(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.isIdCard = value;
	}
	
     @WhereSQL(sql="isIdCard=:AppUser_isIdCard")
	public String getIsIdCard() {
		return this.isIdCard;
	}
	
	
   
		/*
	public String getdatelineString() {
		return DateUtils.convertDate2String(FORMAT_DATELINE, getdateline());
	}
	public void setdatelineString(String value) throws ParseException{
		setdateline(DateUtils.convertString2Date(FORMAT_DATELINE,value));
	}*/
	
	public void setDateline(java.util.Date value) {
		this.dateline = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
     @WhereSQL(sql="dateline=:AppUser_dateline")
	public java.util.Date getDateline() {
		return this.dateline;
	}
		/*
	public String getcardTimeString() {
		return DateUtils.convertDate2String(FORMAT_CARDTIME, getcardTime());
	}
	public void setcardTimeString(String value) throws ParseException{
		setcardTime(DateUtils.convertString2Date(FORMAT_CARDTIME,value));
	}*/
	
	public void setCardTime(java.util.Date value) {
		this.cardTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
     @WhereSQL(sql="cardTime=:AppUser_cardTime")
	public java.util.Date getCardTime() {
		return this.cardTime;
	}
		/*
	public String getlastLoginString() {
		return DateUtils.convertDate2String(FORMAT_LASTLOGIN, getlastLogin());
	}
	public void setlastLoginString(String value) throws ParseException{
		setlastLogin(DateUtils.convertString2Date(FORMAT_LASTLOGIN,value));
	}*/
	
	public void setLastLogin(java.util.Date value) {
		this.lastLogin = value;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
     @WhereSQL(sql="lastLogin=:AppUser_lastLogin")
	public java.util.Date getLastLogin() {
		return this.lastLogin;
	}
	public void setTotalInvestAmount(Double value) {
		this.totalInvestAmount = value;
	}
	
     @WhereSQL(sql="totalInvestAmount=:AppUser_totalInvestAmount")
	public Double getTotalInvestAmount() {
		return this.totalInvestAmount;
	}

	public void setIsPush(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.isPush = value;
	}
	
     @WhereSQL(sql="isPush=:AppUser_isPush")
	public String getIsPush() {
		return this.isPush;
	}
	public void setParentId(Integer value) {
		this.parentId = value;
	}
	
     @WhereSQL(sql="parentId=:AppUser_parentId")
	public Integer getParentId() {
		return this.parentId;
	}
	public void setIsWeekSign(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.isWeekSign = value;
	}
	
     @WhereSQL(sql="isWeekSign=:AppUser_isWeekSign")
	public String getIsWeekSign() {
		return this.isWeekSign;
	}
		/*
	public String getweekSignEndTimeString() {
		return DateUtils.convertDate2String(FORMAT_WEEKSIGNENDTIME, getweekSignEndTime());
	}
	public void setweekSignEndTimeString(String value) throws ParseException{
		setweekSignEndTime(DateUtils.convertString2Date(FORMAT_WEEKSIGNENDTIME,value));
	}*/
	
	public void setWeekSignEndTime(java.util.Date value) {
		this.weekSignEndTime = value;
	}
	
     @WhereSQL(sql="weekSignEndTime=:AppUser_weekSignEndTime")
	public java.util.Date getWeekSignEndTime() {
		return this.weekSignEndTime;
	}

	public void setOsType(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.osType = value;
	}
	
     @WhereSQL(sql="osType=:AppUser_osType")
	public String getOsType() {
		return this.osType;
	}
	public void setCityName(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.cityName = value;
	}
	
     @WhereSQL(sql="cityName=:AppUser_cityName")
	public String getCityName() {
		return this.cityName;
	}
	public void setCityId(Integer value) {
		this.cityId = value;
	}
	
     @WhereSQL(sql="cityId=:AppUser_cityId")
	public Integer getCityId() {
		return this.cityId;
	}
	public void setBankId(Integer value) {
		this.bankId = value;
	}
	
     @WhereSQL(sql="bankId=:AppUser_bankId")
	public Integer getBankId() {
		return this.bankId;
	}
	public void setBankNum(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.bankNum = value;
	}
	
     @WhereSQL(sql="bankNum=:AppUser_bankNum")
	public String getBankNum() {
		return this.bankNum;
	}
	public void setInviteNum(Integer value) {
		this.inviteNum = value;
	}
	
     @WhereSQL(sql="inviteNum=:AppUser_inviteNum")
	public Integer getInviteNum() {
		return this.inviteNum;
	}
	public void setIsFirst(String value) {
		if(StringUtils.isNotBlank(value)){
			value=value.trim();
		}
		this.isFirst = value;
	}

	@WhereSQL(sql="isFirst=:AppUser_isFirst")
	public String getIsFirst() {
		return this.isFirst;
	}
	
	public void setIsPartner(String value) {
		if(StringUtils.isNotBlank(value)){
			value=value.trim();
		}
		this.isPartner = value;
	}

	@WhereSQL(sql="isPartner=:AppUser_isPartner")
	public String getIsPartner() {
		return this.isPartner;
	}
		/*
	public String getpartnerStartTimeString() {
		return DateUtils.convertDate2String(FORMAT_PARTNERSTARTTIME, getpartnerStartTime());
	}
	public void setpartnerStartTimeString(String value) throws ParseException{
		setpartnerStartTime(DateUtils.convertString2Date(FORMAT_PARTNERSTARTTIME,value));
	}*/

	public void setPartnerStartTime(java.util.Date value) {
		this.partnerStartTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	@WhereSQL(sql="partnerStartTime=:AppUser_partnerStartTime")
	public java.util.Date getPartnerStartTime() {
		return this.partnerStartTime;
	}
		/*
	public String getpartnerCancelTimeString() {
		return DateUtils.convertDate2String(FORMAT_PARTNERCANCELTIME, getpartnerCancelTime());
	}
	public void setpartnerCancelTimeString(String value) throws ParseException{
		setpartnerCancelTime(DateUtils.convertString2Date(FORMAT_PARTNERCANCELTIME,value));
	}*/

	public void setPartnerCancelTime(java.util.Date value) {
		this.partnerCancelTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	@WhereSQL(sql="partnerCancelTime=:AppUser_partnerCancelTime")
	public java.util.Date getPartnerCancelTime() {
		return this.partnerCancelTime;
	}
	@Override
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("手机号[").append(getPhone()).append("],")
			.append("头像[").append(getHeader()).append("],")
			.append("性别[").append(getSex()).append("],")
			.append("昵称[").append(getName()).append("],")
			.append("password[").append(getPassword()).append("],")
			.append("邀请码[").append(getInviteCode()).append("],")
			.append("邀请码网页[").append(getInviteCodeUrl()).append("],")
			.append("微信号[").append(getWxCode()).append("],")
			.append("QQ号[").append(getQqCode()).append("],")
			.append("真实姓名[").append(getRealName()).append("],")
			.append("身份证号[").append(getIdCard()).append("],")
			.append("email[").append(getEmail()).append("],")
			.append("是否实名认证[").append(getIsIdCard()).append("],")
/*			.append("交易密码[").append(getTrading()).append("],")
			.append("账户余额[").append(getBalance()).append("],")
			.append("天天存吧免费额度[").append(getQuota()).append("],")
			.append("天天存吧金额[").append(getYebBalance()).append("],")*/
			.append("注册时间[").append(getDateline()).append("],")
			.append("实名认证时间[").append(getCardTime()).append("],")
			.append("最后登录时间[").append(getLastLogin()).append("],")
			.append("总累计投资金额[").append(getTotalInvestAmount()).append("],")
			.append("是否开启推送[").append(getIsPush()).append("],")
			.append("邀请人[").append(getParentId()).append("],")
			.append("是否一周免签到[").append(getIsWeekSign()).append("],")
			.append("免费签到到期时间[").append(getWeekSignEndTime()).append("],")
		/*	.append("账面总余额[").append(getCtBalance()).append("],")
			.append("可用余额[").append(getCaBalance()).append("],")
			.append("冻结余额[").append(getCfBalance()).append("],")
			.append("未转接余额[").append(getCuBalance()).append("],")*/
			.append("Ios/android[").append(getOsType()).append("],")
			.append("所属城市[").append(getCityName()).append("],")
			.append("城市id[").append(getCityId()).append("],")
			.append("开户行行别ID[").append(getBankId()).append("],")
			.append("银行卡号[").append(getBankNum()).append("],")
			.append("邀请人数[").append(getInviteNum()).append("],")
				.append("是否第一次登陆[").append(getIsFirst()).append("],")
				.append("是否是合伙人[").append(getIsPartner()).append("],")
				.append("合伙人开始时间[").append(getPartnerStartTime()).append("],")
				.append("合伙人取消时间[").append(getPartnerCancelTime()).append("],")
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
		if(obj instanceof AppUser == false) return false;
		if(this == obj) return true;
		AppUser other = (AppUser)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	private String zfbAccount;

	@WhereSQL(sql="zfbAccount=:zfbAccount")
	public String getZfbAccount() {
		return zfbAccount;
	}

	public void setZfbAccount(String zfbAccount) {
		this.zfbAccount = zfbAccount;
	}
	
	private String wxOpenId;
	@WhereSQL(sql="wxOpenId=:wxOpenId")
	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	private String hotelType;
	@Transient
	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}
}

	
