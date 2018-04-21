package com.cz.yingpu.system.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:50
 * @see com.cz.yingpu.system.entity.Contact
 */
@Table(name="t_contact")
public class Contact  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String phone;
	private String descr;
	
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @WhereSQL(sql="createTime=:createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
	@WhereSQL(sql="phone=:phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@WhereSQL(sql="descr=:descr")
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}

	
