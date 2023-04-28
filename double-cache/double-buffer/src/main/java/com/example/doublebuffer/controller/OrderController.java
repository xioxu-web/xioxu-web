package com.example.doublebuffer.controller;

import com.example.business.entity.Order;
import com.example.doublebuffer.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaoxu123
 */

@RestController
@RequestMapping("order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping("get/{id}")
    public Order get(@PathVariable("id") Long id){
        return orderService.getOrderById(id);
    }

    @PostMapping("update")
    public void updateOrder(@RequestBody Order order){
        orderService.updateOrder(order);
    }

    @GetMapping("get2")
    public Order get2(@RequestParam("id") Long id,@RequestParam("status")Integer status){
        return orderService.getOrderByIdAndStatus(id,status);
    }

    @DeleteMapping("del")
    public void del(@RequestParam("id") Long id){
        orderService.deleteOrder(id);
    }
}
