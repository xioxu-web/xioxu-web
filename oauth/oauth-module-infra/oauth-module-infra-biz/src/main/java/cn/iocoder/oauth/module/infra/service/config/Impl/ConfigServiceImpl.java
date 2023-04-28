package cn.iocoder.oauth.module.infra.service.config.Impl;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.config.vo.ConfigCreateReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.config.vo.ConfigExportReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.config.vo.ConfigPageReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.config.vo.ConfigUpdateReqVO;
import cn.iocoder.oauth.module.infra.convert.config.ConfigConvert;
import cn.iocoder.oauth.module.infra.dal.dataobject.config.ConfigDO;
import cn.iocoder.oauth.module.infra.dal.mysql.config.ConfigMapper;
import cn.iocoder.oauth.module.infra.enums.ErrorCodeConstants;
import cn.iocoder.oauth.module.infra.enums.config.ConfigTypeEnum;
import cn.iocoder.oauth.module.infra.service.config.ConfigService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 参数配置 Service 实现类
 * @author xiaoxu123
 */
@Service
@Slf4j
@Validated
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigMapper configMapper;


    @Override
    public Long createConfig(ConfigCreateReqVO reqVO) {
        // 校验正确性
        checkCreateOrUpdate(null, reqVO.getKey());
        // 插入参数配置
        ConfigDO config = ConfigConvert.INSTANCE.convert(reqVO);
        config.setType(ConfigTypeEnum.CUSTOM.getType());
        configMapper.insert(config);
        return config.getId();
    }

    @Override
    public void updateConfig(ConfigUpdateReqVO reqVO) {
        // 校验正确性
        checkCreateOrUpdate(reqVO.getId(), null); // 不允许更新 key
        // 更新参数配置
        ConfigDO updateObj = ConfigConvert.INSTANCE.convert(reqVO);
        configMapper.updateById(updateObj);
    }

    @Override
    public void deleteConfig(Long id) {
        // 校验配置存在
        ConfigDO config = checkConfigExists(id);
        // 内置配置，不允许删除
        if (ConfigTypeEnum.SYSTEM.getType().equals(config.getType())) {
            throw ServiceExceptionUtil.exception( ErrorCodeConstants.CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE);
        }
        // 删除
        configMapper.deleteById(id);
    }

    @Override
    public ConfigDO getConfig(Long id) {
        return configMapper.selectById(id);
    }

    @Override
    public ConfigDO getConfigByKey(String key) {
        return configMapper.selectByKey(key);
    }

    @Override
    public PageResult<ConfigDO> getConfigPage(ConfigPageReqVO reqVO) {
        return configMapper.selectPage(reqVO);
    }

    @Override
    public List<ConfigDO> getConfigList(@Valid ConfigExportReqVO reqVO) {
        return null;
    }


    private void checkCreateOrUpdate(Long id, String key) {
        // 校验自己存在
        checkConfigExists(id);
        // 校验参数配置 key 的唯一性
        if (StrUtil.isNotEmpty(key)) {
            checkConfigKeyUnique(id, key);
        }
    }

    @VisibleForTesting
    public ConfigDO checkConfigExists(Long id) {
        if (id == null) {
            return null;
        }
        ConfigDO config = configMapper.selectById(id);
        if (config == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CONFIG_NOT_EXISTS);
        }
        return config;
    }

    @VisibleForTesting
    public void checkConfigKeyUnique(Long id, String key) {
        ConfigDO config = configMapper.selectByKey(key);
        if (config == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的参数配置
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CONFIG_KEY_DUPLICATE);
        }
        if (!config.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CONFIG_KEY_DUPLICATE);
        }
    }
}
