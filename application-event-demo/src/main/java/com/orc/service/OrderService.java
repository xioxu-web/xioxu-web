package com.orc.service;

import com.orc.bean.Order;
import com.orc.event.OrderGenericEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author xiaoxu123
 */
@Service
public class OrderService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 保存订单
     */
    public void save(){
        String orderNo = getOrderNo();
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setOrderStatus("待付款");
        order.setCreateTime(new Date());
        order.setGoods("手机");
        System.out.println("订单保存成功：" + order.toString());

        //发布事件
        applicationEventPublisher.publishEvent(order );
        applicationEventPublisher.publishEvent( new OrderGenericEvent(order,true) );
        System.out.println("======");
    }

    private String getOrderNo() {
        String millis = String.valueOf(System.currentTimeMillis());
        String str = millis.substring(millis.length() - 7, millis.length() - 1);
        return LocalDate.now().format( DateTimeFormatter.ofPattern("yyyyMMdd")) + str;
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
       this.applicationEventPublisher=applicationEventPublisher;
    }
}
