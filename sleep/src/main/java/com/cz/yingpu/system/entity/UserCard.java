package com.cz.yingpu.system.entity;

import java.util.List;

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
 * @version  2017-06-21 09:28:19
 * @see UserCard
 */
@Table(name="t_user_card")
public class UserCard  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "后台发放用户的抵扣券、加息券表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "userId";
	public static final String ALIAS_STATUS = "状态1 未使用 2已使用 3已过期";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_LIMITMONEY = "满多少元使用";
	public static final String ALIAS_MONEY = "money";
	public static final String ALIAS_RATE = "rate";
	public static final String ALIAS_TYPE = "1抵扣券 2加息券";
	public static final String ALIAS_deadLine = "投资期限限制，0或者空为不限制";
	public static final String ALIAS_STARTTIME = "开始时间";
	public static final String ALIAS_ENDTIME = "结束时间";
	public static final String ALIAS_CREATETIME = "发放时间";
    */
	//date formats
	//public static final String FORMAT_STARTTIME = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_ENDTIME = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_CREATETIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private Integer id;
	/**
	 * userId
	 */
	private Integer userId;
	/**
	 * 状态1 未使用 2已使用 3已过期
	 */
	private Integer status;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 满多少元使用
	 */
	private Double limitMoney;
	/**
	 * money
	 */
	private Double money;
	/**
	 * 1抵扣券 
	 */
	private Integer type;
	/**
	 * 品牌id 代表该优惠券只能用于品牌酒店的优惠,为null代表没有限制
	 */
	private Integer brandId;
	/**
	 *酒店id 代表该优惠券只能用于该酒店的优惠,为null代表没有限制
	 */
	private Integer hotelId;
	/**
	 * 开始时间
	 */
	private java.util.Date startTime;
	/**
	 * 结束时间
	 */
	private java.util.Date endTime;
	/**
	 * 发放时间
	 */
	private java.util.Date createTime;
	/**
	 * 卡券类型:1平台赠送2注册赠送3消费赠送
	 */
	private java.lang.Integer cardType;
	//columns END 数据库字段结束
	private String userName;
	private String phone;
	private String inputType;
	private String fileUrl;
	@Transient
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Transient
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	@Transient
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	//concstructor

	public UserCard(){
	}

	public UserCard(
		Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:UserCard_id")
	public Integer getId() {
		return this.id;
	}
	public void setUserId(Integer value) {
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:UserCard_userId")
	public Integer getUserId() {
		return this.userId;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
     @WhereSQL(sql="status=:UserCard_status")
	public Integer getStatus() {
		return this.status;
	}
	public void setName(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:UserCard_name")
	public String getName() {
		return this.name;
	}
	public void setLimitMoney(Double value) {
		this.limitMoney = value;
	}
	
     @WhereSQL(sql="limitMoney=:UserCard_limitMoney")
	public Double getLimitMoney() {
		return this.limitMoney;
	}
	public void setMoney(Double value) {
		this.money = value;
	}
	
     @WhereSQL(sql="money=:UserCard_money")
	public Double getMoney() {
		return this.money;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
     @WhereSQL(sql="type=:UserCard_type")
	public Integer getType() {
		return this.type;
	}
		/*
	public String getstartTimeString() {
		return DateUtils.convertDate2String(FORMAT_STARTTIME, getstartTime());
	}
	public void setstartTimeString(String value) throws ParseException{
		setstartTime(DateUtils.convertString2Date(FORMAT_STARTTIME,value));
	}*/
	
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
     @WhereSQL(sql="startTime=:UserCard_startTime")
	public java.util.Date getStartTime() {
		return this.startTime;
	}
		/*
	public String getendTimeString() {
		return DateUtils.convertDate2String(FORMAT_ENDTIME, getendTime());
	}
	public void setendTimeString(String value) throws ParseException{
		setendTime(DateUtils.convertString2Date(FORMAT_ENDTIME,value));
	}*/
	
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
     @WhereSQL(sql="endTime=:UserCard_endTime")
	public java.util.Date getEndTime() {
		return this.endTime;
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
     @WhereSQL(sql="createTime=:UserCard_createTime")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCardType(java.lang.Integer value) {
		this.cardType = value;
	}

	@WhereSQL(sql="cardType=:UserCard_cardType")
	public java.lang.Integer getCardType() {
		return this.cardType;
	}
	@Override
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("userId[").append(getUserId()).append("],")
			.append("状态1 未使用 2已使用 3已过期[").append(getStatus()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("满多少元使用[").append(getLimitMoney()).append("],")
			.append("money[").append(getMoney()).append("],")
			.append("1抵扣券 2加息券[").append(getType()).append("],")
			.append("开始时间[").append(getStartTime()).append("],")
			.append("结束时间[").append(getEndTime()).append("],")
			.append("发放时间[").append(getCreateTime()).append("],")
			.append("卡券类型:1平台赠送2注册赠送3投资赠送[").append(getCardType()).append("],")
			.toString();
	}
	 @WhereSQL(sql="brandId=:brandId")
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	 @WhereSQL(sql="hotelId=:hotelId")
	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserCard == false) return false;
		if(this == obj) return true;
		UserCard other = (UserCard)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	private List<Hotel> hotels;
	private List<HotelBrand> hotelBrands;
	@Transient
	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}
	@Transient
	public List<HotelBrand> getHotelBrands() {
		return hotelBrands;
	}
	public void setHotelBrands(List<HotelBrand> hotelBrands) {
		this.hotelBrands = hotelBrands;
	}
}

	
