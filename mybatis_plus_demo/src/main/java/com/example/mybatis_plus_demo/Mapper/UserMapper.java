package com.example.mybatis_plus_demo.Mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.mybatis_plus_demo.baseMapper.MyBaseMapper;
import com.example.mybatis_plus_demo.domain.User;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author xiaoxu123
 */
public interface UserMapper extends MyBaseMapper<User> {
    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);
}
