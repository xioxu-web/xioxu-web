package com.example.middleware_design.service;

import com.example.middleware_design.Domain.UserInfo;

import java.util.List;

/**
 * @author xiaoxu123
 */
public interface UserInfoService {
  /*
   * 查询所有用户信息
   * */
  public List<UserInfo> queryAll();
}
