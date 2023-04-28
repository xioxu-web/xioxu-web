package cn.iocoder.oauth.module.system.controller.admin.logger;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.iocoder.oauth.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.iocoder.oauth.module.system.convert.logger.LoginLogConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.logger.LoginLogDO;
import cn.iocoder.oauth.module.system.service.logger.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.Valid;
/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 登录日志")
@RestController
@RequestMapping("/system/login-log")
@Validated
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @GetMapping("/page")
    @ApiOperation("获得登录日志分页列表")
    @PreAuthorize("@ss.hasPermission('system:login-log:query')")
    public CommonResult<PageResult<LoginLogRespVO>> getLoginLogPage(@Valid LoginLogPageReqVO reqVO) {
        PageResult<LoginLogDO> page = loginLogService.getLoginLogPage(reqVO);
        return CommonResult.success(LoginLogConvert.INSTANCE.convertPage(page));
    }

    

}
