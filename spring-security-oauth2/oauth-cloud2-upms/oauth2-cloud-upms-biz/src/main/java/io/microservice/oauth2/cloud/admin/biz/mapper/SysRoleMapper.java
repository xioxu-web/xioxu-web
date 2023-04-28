package io.microservice.oauth2.cloud.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.microservice.oauth2.cloud.admin.api.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     *通过用户ID，查询角色信息
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(String userId);

}
