package com.cz.yingpu.system.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-07-17 16:55:01
 * @see SMSTemplate
 */
@Table(name="t_sms_template")
public class SMSTemplate  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "活动表";
	public static final String ALIAS_Id = "id";
	public static final String ALIAS_NAME = "活动名称";
	public static final String ALIAS_IMAGE = "活动图片";
	public static final String ALIAS_URL = "地址";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private Integer id;
	private String template_id;
	private String content;
	private Date update_date;
	private Date create_date;
	private Integer type;
	
	
	@WhereSQL(sql="template_id=:template_id")
	public String getTemplate_id() {
		return template_id;
	}



	public void setTemplate_id(String templateId) {
		this.template_id = templateId;
	}


	@WhereSQL(sql="content=:content")
	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}


	@WhereSQL(sql="update_date=:update_date")
	public Date getUpdate_date() {
		return update_date;
	}



	public void setUpdate_date(Date updateDate) {
		this.update_date = updateDate;
	}


	 @WhereSQL(sql="create_date=:create_date")
	public Date getCreate_date() {
		return create_date;
	}



	public void setCreate_date(Date createDate) {
		this.create_date = createDate;
	}

	/**
	 * 活动名称
	 */
	private String name;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	//columns END 数据库字段结束
	
	//concstructor

	public SMSTemplate(){
	}

	public SMSTemplate(
		Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Activity_id")
	public Integer getId() {
		return this.id;
	}
	public void setName(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:Activity_name")
	public String getName() {
		return this.name;
	}
     
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SMSTemplate == false) return false;
		if(this == obj) return true;
		SMSTemplate other = (SMSTemplate)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@WhereSQL(sql="type=:Activity_type")
	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
}

	
