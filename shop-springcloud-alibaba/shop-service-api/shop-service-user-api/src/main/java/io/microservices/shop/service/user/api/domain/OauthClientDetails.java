package io.microservices.shop.service.user.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaoxu123
 */
@TableName(value = "oauth_client_details")
@Data
public class OauthClientDetails implements Serializable {

    /**
     * 客户端ID，主要用于标识对应的应用
     */
    @TableId(value ="clientId", type = IdType.INPUT)
    @TableField(value ="clientId", fill = FieldFill.INSERT)
    private String clientId;

    /**
     *
     */
    @TableField
    private String resourceIds;

    /**
     * 客户端秘钥，BCryptPasswordEncoder加密算法加密
     */
    @TableField
    private String clientSecret;

    /**
     * 对应的范围
     */
    @TableField
    private String scope;

    /**
     * 认证模式
     */
    @TableField
    private String authorizedGrantTypes;

    /**
     * 认证后重定向地址
     */
    @TableField
    private String webServerRedirectUri;

    /**
     *
     */
    @TableField
    private String authorities;

    /**
     * 令牌有效期
     */
    @TableField
    private Integer accessTokenValidity;

    /**
     * 令牌刷新周期
     */
    @TableField
    private Integer refreshTokenValidity;

    /**
     *
     */
    @TableField
    private String additionalInformation;

    /**
     *
     */
    @TableField
    private String autoapprove;

}
