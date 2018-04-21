package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;

@Table(name="t_hotel_facility")
public class HotelFacility extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer hotelId;
	private Integer facilityId;
	@Id
	@WhereSQL(sql="id=:id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@WhereSQL(sql="hotelId=:hotelId")
	public Integer getHotelId() {
		return hotelId;
	}
	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}
	@WhereSQL(sql="facilityId=:facilityId")
	public Integer getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(Integer facilityId) {
		this.facilityId = facilityId;
	}
	
	private String name;
	
	private String img;
	
	private String showSort;
	
	private String parentImg;
	
	private String parentShowSort;
	
	@Transient
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Transient
	public String getShowSort() {
		return showSort;
	}
	public void setShowSort(String showSort) {
		this.showSort = showSort;
	}
	@Transient
	public String getParentImg() {
		return parentImg;
	}
	public void setParentImg(String parentImg) {
		this.parentImg = parentImg;
	}
	@Transient
	public String getParentShowSort() {
		return parentShowSort;
	}
	public void setParentShowSort(String parentShowSort) {
		this.parentShowSort = parentShowSort;
	}

	private Integer parentId;
	
	private String parentName;
	
	
	
	@Transient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Transient
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
