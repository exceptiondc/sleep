package com.cz.yingpu.system.entity;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-07-12 10:28:03
 * @see Card
 */
@Table(name="t_card")
public class Card  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Card";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_LIMITMONEY = "满多少元使用";
	public static final String ALIAS_MONEY = "money";
	public static final String ALIAS_RATE = "rate";
	public static final String ALIAS_TYPE = "1抵扣券 2加息券";
	public static final String ALIAS_DEADLINE = "投资期限限制，0或者空为不限制";
	public static final String ALIAS_STARTTIME = "开始时间";
	public static final String ALIAS_ENDTIME = "结束时间";
	public static final String ALIAS_CARDTYPE = "类型：1注册即送2投资即送";
    */
	//date formats
	//public static final String FORMAT_STARTTIME = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_ENDTIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private Integer id;
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

	/**
	 * 开始时间
	 */
	private java.util.Date startTime;
	/**
	 * 结束时间
	 */
	private java.util.Date endTime;
	/**
	 * 类型：1注册即送2投资即送
	 */
	private Integer cardType;
	
	private Double probability;
	//columns END 数据库字段结束
	
	//concstructor
	  @WhereSQL(sql="probability=:probability")
	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	public Card(){
	}

	public Card(
		Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Card_id")
	public Integer getId() {
		return this.id;
	}
	public void setName(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:Card_name")
	public String getName() {
		return this.name;
	}
	public void setLimitMoney(Double value) {
		this.limitMoney = value;
	}
	
     @WhereSQL(sql="limitMoney=:Card_limitMoney")
	public Double getLimitMoney() {
		return this.limitMoney;
	}
	public void setMoney(Double value) {
		this.money = value;
	}
	
     @WhereSQL(sql="money=:Card_money")
	public Double getMoney() {
		return this.money;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
     @WhereSQL(sql="type=:Card_type")
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
	
     @WhereSQL(sql="startTime=:Card_startTime")
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
	
     @WhereSQL(sql="endTime=:Card_endTime")
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setCardType(Integer value) {
		this.cardType = value;
	}
	
     @WhereSQL(sql="cardType=:Card_cardType")
	public Integer getCardType() {
		return this.cardType;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("满多少元使用[").append(getLimitMoney()).append("],")
			.append("money[").append(getMoney()).append("],")
			.append("1抵扣券 2加息券[").append(getType()).append("],")
			.append("开始时间[").append(getStartTime()).append("],")
			.append("结束时间[").append(getEndTime()).append("],")
			.append("类型：1注册即送2投资即送[").append(getCardType()).append("],")
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
		if(obj instanceof Card == false) return false;
		if(this == obj) return true;
		Card other = (Card)obj;
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

	
