package io.microservice.oauth2.cloud.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.microservice.oauth2.cloud.admin.api.entity.SysOauthClientDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoxu123
 */
@Mapper
public interface SysOauthClientDetailsMapper extends BaseMapper<SysOauthClientDetails> {
}
