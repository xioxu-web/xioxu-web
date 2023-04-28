package io.microservice.oauth2.cloud.admin.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.microservice.oauth2.cloud.admin.api.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoxu123
 */
@Data
public class UserDTO extends SysUser {

    /**
     * 角色ID
     */
    private List<Long> role;

    private Long deptId;

    /**
     * 岗位ID
     */
    private List<Long> post;

    /**
     * 新密码
     */
    private String newpassword1;

}
