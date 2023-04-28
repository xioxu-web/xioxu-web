package io.microservices.shop.service.user.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.microservices.shop.service.user.api.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaoxu123
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);
}
