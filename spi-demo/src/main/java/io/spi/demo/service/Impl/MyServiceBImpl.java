package io.spi.demo.service.Impl;

import io.spi.demo.service.MyService;

/**
 * @author xiaoxu123
 * @version 1.0.0
 * @description 接口的第一个实现类
 */
public class MyServiceBImpl implements MyService {

    @Override
    public void print(String info) {
        System.out.println(MyServiceBImpl.class.getName() + " print " + info);
    }
}
