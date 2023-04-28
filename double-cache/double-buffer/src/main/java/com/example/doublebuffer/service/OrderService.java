package com.example.doublebuffer.service;

import com.example.business.entity.Order;

/**
 * @author xiaoxu123
 */
public interface OrderService {

    Order getOrderById(Long id);

    Order updateOrder(Order order);

    void deleteOrder(Long id);

    Order getOrderByIdAndStatus(Long id,Integer status);

}
