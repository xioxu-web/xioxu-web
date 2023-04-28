package com.orc.event;

import com.orc.bean.Order;

/**
 * 创建订单事件
 * @author xiaoxu123
 */
public class OrderGenericEvent extends GenericEvent<Order>{

    public OrderGenericEvent(Order data, Boolean success) {
        super(data, success );
    }
}
