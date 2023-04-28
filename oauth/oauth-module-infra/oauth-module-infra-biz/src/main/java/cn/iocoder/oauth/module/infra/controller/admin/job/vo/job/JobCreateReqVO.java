package cn.iocoder.oauth.module.infra.controller.admin.job.vo.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author xiaoxu123
 */
@ApiModel("管理后台 - 定时任务创建 Request VO")
@Data
@ToString(callSuper = true)
public class JobCreateReqVO extends JobBaseVO{

    @ApiModelProperty(value = "处理器的名字", required = true, example = "sysUserSessionTimeoutJob")
    @NotNull(message = "处理器的名字不能为空")
    private String handlerName;
}
