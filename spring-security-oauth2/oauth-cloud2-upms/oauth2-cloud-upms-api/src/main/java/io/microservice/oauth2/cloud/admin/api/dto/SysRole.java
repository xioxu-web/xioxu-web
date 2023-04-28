package io.microservice.oauth2.cloud.admin.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxu123
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {

    @TableId(value = "ROLE_ID", type = IdType.AUTO)
    private Long roleId;

    @TableField(value = "ROLE_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    private String roleName;

    @TableField(value = "CODE")
    @Size(max = 50, message = "{noMoreThan}")
    private String code;

    @TableField(value = "STATUS")
    @Size(max = 10, message = "{noMoreThan}")
    private String status;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "MODIFY_TIME")
    private Date modifyTime;

    private transient String menuIds;
}
