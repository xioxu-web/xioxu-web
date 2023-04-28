package com.spring.middleware.db.router.mapper;

import com.spring.middleware.db.router.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
@Component
public interface UserMapper {

    List<UserInfo> findAll();

    @Insert("insert into userinfo(user_id,username,pswd) values(#{userId},#{username},#{pswd})")
    int insert(UserInfo user);

    @Update("UPDATE userinfo SET username=#{username},pswd=#{pswd} WHERE user_id =#{user_id}")
    void update(UserInfo user);

    @Delete("DELETE FROM userinfo WHERE user_id =#{user_id}")

    void delete(Long userId);

}
