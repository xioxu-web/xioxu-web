package com.example.springboot_demo.Service;

import com.example.springboot_demo.Domain.User;

import java.util.List;

public interface UserInfoService {
  /*
   * 查询所有用户信息
   * */
  public List<User> queryAll();
}
