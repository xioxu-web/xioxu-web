package com.example.mybatis_plus_demo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis_plus_demo.domain.User;
import com.example.mybatis_plus_demo.Mapper.UserMapper;
import com.example.mybatis_plus_demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author xiaoxu123
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

}
