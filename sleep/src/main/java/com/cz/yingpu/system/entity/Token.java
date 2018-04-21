package com.cz.yingpu.system.entity;

import java.util.Date;

/**
 * Created by lenovo on 2017/3/23.
 */
public class Token {

    private String userId;//登录id
    private String token;
    private Date expired; //过期时间
    private Date lastOperate; // 最近一次操作的时间


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Date getLastOperate() {
        return lastOperate;
    }

    public void setLastOperate(Date lastOperate) {
        this.lastOperate = lastOperate;
    }
}
