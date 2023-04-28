package com.spring.demo.test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author xiaoxu123
 *
 * Bean的生命周期
 */
public class User implements InitializingBean, DisposableBean {

   private String userName;

    public User(String userName) {
        this.userName = userName;
    }

    /**
     * 实例化Bean对象：在容器启动时，Spring会根据配置文件或注解等方式创建Bean的实例对象
     */
    public void init(){
        System.out.println("User Bean初始化方法调用...");
    }


    /**
     *设置Bean属性：Spring将配置文件或注解中定义的属性值通过setter方法注入到Bean实例中。
     */


    /**
     *BeanPostProcessor的前置处理：在Bean实例化之后，Spring会调用BeanPostProcessor的postProcessBeforeInitialization方法，对Bean进行增强或修改
     */


    /**
     *初始化Bean：Spring执行Bean的初始化方法，可以通过实现InitializingBean接口或注解@PostConstruct来指定初始化方法
     */


    /**
     *BeanPostProcessor的后置处理：在Bean初始化完成之后，Spring会调用BeanPostProcessor的postProcessAfterInitialization方法，对Bean进行增强或修改
     */


    /**
     *使用Bean：Bean初始化后，可以被容器或其他Bean引用使用
     */


    /**
     *销毁Bean：在应用程序关闭时，Spring会销毁Bean实例。可以通过实现DisposableBean接口或注解@PreDestroy来指定销毁方法
     */

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("");
    }




    @Override
    public void destroy() throws Exception {
        System.out.println("User Bean 销毁方法调用");
    }
}
