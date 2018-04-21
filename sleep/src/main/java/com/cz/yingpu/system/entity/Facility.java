package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;

@Table(name="t_facility")
public class Facility extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Integer level;
	private Integer parentId;
	private Integer showSort;
	private String img;
	@WhereSQL(sql="name=:name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@WhereSQL(sql="level=:level")
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	@WhereSQL(sql="parentId=:parentId")
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@WhereSQL(sql="showSort=:showSort")
	public Integer getShowSort() {
		return showSort;
	}
	public void setShowSort(Integer showSort) {
		this.showSort = showSort;
	}
	@WhereSQL(sql="img=:img")
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Id
	@WhereSQL(sql="id=:id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
