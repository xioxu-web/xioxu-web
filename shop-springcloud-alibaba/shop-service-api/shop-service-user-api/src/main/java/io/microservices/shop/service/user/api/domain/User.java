package io.microservices.shop.service.user.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxu123
 */
@TableName(value ="oauth_user")
@Data
public class User implements Serializable {
    /**
     * 用户名
     */
    @TableId(value ="username", type = IdType.INPUT)
    @TableField(value ="username", fill = FieldFill.INSERT)
    private String username;

    /**
     * 密码，加密存储
     */
    @TableField
    private String password;

    /**
     * 注册手机号
     */
    @TableField
    private String phone;

    /**
     * 注册邮箱
     */
    @TableField
    private String email;

    /**
     * 创建时间
     */
    @TableField
    private Date created;

    /**
     * 修改时间
     */
    @TableField
    private Date updated;

    /**
     * 会员来源：1:PC，2：H5，3：Android，4：IOS
     */
    @TableField
    private String sourceType;

    /**
     * 昵称
     */
    @TableField
    private String nickName;

    /**
     * 真实姓名
     */
    @TableField
    private String name;

    /**
     * 使用状态（1正常 0非正常）
     */
    @TableField
    private String status;

    /**
     * 头像地址
     */
    @TableField
    private String headPic;

    /**
     * QQ号码
     */
    @TableField
    private String qq;

    /**
     * 手机是否验证 （0否  1是）
     */
    @TableField
    private String isMobileCheck;

    /**
     * 邮箱是否检测（0否  1是）
     */
    @TableField
    private String isEmailCheck;

    /**
     * 性别，1男，0女
     */
    @TableField
    private String sex;

    /**
     * 会员等级
     */
    @TableField
    private Integer userLevel;

    /**
     * 积分
     */
    @TableField
    private Integer points;

    /**
     * 经验值
     */
    @TableField
    private Integer experienceValue;

    /**
     * 出生年月日
     */
    @TableField
    private Date birthday;

    /**
     * 最后登录时间
     */
    @TableField
    private Date lastLoginTime;
}
