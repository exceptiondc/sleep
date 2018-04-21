package com.cz.yingpu.system.entity;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see SearchLocation
 */
@Table(name="t_search_location")
public class SearchLocation extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	private Integer id;
	/**
	 */
	private String name;

	private String longitude;

	private String latitude;

	private String province;

	private String city;

	private String district;

	private Date createTime;

	private Integer categoryId;


	//concstructor

	public SearchLocation(){
	}

	public SearchLocation(
		Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(Integer value) {
		this.id = value;
	}

	@Id
     @WhereSQL(sql="id=:t_search_location_id")
	public Integer getId() {
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
		if(obj instanceof SearchLocation == false) return false;
		if(this == obj) return true;
		SearchLocation other = (SearchLocation)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@WhereSQL(sql="name=:t_search_location_name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@WhereSQL(sql="longitude=:t_search_location_longitude")
	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@WhereSQL(sql="latitude=:t_search_location_latitude")
	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@WhereSQL(sql="province=:t_search_location_province")
	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}

	@WhereSQL(sql="city=:t_search_location_city")
	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}

	@WhereSQL(sql="district=:t_search_location_district")
	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}

	@WhereSQL(sql="createTime=:t_search_location_createTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@WhereSQL(sql="categoryId=:t_search_location_categoryId")
	public Integer getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
}

	
