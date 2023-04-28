package io.microservice.oauth2.cloud.order.controller;

import io.microservice.oauth2.cloud.auth.common.model.LoginVal;
import io.microservice.oauth2.cloud.auth.common.model.order.Order;
import io.microservice.oauth2.cloud.auth.common.utils.OauthUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    //构造两个订单
    public static List<Order> orders=new ArrayList<>();

    static{
        Order order1 = Order.builder()
                .id(1L)
                .useMoney(10000L)
                .build();
        Order order2 = Order.builder()
                .id(2L)
                .useMoney(10000L)
                .build();

        orders.add(order1);
        orders.add(order2);
    }

    @RequestMapping("/login/info")
    public LoginVal info(){
        return OauthUtils.getCurrentUser();
    }

    @RequestMapping("/login/admin")
    public LoginVal admin() {
        return OauthUtils.getCurrentUser();
    }
}
