package com.orc.listener;

import com.orc.bean.Order;
import com.orc.event.GenericEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxu123
 */
@Component
public class OrderCreateEventListenerAnnotation {

    @EventListener(condition = "#event.success")
    public void orderListener(GenericEvent<Order> event){
        System.out.println(this.getClass().getName()+"--处理泛型条件事件。。。");
    }

}
