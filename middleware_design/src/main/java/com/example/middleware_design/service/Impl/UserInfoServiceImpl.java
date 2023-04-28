package com.example.middleware_design.service.Impl;

import com.example.middleware_design.Domain.UserInfo;
import com.example.middleware_design.mapper.UserInfoMapper;
import com.example.middleware_design.service.UserInfoService;
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
  public List<UserInfo> queryAll() {
    return userInfoMapper.queryAll();
  }
}
