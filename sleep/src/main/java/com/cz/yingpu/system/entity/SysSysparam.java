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
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.entity.SysSysparam
 */
@Table(name="t_sys_sysparam")
public class SysSysparam  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "系统常用变量表";
	public static final String ALIAS_CODE = "系统表的KEY";
	public static final String ALIAS_VALUE = "系统表的值";
	public static final String ALIAS_DESCR = "描述";
	public static final String ALIAS_CHOOSEFLAG = "是否使用(Y是N否)";
	public static final String ALIAS_ID = "id";
    */
	//date formats
	
	//columns START
	/**
	 * 系统表的KEY
	 */
	private java.lang.String code;
	/**
	 * 系统表的值
	 */
	private java.lang.String value;
	/**
	 * 描述
	 */
	private java.lang.String descr;
	/**
	 * 是否使用(Y是N否)
	 */
	private java.lang.String chooseFlag;
	/**
	 * id
	 */
	private java.lang.Integer id;
	//columns END 数据库字段结束
	
	//concstructor

	public SysSysparam(){
	}

	public SysSysparam(
		java.lang.String code
	){
		this.code = code;
	}

	//get and set
	public void setCode(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.code = value;
	}
	
	@Id
     @WhereSQL(sql="code=:SysSysparam_code")
	public java.lang.String getCode() {
		return this.code;
	}
	public void setValue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.value = value;
	}
	
     @WhereSQL(sql="value=:SysSysparam_value")
	public java.lang.String getValue() {
		return this.value;
	}
	public void setDescr(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.descr = value;
	}
	
     @WhereSQL(sql="descr=:SysSysparam_descr")
	public java.lang.String getDescr() {
		return this.descr;
	}
	public void setChooseFlag(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.chooseFlag = value;
	}
	
     @WhereSQL(sql="chooseFlag=:SysSysparam_chooseFlag")
	public java.lang.String getChooseFlag() {
		return this.chooseFlag;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
     @WhereSQL(sql="id=:SysSysparam_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("系统表的KEY[").append(getCode()).append("],")
			.append("系统表的值[").append(getValue()).append("],")
			.append("描述[").append(getDescr()).append("],")
			.append("是否使用(Y是N否)[").append(getChooseFlag()).append("],")
			.append("id[").append(getId()).append("],")
			.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCode())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SysSysparam == false) return false;
		if(this == obj) return true;
		SysSysparam other = (SysSysparam)obj;
		return new EqualsBuilder()
			.append(getCode(),other.getCode())
			.isEquals();
	}
}

	
