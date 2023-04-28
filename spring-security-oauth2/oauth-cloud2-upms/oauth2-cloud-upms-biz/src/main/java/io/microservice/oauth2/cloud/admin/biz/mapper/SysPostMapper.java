package io.microservice.oauth2.cloud.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.microservice.oauth2.cloud.admin.api.entity.SysPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     *通过用户ID，查询岗位信息
     * @param userId
     * @return 岗位信息
     */
    List<SysPost> listPostsByUserId(Long userId);
}
