package cn.iocoder.oauth.module.infra.controller.admin.logger;

import cn.iocoder.oauth.module.infra.service.logger.ApiErrorLogService;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import cn.iocoder.oauth.module.infra.convert.logger.ApiErrorLogConvert;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;
import static cn.iocoder.oauth.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Api(tags = "管理后台 - API 错误日志")
@RestController
@RequestMapping("/infra/api-error-log")
@Validated
public class ApiErrorLogController {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @PutMapping("/update-status")
    @ApiOperation("更新 API 错误日志的状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "processStatus", value = "处理状态", required = true, example = "1", dataTypeClass = Integer.class)
    })
    @PreAuthorize("@ss.hasPermission('infra:api-error-log:update-status')")
    public CommonResult<Boolean> updateApiErrorLogProcess(@RequestParam("id") Long id,
                                                          @RequestParam("processStatus") Integer processStatus) {
        apiErrorLogService.updateApiErrorLogProcess(id, processStatus, getLoginUserId());
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("获得 API 错误日志分页")
    @PreAuthorize("@ss.hasPermission('infra:api-error-log:query')")
    public CommonResult<PageResult<ApiErrorLogRespVO>> getApiErrorLogPage(@Valid ApiErrorLogPageReqVO pageVO) {
        PageResult<ApiErrorLogDO> pageResult = apiErrorLogService.getApiErrorLogPage(pageVO);
        return success(ApiErrorLogConvert.INSTANCE.convertPage(pageResult));
    }


}
