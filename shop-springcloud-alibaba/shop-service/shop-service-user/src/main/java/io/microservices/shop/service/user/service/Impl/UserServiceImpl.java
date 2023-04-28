package io.microservices.shop.service.user.service.Impl;

import io.microservices.shop.service.user.api.domain.User;
import io.microservices.shop.service.user.mapper.UserMapper;
import io.microservices.shop.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaoxu123
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findById(String id) {
        return userMapper.selectById(id);
    }
}
