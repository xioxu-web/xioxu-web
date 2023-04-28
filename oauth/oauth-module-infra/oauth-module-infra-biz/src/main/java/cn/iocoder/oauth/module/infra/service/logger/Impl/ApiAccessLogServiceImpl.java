package cn.iocoder.oauth.module.infra.service.logger.Impl;

import cn.iocoder.oauth.module.infra.service.logger.ApiAccessLogService;
import cn.iocoder.oauth.framework.apilog.core.service.dto.ApiAccessLogCreateReqDTO;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExportReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import cn.iocoder.oauth.module.infra.convert.logger.ApiAccessLogConvert;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import cn.iocoder.oauth.module.infra.dal.mysql.logger.ApiAccessLogMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.List;

/**
 * API 访问日志 Service 实现类
 *
 * @author xiaoxu
 */
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Resource
    private ApiAccessLogMapper apiAccessLogMapper;

    @Override
    public PageResult<ApiAccessLogDO> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO) {
        return apiAccessLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ApiAccessLogDO> getApiAccessLogList(ApiAccessLogExportReqVO exportReqVO) {
        return apiAccessLogMapper.selectList(exportReqVO);
    }

    @Override
    @Async
    public void createApiAccessLogAsync(ApiAccessLogCreateReqDTO createDTO) {
        ApiAccessLogDO apiAccessLog = ApiAccessLogConvert.INSTANCE.convert(createDTO);
        apiAccessLogMapper.insert(apiAccessLog);
    }

}
