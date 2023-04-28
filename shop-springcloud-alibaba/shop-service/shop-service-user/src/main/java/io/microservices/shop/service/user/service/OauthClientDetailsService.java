package io.microservices.shop.service.user.service;

import io.microservices.shop.service.user.api.domain.OauthClientDetails;

import java.util.List;

/**
 * @author xiaoxu123
 */
public interface OauthClientDetailsService {
    /**
     * 根据 ID 查询 OauthClientDetails
     *
     * @param id
     * @return
     */
    OauthClientDetails findById(String id);

}
