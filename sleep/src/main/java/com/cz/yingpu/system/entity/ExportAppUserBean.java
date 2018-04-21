package com.cz.yingpu.system.entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
public class ExportAppUserBean {

    private Integer id;
    private String phone;
    private String header;
    private String sex;
    private String name;
    private String inviteCode;
    private String inviteCodeUrl;
    private String wxCode;
    private String qqCode;
    private String realName;
    private String idCard;
    private String email;
    private String isIdCard;
    private Double balance;
    private Double quota;
    private Double yebBalance;
    private java.util.Date dateline;
    /**
     * 实名认证时间
     */
    private java.util.Date cardTime;
    /**
     * 最后登录时间
     */
    private java.util.Date lastLogin;
    /**
     * 总累计投资金额
     */
    private Double totalInvestAmount;
    /**
     * 现有投资金额
     */
    private Double nowInvestAmount;
    /**
     * 是否开启推送(0否1是)
     */
    private String isPush;
    /**
     * 邀请人
     */
    private Integer parentId;
    /**
     * 是否一周免签到
     */
    private String isWeekSign;
    /**
     * 免费签到到期时间
     */
    private java.util.Date weekSignEndTime;
    /**
     * 账面总余额
     */
    private Double ctBalance;
    /**
     * 可用余额
     */
    private Double caBalance;
    /**
     * 冻结余额
     */
    private Double cfBalance;
    /**
     * 未转接余额
     */
    private Double cuBalance;
    /**
     * Ios/android
     */
    private String osType;
    /**
     * 所属城市
     */
    private String cityName;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 开户行行别ID
     */
    private Integer bankId;
    /**
     * 银行卡号
     */
    private String bankNum;
    /**
     * 邀请人数
     */
    private Integer inviteNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteCodeUrl() {
        return inviteCodeUrl;
    }

    public void setInviteCodeUrl(String inviteCodeUrl) {
        this.inviteCodeUrl = inviteCodeUrl;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getQqCode() {
        return qqCode;
    }

    public void setQqCode(String qqCode) {
        this.qqCode = qqCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsIdCard() {
        return isIdCard;
    }

    public void setIsIdCard(String isIdCard) {
        this.isIdCard = isIdCard;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public Double getYebBalance() {
        return yebBalance;
    }

    public void setYebBalance(Double yebBalance) {
        this.yebBalance = yebBalance;
    }

    public Date getDateline() {
        return dateline;
    }

    public void setDateline(Date dateline) {
        this.dateline = dateline;
    }

    public Date getCardTime() {
        return cardTime;
    }

    public void setCardTime(Date cardTime) {
        this.cardTime = cardTime;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Double getTotalInvestAmount() {
        return totalInvestAmount;
    }

    public void setTotalInvestAmount(Double totalInvestAmount) {
        this.totalInvestAmount = totalInvestAmount;
    }

    public Double getNowInvestAmount() {
        return nowInvestAmount;
    }

    public void setNowInvestAmount(Double nowInvestAmount) {
        this.nowInvestAmount = nowInvestAmount;
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    public String getIsWeekSign() {
        return isWeekSign;
    }

    public void setIsWeekSign(String isWeekSign) {
        this.isWeekSign = isWeekSign;
    }

    public Date getWeekSignEndTime() {
        return weekSignEndTime;
    }

    public void setWeekSignEndTime(Date weekSignEndTime) {
        this.weekSignEndTime = weekSignEndTime;
    }

    public Double getCtBalance() {
        return ctBalance;
    }

    public void setCtBalance(Double ctBalance) {
        this.ctBalance = ctBalance;
    }

    public Double getCaBalance() {
        return caBalance;
    }

    public void setCaBalance(Double caBalance) {
        this.caBalance = caBalance;
    }

    public Double getCfBalance() {
        return cfBalance;
    }

    public void setCfBalance(Double cfBalance) {
        this.cfBalance = cfBalance;
    }

    public Double getCuBalance() {
        return cuBalance;
    }

    public void setCuBalance(Double cuBalance) {
        this.cuBalance = cuBalance;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public Integer getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(Integer inviteNum) {
        this.inviteNum = inviteNum;
    }
}
