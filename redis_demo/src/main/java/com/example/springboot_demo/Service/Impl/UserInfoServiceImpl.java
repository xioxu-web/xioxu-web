package com.example.springboot_demo.Service.Impl;

import com.example.springboot_demo.Domain.User;
import com.example.springboot_demo.mapper.UserInfoMapper;
import com.example.springboot_demo.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
  @Autowired
  private UserInfoMapper userInfoMapper;

  @Override
  public List<User> queryAll() {
    return userInfoMapper.queryAll();
  }
}
