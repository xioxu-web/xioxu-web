package io.spi.demo.loader;

import java.util.ServiceLoader;

/**
 * @author xiaoxu123
 * @version 1.0.0
 * @description 类加载工具
 */
public class MyServiceLoader {

    /**
     * 使用SPI机制加载所有的Class
     */
    public static<S> ServiceLoader<S> loadAll(final Class<S> clazz){
        return ServiceLoader.load(clazz);
    }

}
