package io.microservices.shop.user.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.microservices.shop.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoxu123
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
