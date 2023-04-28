package cn.iocoder.oauth.module.infra.controller.admin.logger;

import cn.iocoder.oauth.module.infra.service.logger.ApiAccessLogService;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogRespVO;
import cn.iocoder.oauth.module.infra.convert.logger.ApiAccessLogConvert;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import cn.iocoder.oauth.module.infra.service.logger.ApiAccessLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.Valid;


import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;


/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - API 访问日志")
@RestController
@RequestMapping("/infra/api-access-log")
@Validated
public class ApiAccessLogController {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @GetMapping("/page")
    @ApiOperation("获得API 访问日志分页")
    @PreAuthorize("@ss.hasPermission('infra:api-access-log:query')")
    public CommonResult<PageResult<ApiAccessLogRespVO>> getApiAccessLogPage(@Valid ApiAccessLogPageReqVO pageVO) {
        PageResult<ApiAccessLogDO> pageResult = apiAccessLogService.getApiAccessLogPage(pageVO);
        return success(ApiAccessLogConvert.INSTANCE.convertPage(pageResult));
    }


}
