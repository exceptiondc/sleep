package com.cz.yingpu.system.entity;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;

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
 * @see CommentThumbup
 */
@Table(name="t_comment_thumbup")
public class CommentThumbup extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	private Integer id;
	/**
	 */
	private java.util.Date createTime;

	private Integer userId;

	private Integer commentId;



	//concstructor

	public CommentThumbup(){
	}

	public CommentThumbup(
		Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(Integer value) {
		this.id = value;
	}

	@Id
     @WhereSQL(sql="id=:t_comment_thumbup_id")
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
		if(obj instanceof CommentThumbup == false) return false;
		if(this == obj) return true;
		CommentThumbup other = (CommentThumbup)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


	@WhereSQL(sql="createTime=:comment_createTime")
	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@WhereSQL(sql="userId=:comment_userId")
	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@WhereSQL(sql="commentId=:comment_commentId")
	public Integer getCommentId() {
		return commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
}

	
