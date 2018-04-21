package com.cz.yingpu.system.entity;

import javax.persistence.Table;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;

@Table(name="t_status")
public class Status  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	
	private Integer statusCode;
	private String name;
	private String descr;
	private String group;
	@WhereSQL(sql="statusCode=:statusCode")
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	@WhereSQL(sql="name=:name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@WhereSQL(sql="descr=:descr")
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	@WhereSQL(sql="group=:group")
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
}

	
