package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.entity.Announce
 */
@Table(name="t_announce")
public class Announce  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "站内公告表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ICON = "图标";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_DESCR = "公告描述";
	public static final String ALIAS_CONTENT = "公告内容";
	public static final String ALIAS_CREATETIME = "时间";
    */
	//date formats
	//public static final String FORMAT_CREATETIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * 图标
	 */
	private java.lang.String icon;
	/**
	 * 标题
	 */
	private java.lang.String title;
	/**
	 * 公告描述
	 */
	private java.lang.String descr;
	/**
	 * 公告内容
	 */
	private java.lang.String content;
	/**
	 * 时间
	 */
	private java.util.Date createTime;
	
	
	private java.util.Date postTime;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@WhereSQL(sql="weight=:Announce_postTime")
	public java.util.Date getPostTime() {
		return postTime;
	}

	public void setPostTime(java.util.Date postTime) {
		this.postTime = postTime;
	}

	
	
	private java.lang.String keyword;
	

	@WhereSQL(sql="weight=:Announce_weight")
	public java.lang.String getKeyword() {
		return keyword;
	}

	public void setKeyword(java.lang.String keyword) {
		this.keyword = keyword;
	}
	
	/**
	 * 权重
	 */
	private Integer weight;
	
	@WhereSQL(sql="weight=:Announce_weight")
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	//columns END 数据库字段结束
	
	//concstructor

	public Announce(){
	}

	public Announce(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Announce_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setIcon(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.icon = value;
	}
	
     @WhereSQL(sql="icon=:Announce_icon")
	public java.lang.String getIcon() {
		return this.icon;
	}
	public void setTitle(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.title = value;
	}
	
     @WhereSQL(sql="title=:Announce_title")
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setDescr(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.descr = value;
	}
	
     @WhereSQL(sql="descr=:Announce_descr")
	public java.lang.String getDescr() {
		return this.descr;
	}
	public void setContent(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.content = value;
	}
	
     @WhereSQL(sql="content=:Announce_content")
	public java.lang.String getContent() {
		return this.content;
	}
		/*
	public String getcreateTimeString() {
		return DateUtils.convertDate2String(FORMAT_CREATETIME, getcreateTime());
	}
	public void setcreateTimeString(String value) throws ParseException{
		setcreateTime(DateUtils.convertString2Date(FORMAT_CREATETIME,value));
	}*/
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
     @WhereSQL(sql="createTime=:Announce_createTime")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	


	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("图标[").append(getIcon()).append("],")
			.append("标题[").append(getTitle()).append("],")
			.append("公告描述[").append(getDescr()).append("],")
			.append("公告内容[").append(getContent()).append("],")
			.append("时间[").append(getCreateTime()).append("],")
			.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Announce == false) return false;
		if(this == obj) return true;
		Announce other = (Announce)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
