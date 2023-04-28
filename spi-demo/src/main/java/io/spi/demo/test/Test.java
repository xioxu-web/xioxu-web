package io.spi.demo.test;

import io.spi.demo.loader.MyServiceLoader;
import io.spi.demo.service.MyService;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * @author xiaoxu123
 */
public class Test {

    public static void main(String[] args){
        ServiceLoader<MyService> loader = MyServiceLoader.loadAll(MyService.class);
        StreamSupport.stream(loader.spliterator(), false).forEach(s -> s.print("Hello World"));
    }

}
