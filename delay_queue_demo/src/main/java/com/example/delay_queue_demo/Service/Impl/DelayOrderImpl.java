package com.example.delay_queue_demo.Service.Impl;
import com.example.delay_queue_demo.Service.IDelayOrder;
import com.example.delay_queue_demo.domain.ItemVo;
import com.example.delay_queue_demo.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.DelayQueue;

/**
 * @author xiaoxu123
 */
@Service
public class DelayOrderImpl implements IDelayOrder {
    private final static Logger logger =LoggerFactory.getLogger(DelayOrderImpl.class);

    //检测弹出到期订单的线程
    private Thread takeOrder;

    private final DelayQueue<ItemVo<Order>> delayQueue=new DelayQueue<>();

    @Override
    public void orderDelay(Order order, Long expriedTime) {
        ItemVo<Order> orderItemVo = new ItemVo<>(expriedTime, order);
    }
}
