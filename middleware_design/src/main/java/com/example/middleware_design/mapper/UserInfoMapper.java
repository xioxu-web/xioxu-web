package com.example.middleware_design.mapper;

import com.example.middleware_design.Domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

@Mapper
@CacheConfig(cacheNames = "UserInfo")
public interface UserInfoMapper {

  @Select("select * from userinfo")
  @Results(id = "student", value = {@Result(property = "userId", column ="userId", javaType = Integer.class),
    @Result(property = "username", column = "username", javaType = String.class),
    @Result(property = "pswd", column = "pswd", javaType = String.class)})
  List<UserInfo> queryAll();
}
