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
 * @version  2017-02-24 15:17:27
 * @see com.cz.mts.system.entity.Message
 */
@Table(name="t_message")
public class Message  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "消息表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PUSHTYPE = "推送类型";
	public static final String ALIAS_USERID = "用户id";
	public static final String ALIAS_CREATETIME = "创建时间";
	public static final String ALIAS_CONTENT = "推送内容";
	public static final String ALIAS_ITEMID = "目标id";
	public static final String ALIAS_URL = "url";
	public static final String ALIAS_ISREAD = "是否已读：0未读 1已读";
	public static final String ALIAS_NAME = "推送名称";
	public static final String ALIAS_TYPE = "消息类型：1系统消息 2平台消息";
    */
	//date formats
	//public static final String FORMAT_CREATETIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * 推送类型
	 */
	private java.lang.Integer pushType;
	/**
	 * 用户id
	 */
	private java.lang.Integer userId;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 推送内容
	 */
	private java.lang.String content;
	/**
	 * 目标id
	 */
	private java.lang.Integer itemId;
	/**
	 * url
	 */
	private java.lang.String url;
	/**
	 * 是否已读：0未读 1已读
	 */
	private java.lang.String isRead;
	/**
	 * 推送名称
	 */
	private java.lang.String name;
	/**
	 * 消息类型：1系统消息 2平台消息
	 */
	private java.lang.Integer type;
	//columns END 数据库字段结束
	
	//concstructor

	public Message(){
	}

	public Message(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Message_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setPushType(java.lang.Integer value) {
		this.pushType = value;
	}
	
     @WhereSQL(sql="pushType=:Message_pushType")
	public java.lang.Integer getPushType() {
		return this.pushType;
	}
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:Message_userId")
	public java.lang.Integer getUserId() {
		return this.userId;
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
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
     @WhereSQL(sql="createTime=:Message_createTime")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setContent(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.content = value;
	}
	
     @WhereSQL(sql="content=:Message_content")
	public java.lang.String getContent() {
		return this.content;
	}
	public void setItemId(java.lang.Integer value) {
		this.itemId = value;
	}
	
     @WhereSQL(sql="itemId=:Message_itemId")
	public java.lang.Integer getItemId() {
		return this.itemId;
	}
	public void setUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.url = value;
	}
	
     @WhereSQL(sql="url=:Message_url")
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setIsRead(java.lang.String value) {
		this.isRead = value;
	}
	
     @WhereSQL(sql="isRead=:Message_isRead")
	public java.lang.String getIsRead() {
		return this.isRead;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:Message_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
     @WhereSQL(sql="type=:Message_type")
	public java.lang.Integer getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("推送类型[").append(getPushType()).append("],")
			.append("用户id[").append(getUserId()).append("],")
			.append("创建时间[").append(getCreateTime()).append("],")
			.append("推送内容[").append(getContent()).append("],")
			.append("目标id[").append(getItemId()).append("],")
			.append("url[").append(getUrl()).append("],")
			.append("是否已读：0未读 1已读[").append(getIsRead()).append("],")
			.append("推送名称[").append(getName()).append("],")
			.append("消息类型：1系统消息 2平台消息[").append(getType()).append("],")
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
		if(obj instanceof Message == false) return false;
		if(this == obj) return true;
		Message other = (Message)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	public Message(Integer id, Integer pushType, Integer userId,
			Date createTime, String content, Integer itemId, String url,
				   String isRead, String name, Integer type) {
		super();
		this.id = id;
		this.pushType = pushType;
		this.userId = userId;
		this.createTime = createTime;
		this.content = content;
		this.itemId = itemId;
		this.url = url;
		this.isRead = isRead;
		this.name = name;
		this.type = type;
	}
	
	
	
}

	
