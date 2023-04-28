package io.microservices.shop.service.user.service;

import io.microservices.shop.service.user.api.domain.User;

/**
 * @author xiaoxu123
 */
public interface UserService {

    /**
     * 根据 id 查询 User
     * @param id
     * @return
     */
    User findById(String id);


}
