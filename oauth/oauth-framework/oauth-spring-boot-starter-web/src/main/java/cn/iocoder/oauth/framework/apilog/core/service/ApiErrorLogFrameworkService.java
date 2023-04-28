package cn.iocoder.oauth.framework.apilog.core.service;

import cn.iocoder.oauth.framework.apilog.core.service.dto.ApiErrorLogCreateReqDTO;

import javax.validation.Valid;
import java.util.concurrent.Future;

/**
 * API 错误日志 Framework Service 接口
 *
 * @author admin
 */
public interface ApiErrorLogFrameworkService {

    /**
     * 创建 API 错误日志
     *
     * @param createDTO 创建信息
     */
    void createApiErrorLogAsync(@Valid ApiErrorLogCreateReqDTO createDTO);

}
