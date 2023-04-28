package com.spring.middleware.db.router.domain;

import java.io.Serializable;

/**
 * @author xiaoxu123
 */
public class UserInfo implements Serializable {

    private String userId;
    private String username;
    private String pswd;

    public UserInfo() {
    }
    public UserInfo(String userId, String username, String pswd) {
        this.userId = userId;
        this.username = username;
        this.pswd = pswd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", pswd='" + pswd + '\'' +
                '}';
    }
}
