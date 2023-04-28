package com.distributed.demo.handler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author xiaoxu123
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static Logger log = LoggerFactory.getLogger(SpringUtil.class);

    /**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        log.info("====================arg0:{}" , arg0);
        applicationContext = arg0;
    }

    /**
     * 获取bean
     * @param id
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getBean(String id, Class<T> type) {
        return applicationContext.getBean(id, type);
    }

    /**
     * 获取对象
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

}
