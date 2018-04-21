package com.cz.yingpu.system.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.sun.jna.platform.win32.OaIdl.LIBFLAGS;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.entity.Hotel
 */
@Table(name="t_hotel")
public class Hotel  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 */
	private java.lang.String name;
	/**
	 */
	private java.lang.String phone;
	/**
	 */
	private java.lang.String descr;
	
	private java.lang.String address;

	/**
	 */
	private java.lang.String img;
	/**
	 */
	private java.util.Date createTime;
	/**
	 */
	private java.lang.Double score;
	
	
	private java.lang.Double avgPrice;
	
	/** 经度 */
	private String longitude;
	/** 维度 */
	private String latitude;
	
	private String level;
	
	private String property;
	
	private String serchKey;
	
	private	Integer brandId;
	
	private String scoreDescr;
	
	
	private String addressDescr;
	
	@WhereSQL(sql="addressDescr=:addressDescr")
	public String getAddressDescr() {
		return addressDescr;
	}

	public void setAddressDescr(String addressDescr) {
		this.addressDescr = addressDescr;
	}

	@WhereSQL(sql="scoreDescr=:scoreDescr")
	public String getScoreDescr() {
		return scoreDescr;
	}

	public void setScoreDescr(String scoreDescr) {
		this.scoreDescr = scoreDescr;
	}
	
	

	@WhereSQL(sql="brandId=:brandId")
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	//concstructor
	@WhereSQL(sql="level=:level")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@WhereSQL(sql="property=:property")
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@WhereSQL(sql="serchKey=:serchKey")
	public String getSerchKey() {
		return serchKey;
	}

	public void setSerchKey(String serchKey) {
		this.serchKey = serchKey;
	}

	public Hotel(){
	}

	public Hotel(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:hotel_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Hotel == false) return false;
		if(this == obj) return true;
		Hotel other = (Hotel)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@WhereSQL(sql="name=:hotel_name")
	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	@WhereSQL(sql="phone=:hotel_phone")
	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	@WhereSQL(sql="descr=:hotel_descr")
	public java.lang.String getDescr() {
		return descr;
	}

	public void setDescr(java.lang.String descr) {
		this.descr = descr;
	}

	@WhereSQL(sql="img=:hotel_img")
	public java.lang.String getImg() {
		return img;
	}

	public void setImg(java.lang.String img) {
		this.img = img;
	}

	@WhereSQL(sql="score=:hotel_score")
	public java.lang.Double getScore() {
		return score;
	}

	public void setScore(java.lang.Double score) {
		this.score = score;
	}

	@WhereSQL(sql="avgPrice=:hotel_avgPrice")
	public java.lang.Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(java.lang.Double avgPrice) {
		this.avgPrice = avgPrice;
	}
	
	@WhereSQL(sql="createTime=:hotel_createTime")
	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	

	@WhereSQL(sql="address=:hotel_address")
	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	private List<HotelBrand> hotelBrands;
	
	@Transient
	public List<HotelBrand> getHotelBrands() {
		return hotelBrands;
	}

	public void setHotelBrands(List<HotelBrand> hotelBrands) {
		this.hotelBrands = hotelBrands;
	}
	
	private String brandName;
	@Transient
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	@WhereSQL(sql="longitude=:hotel_longitude")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@WhereSQL(sql="latitude=:hotel_latitude")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	private String commentNum;
	
	@WhereSQL(sql="commentNum=:commentNum")
	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	private String range;
	@Transient
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	private String facilitys;
	@Transient
	public String getFacilitys() {
		return facilitys;
	}
	public void setFacilitys(String facilitys) {
		this.facilitys = facilitys;
	}
	private List<HotelFacility> hotelFacilities;
	@Transient
	public List<HotelFacility> getHotelFacilities() {
		return hotelFacilities;
	}
	public void setHotelFacilities(List<HotelFacility> hotelFacilities) {
		this.hotelFacilities = hotelFacilities;
	}
	private List<Facility> facilityLists;
	@Transient
	public List<Facility> getFacilityLists() {
		return facilityLists;
	}
	public void setFacilityLists(List<Facility> facilityLists) {
		this.facilityLists = facilityLists;
	}
	private List<Map<String, Object>> hotelHouses;
	@Transient
	public List<Map<String, Object>> getHotelHouses() {
		return hotelHouses;
	}

	public void setHotelHouses(List<Map<String, Object>> hotelHouses) {
		this.hotelHouses = hotelHouses;
	}
	private Integer collect;
	@Transient
	public Integer getCollect() {
		return collect;
	}

	public void setCollect(Integer collect) {
		this.collect = collect;
	}
	


	private String coverImg;

	@WhereSQL(sql="coverImg=:coverImg")
	public String getCoverImg() {
		return coverImg;
	}


	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
}

	
