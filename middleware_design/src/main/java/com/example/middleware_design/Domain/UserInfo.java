package com.example.middleware_design.Domain;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * @author xiaoxu123
 */
public class UserInfo implements Serializable {
    private String userId;
    private String username;
    private Integer age;
    private String address;

    public UserInfo(String userId, String username, Integer age, String address) {
        this.userId = userId;
        this.username = username;
        this.age = age;
        this.address = address;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
