package com.spring.middleware.db.router.service;

import com.spring.middleware.db.router.mapper.UserMapper;
import com.spring.middleware.db.router.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author xiaoxu123
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //@Master
    public List<UserInfo> findAll() {
        List<UserInfo> userList = userMapper.findAll();
        return userList;
    }

    public int insertUser(UserInfo user)
    {
        int i = userMapper.insert(user);
        return i;
    }

}
