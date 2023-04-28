package io.microservices.shop.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaoxu123
 */
@TableName(value ="sys_user")
@Data
public class SysUser {

    @TableId(value ="user_id", type = IdType.INPUT)
    @TableField(value ="user_id", fill = FieldFill.INSERT)
    private String userId;

    @TableField
    private String password;

    @TableField
    private String username;

    @TableField
    private Integer orgId;

    @TableField
    private Boolean enabled;

    @TableField
    private String phone;

    @TableField
    private String email;

    @TableField
    private Date createTime;
}
