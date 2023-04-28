package io.microservice.oauth2.cloud.admin.biz.service.Impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.microservice.oauth2.cloud.admin.api.entity.SysOauthClientDetails;
import io.microservice.oauth2.cloud.admin.biz.mapper.SysOauthClientDetailsMapper;
import io.microservice.oauth2.cloud.admin.biz.service.SysOauthClientDetailsService;
import io.microservice.oauth2.common.core.constant.CacheConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * @author xiaoxu123
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {

    /**
     *删除用户客户端信息
     * @param id
     * @return
     *
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY,key ="#id")
    public Boolean removeClientDetailsById(String id) {
        return this.removeById(id);
    }

    /**
     *修改客户端信息
     * @param clientDetails
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY,key ="clientDetails.clientId")
    public Boolean updateClientDetails(SysOauthClientDetails clientDetails) {
        return this.updateById(clientDetails);
    }

    /**
     *清除客户端换粗
     */
    @Override
    @CacheEvict(value =CacheConstants.DEFAULT_CODE_KEY,allEntries = true)
    public void clearClientCache() {
    }

}
