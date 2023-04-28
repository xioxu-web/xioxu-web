package com.example.mybatis_plus_demo.userTest;

import com.example.mybatis_plus_demo.domain.User;
import com.example.mybatis_plus_demo.Mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectAllUser(){
        List<User> userList= userMapper.selectList(null);
        userList.forEach(System.out::println);
    }


}
