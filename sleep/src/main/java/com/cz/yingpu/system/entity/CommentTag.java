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
 * @see com.cz.yingpu.system.entity.CommentTag
 */
@Table(name="t_comment_tag")
public class CommentTag  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 */
	private java.lang.Integer commentId;
	/**
	 */
	private java.lang.String name;
	
	private java.util.Date createDate;
	/**
	 */
	private java.lang.Integer hotelRoomId;
	
	
	
	
	//concstructor

	public CommentTag(){
	}

	public CommentTag(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:c_t_id")
	public java.lang.Integer getId() {
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
		if(obj instanceof CommentTag == false) return false;
		if(this == obj) return true;
		CommentTag other = (CommentTag)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@WhereSQL(sql="commentId=:c_t_commentId")
	public java.lang.Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(java.lang.Integer commentId) {
		this.commentId = commentId;
	}

	@WhereSQL(sql="name=:c_t_name")
	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	@WhereSQL(sql="createDate=:c_t_createDate")
	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	
	@WhereSQL(sql="hotelRoomId=:c_t_hotelRoomId")
	public java.lang.Integer getHotelRoomId() {
		return hotelRoomId;
	}

	public void setHotelRoomId(java.lang.Integer hotelRoomId) {
		this.hotelRoomId = hotelRoomId;
	}

}

	
