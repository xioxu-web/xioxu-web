package cn.iocoder.oauth.module.infra.service.logger;

import cn.iocoder.oauth.framework.apilog.core.service.ApiErrorLogFrameworkService;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogExportReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiErrorLogDO;

import java.util.List;

/**
 * API 错误日志 Service 接口
 *
 * @author xiaoxu
 */
public interface ApiErrorLogService extends ApiErrorLogFrameworkService {

    /**
     * 获得 API 错误日志分页
     *
     * @param pageReqVO 分页查询
     * @return API 错误日志分页
     */
    PageResult<ApiErrorLogDO> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO);


    /**
     * 更新 API 错误日志已处理
     *
     * @param id API 日志编号
     * @param processStatus 处理结果
     * @param processUserId 处理人
     */
    void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId);

}
