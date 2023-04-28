package cn.iocoder.oauth.module.system.controller.admin.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author xiaoxu123
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginReqVO {

    @ApiModelProperty(value ="账号",required = true,example ="xiaoxu")
    @NotBlank(message = "登录账号不能为空")
    @Length(min =4,max =16,message ="登录账号为4到16位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

    @ApiModelProperty(value ="密码",required = true,example ="123456")
    @NotBlank(message ="登录密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

    @ApiModelProperty(value = "验证码", required = true, example = "1024", notes = "验证码开启时，需要传递")
    @NotEmpty(message = "验证码不能为空", groups = CodeEnableGroup.class)
    private String code;

    @ApiModelProperty(value = "验证码的唯一标识", required = true, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62", notes = "验证码开启时，需要传递")
    @NotEmpty(message = "唯一标识不能为空", groups = CodeEnableGroup.class)
    private String uuid;

    /**
     * 开启验证码的 Group
     */
    public interface CodeEnableGroup {}

}
