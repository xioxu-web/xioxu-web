package com.example.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.business.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoxu123
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
