package com.orc.api;

/**
 * @author xiaoxu123
 */
public interface UserService {
    ApiResult<User> getUser(Long id);
}
