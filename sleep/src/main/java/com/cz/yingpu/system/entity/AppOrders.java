package com.cz.yingpu.system.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.entity.AppOrders
 */
@Table(name="t_app_orders")
public class AppOrders  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * id
	 */
	private Integer id;
	
	private Integer userid;
	
	private String orderNumber;
	
	private String paySerialNumber;
	
	@WhereSQL(sql="paySerialNumber=:paySerialNumber")
	public String getPaySerialNumber() {
		return paySerialNumber;
	}

	public void setPaySerialNumber(String paySerialNumber) {
		this.paySerialNumber = paySerialNumber;
	}
	private Integer status;
	
	private Double amount;
	
	private Double paymentAmount;
	
	private Double cardPrice;
	
	private Integer cardId;
	
	private Integer hotelHouseId;
	@WhereSQL(sql="hotelHouseId=:hotelHouseId")
	public Integer getHotelHouseId() {
		return hotelHouseId;
	}

	public void setHotelHouseId(Integer hotelHouseId) {
		this.hotelHouseId = hotelHouseId;
	}
	private Date createTime;
	
	private Date paymentTime;
	
	private Date cancelTime;
	
	private Integer orderType;
	
	
	private String payType;
	@WhereSQL(sql="payType=:payType")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Id
	@WhereSQL(sql="id=:id")
	public Integer getId() {
		return id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@WhereSQL(sql="userid=:userid")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	@WhereSQL(sql="orderNumber=:orderNumber")
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	@WhereSQL(sql="status=:status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@WhereSQL(sql="amount=:amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@WhereSQL(sql="paymentAmount=:paymentAmount")
	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	@WhereSQL(sql="cardPrice=:cardPrice")
	public Double getCardPrice() {
		return cardPrice;
	}

	public void setCardPrice(Double cardPrice) {
		this.cardPrice = cardPrice;
	}
	@WhereSQL(sql="cardId=:cardId")
	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	@WhereSQL(sql="createTime=:createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	@WhereSQL(sql="paymentTime=:paymentTime")
	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	@WhereSQL(sql="orderType=:orderType")
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	//数据库字段结束
	private Date startTime;
	private Date endTime;
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
	@Transient
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	private String phone;

	private List<Status> datas;
	@Transient
	public List<Status> getDatas() {
		return datas;
	}

	public void setDatas(List<Status> datas) {
		this.datas = datas;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	@WhereSQL(sql="cancelTime=:cancelTime")
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	private Date reservationStartTime;
	private Date reservationEndTime;
	
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8") 
	@WhereSQL(sql="reservationStartTime=:reservationStartTime")
	public Date getReservationStartTime() {
		return reservationStartTime;
	}
	public void setReservationStartTime(Date reservationStartTime) {
		this.reservationStartTime = reservationStartTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8") 
	@WhereSQL(sql="reservationEndTime=:reservationEndTime")
	public Date getReservationEndTime() {
		return reservationEndTime;
	}
	public void setReservationEndTime(Date reservationEndTime) {
		this.reservationEndTime = reservationEndTime;
	}
	
	private Double price;
	
	@Transient
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	private Double hourPrice;
	@Transient
	public Double getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(Double hourPrice) {
		this.hourPrice = hourPrice;
	}
	
	private HotelUser hotelUser;
	@Transient
	public HotelUser getHotelUser() {
		return hotelUser;
	}

	public void setHotelUser(HotelUser hotelUser) {
		this.hotelUser = hotelUser;
	}
	private Hotel hotel;
	@Transient
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	private HotelHouse hotelHouse;
	@Transient
	public HotelHouse getHotelHouse() {
		return hotelHouse;
	}

	public void setHotelHouse(HotelHouse hotelHouse) {
		this.hotelHouse = hotelHouse;
	}
	
	private Date checkTime;

	@Transient
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	private Integer isInvoice;

	public Integer getIsInvoice() {
		return isInvoice;
	}
	@WhereSQL(sql="isInvoice=:isInvoice")
	public void setIsInvoice(Integer isInvoice) {
		this.isInvoice = isInvoice;
	}
	
}
