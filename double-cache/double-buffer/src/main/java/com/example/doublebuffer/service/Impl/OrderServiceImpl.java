package com.example.doublebuffer.service.Impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.business.entity.Order;
import com.example.business.mapper.OrderMapper;
import com.example.doublebuffer.annotation.CacheType;
import com.example.doublebuffer.annotation.DoubleCache;
import com.example.doublebuffer.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author xiaoxu123
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private  OrderMapper orderMapper;

    @Override
    @DoubleCache(cacheName = "order", key = "#id",
            type = CacheType.FULL)
    public Order getOrderById(Long id) {
       return orderMapper.selectOne(new LambdaQueryWrapper<Order>().eq(Order::getId,id));
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public Order getOrderByIdAndStatus(Long id, Integer status) {
        return null;
    }
}
