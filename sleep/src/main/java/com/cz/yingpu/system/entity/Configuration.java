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
 * @version  2017-02-24 15:17:26
 * @see com.cz.mts.system.entity.Configuration
 */
@Table(name="t_configuration")
public class Configuration  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "配置表";
	public static final String ALIAS_CODE = "系统表的KEY";
	public static final String ALIAS_VALUE = "系统表的值";
	public static final String ALIAS_DESCR = "描述";
	public static final String ALIAS_CHOOSEFLAG = "是否使用(Y是N否)";
    */
	//date formats
	
	//columns START
	/**
	 * 系统表的KEY
	 */
	private String code;
	/**
	 * 系统表的值
	 */
	private String value;
	/**
	 * 描述
	 */
	private String descr;
	/**
	 * 是否使用(Y是N否)
	 */
	private String chooseFlag;
	
	private Integer isImage;
	
	private Integer id;
	//columns END 数据库字段结束
	
	//concstructor

	public Configuration(){
	}

	public Configuration(
		String code
	){
		this.code = code;
	}

	//get and set
	public void setCode(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.code = value;
	}
	
	
	@WhereSQL(sql="isImage=:Configuration_isImage")
	 public Integer getIsImage() {
		return isImage;
	}

	public void setIsImage(Integer isImage) {
		this.isImage = isImage;
	}

	@WhereSQL(sql="id=:Configuration_id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Id
     @WhereSQL(sql="code=:Configuration_code")
	public String getCode() {
		return this.code;
	}
	public void setValue(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.value = value;
	}
	
     @WhereSQL(sql="value=:Configuration_value")
	public String getValue() {
		return this.value;
	}
	public void setDescr(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.descr = value;
	}
	
     @WhereSQL(sql="descr=:Configuration_descr")
	public String getDescr() {
		return this.descr;
	}
	public void setChooseFlag(String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.chooseFlag = value;
	}
	
     @WhereSQL(sql="chooseFlag=:Configuration_chooseFlag")
	public String getChooseFlag() {
		return this.chooseFlag;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("系统表的KEY[").append(getCode()).append("],")
			.append("系统表的值[").append(getValue()).append("],")
			.append("描述[").append(getDescr()).append("],")
			.append("是否使用(Y是N否)[").append(getChooseFlag()).append("],")
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
		if(obj instanceof Configuration == false) return false;
		if(this == obj) return true;
		Configuration other = (Configuration)obj;
		return new EqualsBuilder()
			.append(getCode(),other.getCode())
			.isEquals();
	}
}

	
