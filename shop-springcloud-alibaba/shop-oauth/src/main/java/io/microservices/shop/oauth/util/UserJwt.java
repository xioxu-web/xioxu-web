package io.microservices.shop.oauth.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

/**
 * @author xiaoxu123
 * @Description: 封装用户信息的对象，用于存储到JWT中
 */
public class UserJwt extends User{
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名字
     */
    private String name;

    /**
     * 设置公司
     */
    private String company;

    /**
     * 地址
     */
    private String address;

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
