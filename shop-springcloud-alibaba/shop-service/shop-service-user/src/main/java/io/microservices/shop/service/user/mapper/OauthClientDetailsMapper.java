package io.microservices.shop.service.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.microservices.shop.service.user.api.domain.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

}
