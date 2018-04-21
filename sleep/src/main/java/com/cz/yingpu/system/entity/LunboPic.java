package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
 * @version  2017-03-30 18:30:09
 * @see LunboPic
 */
@Table(name="t_lunbo_pic")
public class LunboPic  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "轮播图表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_POSITION = "轮播图位置";
	public static final String ALIAS_ITEM = "跳转内容";
	public static final String ALIAS_SKIPTYPE = "跳转类型";
	public static final String ALIAS_CREATETIME = "注册时间";
	public static final String ALIAS_CITYID = "城市";
	public static final String ALIAS_IMAGE = "图片";
    */
	//date formats
	//public static final String FORMAT_CREATETIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 轮播图位置
	 */
	private Integer position;
	/**
	 * 跳转内容
	 */
	private String item;
	/**
	 * 跳转类型
	 */
	private Integer skipType;
	/**
	 * 注册时间
	 */
	private java.util.Date createTime;
	/**
	 * 城市
	 */
	private Integer cityId;
	/**
	 * 图片
	 */
	private String image;
	//columns END 数据库字段结束


	private String projectName ;
	private String url ;
	private String announceTitle ;
	
	private String title;
	@WhereSQL(sql="title=:title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Transient
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Transient
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Transient
	public String getAnnounceTitle() {
		return announceTitle;
	}

	public void setAnnounceTitle(String announceTitle) {
		this.announceTitle = announceTitle;
	}
	//concstructor

	public LunboPic(){
	}

	public LunboPic(
		Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:LunboPic_id")
	public Integer getId() {
		return this.id;
	}
	public void setPosition(Integer value) {
		this.position = value;
	}
	
     @WhereSQL(sql="position=:LunboPic_position")
	public Integer getPosition() {
		return this.position;
	}
	public void setItem(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.item = value;
	}
	
	
	
     @WhereSQL(sql="item=:LunboPic_item")
	public String getItem() {
		return this.item;
	}
	public void setSkipType(Integer value) {
		this.skipType = value;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
	@WhereSQL(sql="skipType=:LunboPic_skipType")
	public Integer getSkipType() {
		return this.skipType;
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
	
     @WhereSQL(sql="createTime=:LunboPic_createTime")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setCityId(Integer value) {
		this.cityId = value;
	}
	
     @WhereSQL(sql="cityId=:LunboPic_cityId")
	public Integer getCityId() {
		return this.cityId;
	}
	public void setImage(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.image = value;
	}
	
     @WhereSQL(sql="image=:LunboPic_image")
	public String getImage() {
		return this.image;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("轮播图位置[").append(getPosition()).append("],")
			.append("跳转内容[").append(getItem()).append("],")
			.append("跳转类型[").append(getSkipType()).append("],")
			.append("注册时间[").append(getCreateTime()).append("],")
			.append("城市[").append(getCityId()).append("],")
			.append("图片[").append(getImage()).append("],")
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
		if(obj instanceof LunboPic == false) return false;
		if(this == obj) return true;
		LunboPic other = (LunboPic)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
