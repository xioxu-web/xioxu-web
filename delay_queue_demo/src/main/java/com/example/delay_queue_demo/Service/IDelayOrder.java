package com.example.delay_queue_demo.Service;

import com.example.delay_queue_demo.domain.Order;

/**
 * @author xiaoxu123
 */
public interface IDelayOrder {
    /**
     * 处理延时订单
     *
     */
  public void  orderDelay(Order order, Long expriedTime);

}
