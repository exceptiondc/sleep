package com.cz.yingpu.system.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.entity.HotelUser
 */
@Table(name="t_hotel_user")
public class HotelUser  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * id
	 */
	private java.lang.Integer id;
	private Integer userid;
	private Integer orderid;
	private Integer status;
	private Integer checkuserid;
	private Integer hotelHouseId;
	private Integer hotelid;
	@Id
	@WhereSQL(sql="id=:id")
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	@WhereSQL(sql="userid=:userid")
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	@WhereSQL(sql="orderid=:orderid")
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	@WhereSQL(sql="status=:status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@WhereSQL(sql="checkuserid=:checkuserid")
	public Integer getCheckuserid() {
		return checkuserid;
	}
	public void setCheckuserid(Integer checkuserid) {
		this.checkuserid = checkuserid;
	}
	@WhereSQL(sql="hotelHouseId=:hotelHouseId")
	public Integer getHotelHouseId() {
		return hotelHouseId;
	}
	public void setHotelHouseId(Integer hotelHouseId) {
		this.hotelHouseId = hotelHouseId;
	}
	@WhereSQL(sql="hotelid=:hotelid")
	public Integer getHotelid() {
		return hotelid;
	}
	public void setHotelid(Integer hotelid) {
		this.hotelid = hotelid;
	}
	@WhereSQL(sql="lockid=:lockid")
	public Integer getLockid() {
		return lockid;
	}
	public void setLockid(Integer lockid) {
		this.lockid = lockid;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@WhereSQL(sql="createTime=:createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@WhereSQL(sql="checkTime=:checkTime")
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@WhereSQL(sql="checkOutTime=:checkOutTime")
	public Date getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	private Integer lockid;
	private Date createTime;
	private Date checkTime;
	private Date checkOutTime;
	
	private Date reservationStartTime;
	private Date reservationEndTime;
	
	
	
	@WhereSQL(sql="reservationStartTime=:reservationStartTime")
	public Date getReservationStartTime() {
		return reservationStartTime;
	}
	public void setReservationStartTime(Date reservationStartTime) {
		this.reservationStartTime = reservationStartTime;
	}
	@WhereSQL(sql="reservationEndTime=:reservationEndTime")
	public Date getReservationEndTime() {
		return reservationEndTime;
	}
	public void setReservationEndTime(Date reservationEndTime) {
		this.reservationEndTime = reservationEndTime;
	}
	/**数据库字段end
	 */
	private String phone;
	private String hotelName;
	private Date startTime;
	private Date endTime;
	private List<Status> datas;
	@Transient
	public List<Status> getDatas() {
		return datas;
	}
	public void setDatas(List<Status> datas) {
		this.datas = datas;
	}
	@Transient
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Transient
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	@Transient
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Transient
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}

	
