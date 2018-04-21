package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cz.yingpu.frame.annotation.WhereSQL;

@Table(name="t_hotelBrand")
public class HotelBrand {
	
	private Integer id;
	private String name;
	private Double grade;
	private Integer sort;
	 @Id
	 @WhereSQL(sql="id=:id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	 @WhereSQL(sql="name=:name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@WhereSQL(sql="grade=:grade")
	public Double getGrade() {
		return grade;
	}
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	@WhereSQL(sql="sort=:sort")
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@WhereSQL(sql="logo=:logo")
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@WhereSQL(sql="descr=:descr")
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	private String logo;
	private String descr;
}
