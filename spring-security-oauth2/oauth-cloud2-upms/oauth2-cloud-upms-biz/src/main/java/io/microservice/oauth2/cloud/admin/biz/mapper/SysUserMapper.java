package io.microservice.oauth2.cloud.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.microservice.oauth2.cloud.admin.api.dto.UserDTO;
import io.microservice.oauth2.cloud.admin.api.entity.SysUser;
import io.microservice.oauth2.cloud.admin.api.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser>{

    /**
     *根据用户名查询用户信息（包含角色信息）
     * @param username
     * @return UserVO
     */
    UserVO getUserVoByUsername(String username);

    /**
     * 通过ID查询用户信息
     * @param id 用户ID
     * @return userVo
     */
    UserVO getUserVoById(Long id);


    /**
     *查询所有用户信息
     * @param userDTO
     * @return
     */
    List<UserVO> selectVoList(@Param("query") UserDTO userDTO);


}
