package com.cz.yingpu.system.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;

@Table(name="t_footprint")
public class Footprint extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date createTime;
	private Integer userId;
	private Integer hotelId;
	private Integer isDelete;
	@Id
	@WhereSQL(sql="id=:id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@WhereSQL(sql="createTime=:createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@WhereSQL(sql="userId=:userId")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@WhereSQL(sql="hotelId=:hotelId")
	public Integer getHotelId() {
		return hotelId;
	}
	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}
	@WhereSQL(sql="isDelete=:isDelete")
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	private String img;
	
	private String name;
	@Transient
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Transient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
