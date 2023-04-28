package com.zookeeper.demo.zk.listener;

import com.zookeeper.demo.zk.service.ZkServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

@Component
public class RegistryZooListener implements ServletContextListener {
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private ZkServiceRegistry serviceRegistry;

    @Override
    public
    void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext( servletContext );
        RequestMappingHandlerMapping mapping = applicationContext.getBean( RequestMappingHandlerMapping.class );
        //获取到所有的请求mapping
        Map<RequestMappingInfo, HandlerMethod> infoMap = mapping.getHandlerMethods();
        for (RequestMappingInfo info : infoMap.keySet()) {
            String serviceName = info.getName();
            if (serviceName != null) {
                //注册服务
                System.out.println(serviceName);
                serviceRegistry.register( serviceName, String.format( "%s:%d", serverAddress, serverPort ) );
            }
        }
    }

    @Override
    public
    void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}