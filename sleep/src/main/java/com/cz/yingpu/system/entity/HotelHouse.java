package com.cz.yingpu.system.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
 * @version  2017-03-22 11:49:23
 * @see HotelHouse
 */
@Table(name="t_hotelHouse")
public class HotelHouse  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "验证码表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_CONTENT = "验证码";
	public static final String ALIAS_CREATETIME = "createTime";
	public static final String ALIAS_TYPE = "类型：1注册 2修改绑定原手机号 3绑定新手机号 4找回密码  5绑定手机号";
	public static final String ALIAS_PHONE = "手机号";
    */
	//date formats
	//public static final String FORMAT_CREATETIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	
	
	
	/**
	 * id
	 */
	private Integer id;
	
	private String houseName;
	
	 @WhereSQL(sql="houseName=:houseName")
	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public HotelHouse(){
	}

	public HotelHouse(
		Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:id")
	public Integer getId() {
		return this.id;
	}
	@WhereSQL(sql="hotelid=:hotelid")
	public Integer getHotelid() {
		return hotelid;
	}

	public void setHotelid(Integer hotelid) {
		this.hotelid = hotelid;
	}
	@WhereSQL(sql="houseNumber=:houseNumber")
	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	@WhereSQL(sql="lockNumber=:lockNumber")
	public String getLockNumber() {
		return lockNumber;
	}

	public void setLockNumber(String lockNumber) {
		this.lockNumber = lockNumber;
	}
	@WhereSQL(sql="status=:status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@WhereSQL(sql="orientation=:orientation")
	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	@WhereSQL(sql="hotelid=:hotelid")
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	@WhereSQL(sql="reserveTime=:reserveTime")
	public String getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	@WhereSQL(sql="type=:type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@WhereSQL(sql="price=:price")
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	private Integer hotelid;
	private String houseNumber;
	private String lockNumber;
	private Integer status;
	private String orientation;
	private String descr;
	private String reserveTime;
	private Integer type;
	
	private Double price;
	
	private Double hourPrice;
	
	private Double area;
	@WhereSQL(sql="area=:area")
	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}
	private String bedType;
	
	private String fitType;
	
	private String isBreakFast;
	
	private String img;
	
	private String imgs;
	

	@WhereSQL(sql="bedType=:bedType")
	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	@WhereSQL(sql="fitType=:fitType")
	public String getFitType() {
		return fitType;
	}

	public void setFitType(String fitType) {
		this.fitType = fitType;
	}
	@WhereSQL(sql="isBreakFast=:isBreakFast")
	public String getIsBreakFast() {
		return isBreakFast;
	}

	public void setIsBreakFast(String isBreakFast) {
		this.isBreakFast = isBreakFast;
	}
	@WhereSQL(sql="img=:img")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	@WhereSQL(sql="imgs=:imgs")
	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	@WhereSQL(sql="hourPrice=:hourPrice")
	public Double getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(Double hourPrice) {
		this.hourPrice = hourPrice;
	}
	private List<Map<String, Object>> datas;
	@Transient
	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

	private List<Map<String, Object>> statusdata;
	@Transient
	public List<Map<String, Object>> getStatusdata() {
		return statusdata;
	}

	public void setStatusdata(List<Map<String, Object>> statusdata) {
		this.statusdata = statusdata;
	}

	private List<Map<String, Object>> types;
	@Transient
	public List<Map<String, Object>> getTypes() {
		return types;
	}

	public void setTypes(List<Map<String, Object>> types) {
		this.types = types;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof HotelHouse == false) return false;
		if(this == obj) return true;
		HotelHouse other = (HotelHouse)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	
	private Date startTime;
	
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
	private Date endTime;
}

	
