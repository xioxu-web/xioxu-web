package io.microservices.shop.service.user.service.Impl;

import io.microservices.shop.service.user.api.domain.OauthClientDetails;
import io.microservices.shop.service.user.mapper.OauthClientDetailsMapper;
import io.microservices.shop.service.user.service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public OauthClientDetails findById(String id) {
        return oauthClientDetailsMapper.selectById(id);
    }


}
