package com.example.springdataelastic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springdataelastic.pojo.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author xiaoxu123
 */
@Mapper
public interface DiscussPostMapper extends BaseMapper<DiscussPost> {


}
