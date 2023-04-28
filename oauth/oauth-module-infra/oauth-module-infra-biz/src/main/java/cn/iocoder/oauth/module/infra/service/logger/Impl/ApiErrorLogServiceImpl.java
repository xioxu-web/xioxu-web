package cn.iocoder.oauth.module.infra.service.logger.Impl;

import cn.iocoder.oauth.module.infra.service.logger.ApiErrorLogService;
import cn.iocoder.oauth.framework.apilog.core.service.dto.ApiErrorLogCreateReqDTO;
import cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogExportReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import cn.iocoder.oauth.module.infra.convert.logger.ApiErrorLogConvert;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import cn.iocoder.oauth.module.infra.dal.mysql.logger.ApiErrorLogMapper;
import cn.iocoder.oauth.module.infra.enums.ErrorCodeConstants;
import cn.iocoder.oauth.module.infra.enums.logger.ApiErrorLogProcessStatusEnum;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * API 错误日志 Service 实现类
 *
 * @author xiaoxu
 */
@Service
@Validated
public class ApiErrorLogServiceImpl implements ApiErrorLogService {

    @Resource
    private ApiErrorLogMapper apiErrorLogMapper;

    @Override
    public PageResult<ApiErrorLogDO> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO) {
        return apiErrorLogMapper.selectPage(pageReqVO);
    }


    @Override
    public void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId) {
        ApiErrorLogDO errorLog = apiErrorLogMapper.selectById(id);
        if (errorLog == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.API_ERROR_LOG_NOT_FOUND);
        }
        if (!ApiErrorLogProcessStatusEnum.INIT.getStatus().equals(errorLog.getProcessStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.API_ERROR_LOG_PROCESSED);
        }
        // 标记处理
        apiErrorLogMapper.updateById(ApiErrorLogDO.builder().id(id).processStatus(processStatus)
                .processUserId(processUserId).processTime(new Date()).build());
    }

    @Override
    @Async
    public void createApiErrorLogAsync(ApiErrorLogCreateReqDTO createDTO) {
        ApiErrorLogDO apiErrorLog = ApiErrorLogConvert.INSTANCE.convert(createDTO);
        apiErrorLog.setProcessStatus(ApiErrorLogProcessStatusEnum.INIT.getStatus());
        apiErrorLogMapper.insert(apiErrorLog);
    }

}
