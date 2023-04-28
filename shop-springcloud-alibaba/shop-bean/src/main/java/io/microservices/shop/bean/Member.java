package io.microservices.shop.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxu123
 */
@Data
@TableName(value ="t_member")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value ="id", type = IdType.INPUT)
    @TableField(value ="id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 会员等级id
     */
    private Long levelId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birth;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 用户来源
     */
    private Integer sourceType;
    /**
     * 积分
     */
    private Integer integration;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 删除标记（0-正常，1-删除）
     */
    private Integer delFlag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户编号
     */
    private String userId;

}
