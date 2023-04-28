package cn.iocoder.oauth.module.infra.controller.admin.job.vo.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 定时任务 Base VO
 * @author xiaoxu123
 */
@Data
public class JobBaseVO {

   @ApiModelProperty(value = "任务名称",required = true,example ="测试任务")
   @NotNull(message ="任务名称不能为空")
   private String name;

   @ApiModelProperty(value ="处理器参数",required = true,example ="oauth")
   private String handlerParam;

   @ApiModelProperty(value = "CRON 表达式",required = true,example ="0/10 * * * * ? *")
   @NotNull(message ="CRON 表达式不能为空")
   private String cronExpression;

    @ApiModelProperty(value = "重试次数", required = true, example = "3")
    @NotNull(message = "重试次数不能为空")
    private Integer retryCount;

    @ApiModelProperty(value = "重试间隔", required = true, example = "1000")
    @NotNull(message = "重试间隔不能为空")
    private Integer retryInterval;

    @ApiModelProperty(value = "监控超时时间", example = "1000")
    private Integer monitorTimeout;

}
