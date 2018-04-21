package com.cz.yingpu.system.entity;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.entity.Comment
 */
@Table(name="t_comment")
public class Comment  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 */
	private java.lang.Integer userId;
	/**
	 */
	private java.lang.String content;
	/**
	 */
	private java.lang.Integer type;
	
	private java.lang.Integer parentid;

	private Integer hotelId;

	/**
	 */
	private java.lang.Integer orderId;
	/**
	 */
	private java.util.Date createTime;
	/**
	 */
	private java.lang.Double impressionScore;

	private java.lang.Double environmentScore;

	private java.lang.Double satisficingScore;

	private String images;

	private Integer thumbNum;

	private Integer viewTimes;

	private String tags;
	
	
	//concstructor

	public Comment(){
	}

	public Comment(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:comment_id")
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
		if(obj instanceof Comment == false) return false;
		if(this == obj) return true;
		Comment other = (Comment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@WhereSQL(sql="userid=:comment_userid")
	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userid) {
		this.userId = userid;
	}

	@WhereSQL(sql="content=:comment_content")
	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	@WhereSQL(sql="type=:comment_type")
	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	@WhereSQL(sql="parentid=:comment_parentid")
	public java.lang.Integer getParentid() {
		return parentid;
	}

	public void setParentid(java.lang.Integer parentid) {
		this.parentid = parentid;
	}

	@WhereSQL(sql="orderId=:comment_order")
	public java.lang.Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.Integer order) {
		this.orderId = order;
	}

	@WhereSQL(sql="createTime=:comment_createTime")
	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	@WhereSQL(sql="impressionScore=:comment_impressionScore")
	public java.lang.Double getImpressionScore() {
		return impressionScore;
	}

	public void setImpressionScore(java.lang.Double impressionScore) {
		this.impressionScore = impressionScore;
	}

	@WhereSQL(sql="images=:comment_images")
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	@WhereSQL(sql="hotelId=:comment_hotelId")
	public Integer getHotelId() {
		return hotelId;
	}


	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	@WhereSQL(sql="thumbNum=:comment_thumbNum")
	public Integer getThumbNum() {
		return thumbNum;
	}


	public void setThumbNum(Integer thumbNum) {
		this.thumbNum = thumbNum;
	}

	@WhereSQL(sql="viewTimes=:comment_viewTimes")
	public Integer getViewTimes() {
		return viewTimes;
	}


	public void setViewTimes(Integer viewTimes) {
		this.viewTimes = viewTimes;
	}

	@WhereSQL(sql="tags=:comment_tags")
	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}

	@WhereSQL(sql="environmentScore=:comment_environmentScore")
	public Double getEnvironmentScore() {
		return environmentScore;
	}


	public void setEnvironmentScore(Double environmentScore) {
		this.environmentScore = environmentScore;
	}

	@WhereSQL(sql="satisficingScore=:comment_satisficingScore")
	public Double getSatisficingScore() {
		return satisficingScore;
	}


	public void setSatisficingScore(Double satisficingScore) {
		this.satisficingScore = satisficingScore;
	}
}

	
