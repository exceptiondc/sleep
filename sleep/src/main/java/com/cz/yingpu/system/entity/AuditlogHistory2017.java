package com.cz.yingpu.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cz.yingpu.frame.annotation.WhereSQL;
import com.cz.yingpu.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:44
 * @see com.cz.yingpu.system.entity.AuditlogHistory2017
 */
@Table(name="t_auditlog_history_2017")
public class AuditlogHistory2017  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "操作记录";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_OPERATIONTYPE = "操作类型";
	public static final String ALIAS_OPERATORNAME = "操作人姓名";
	public static final String ALIAS_PREVALUE = "旧值";
	public static final String ALIAS_CURVALUE = "新值";
	public static final String ALIAS_OPERATIONTIME = "操作时间";
	public static final String ALIAS_OPERATIONCLASS = "操作类";
	public static final String ALIAS_OPERATIONCLASSID = "记录ID";
    */
	//date formats
	//public static final String FORMAT_OPERATIONTIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 操作类型
	 */
	private java.lang.String operationType;
	/**
	 * 操作人姓名
	 */
	private java.lang.String operatorName;
	/**
	 * 旧值
	 */
	private java.lang.String preValue;
	/**
	 * 新值
	 */
	private java.lang.String curValue;
	/**
	 * 操作时间
	 */
	private java.util.Date operationTime;
	/**
	 * 操作类
	 */
	private java.lang.String operationClass;
	/**
	 * 记录ID
	 */
	private java.lang.String operationClassID;
	//columns END 数据库字段结束
	
	//concstructor

	public AuditlogHistory2017(){
	}

	public AuditlogHistory2017(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:AuditlogHistory2017_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setOperationType(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.operationType = value;
	}
	
     @WhereSQL(sql="operationType=:AuditlogHistory2017_operationType")
	public java.lang.String getOperationType() {
		return this.operationType;
	}
	public void setOperatorName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.operatorName = value;
	}
	
     @WhereSQL(sql="operatorName=:AuditlogHistory2017_operatorName")
	public java.lang.String getOperatorName() {
		return this.operatorName;
	}
	public void setPreValue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.preValue = value;
	}
	
     @WhereSQL(sql="preValue=:AuditlogHistory2017_preValue")
	public java.lang.String getPreValue() {
		return this.preValue;
	}
	public void setCurValue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.curValue = value;
	}
	
     @WhereSQL(sql="curValue=:AuditlogHistory2017_curValue")
	public java.lang.String getCurValue() {
		return this.curValue;
	}
		/*
	public String getoperationTimeString() {
		return DateUtils.convertDate2String(FORMAT_OPERATIONTIME, getoperationTime());
	}
	public void setoperationTimeString(String value) throws ParseException{
		setoperationTime(DateUtils.convertString2Date(FORMAT_OPERATIONTIME,value));
	}*/
	
	public void setOperationTime(java.util.Date value) {
		this.operationTime = value;
	}
	
     @WhereSQL(sql="operationTime=:AuditlogHistory2017_operationTime")
	public java.util.Date getOperationTime() {
		return this.operationTime;
	}
	public void setOperationClass(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.operationClass = value;
	}
	
     @WhereSQL(sql="operationClass=:AuditlogHistory2017_operationClass")
	public java.lang.String getOperationClass() {
		return this.operationClass;
	}
	public void setOperationClassID(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.operationClassID = value;
	}
	
     @WhereSQL(sql="operationClassID=:AuditlogHistory2017_operationClassID")
	public java.lang.String getOperationClassID() {
		return this.operationClassID;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("ID[").append(getId()).append("],")
			.append("操作类型[").append(getOperationType()).append("],")
			.append("操作人姓名[").append(getOperatorName()).append("],")
			.append("旧值[").append(getPreValue()).append("],")
			.append("新值[").append(getCurValue()).append("],")
			.append("操作时间[").append(getOperationTime()).append("],")
			.append("操作类[").append(getOperationClass()).append("],")
			.append("记录ID[").append(getOperationClassID()).append("],")
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
		if(obj instanceof AuditlogHistory2017 == false) return false;
		if(this == obj) return true;
		AuditlogHistory2017 other = (AuditlogHistory2017)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
