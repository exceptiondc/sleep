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
 * @see SearchLocationCategory
 */
@Table(name="t_search_location_category")
public class SearchLocationCategory extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	private Integer id;
	/**
	 */
	private String name;

	private Date createTime;


	//concstructor

	public SearchLocationCategory(){
	}

	public SearchLocationCategory(
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
		if(obj instanceof SearchLocationCategory == false) return false;
		if(this == obj) return true;
		SearchLocationCategory other = (SearchLocationCategory)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@WhereSQL(sql="createTime=:t_search_location_createTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@WhereSQL(sql="name=:t_search_location_name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}

	
