package cn.iocoder.oauth.framework.test.config;

import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * SQL 初始化的测试 Configuration
 *
 * 为什么不使用 org.springframework.boot.autoconfigure.sql.init.DataSourceInitializationConfiguration 呢？
 * 因为我们在单元测试会使用 spring.main.lazy-initialization 为 true，开启延迟加载。此时，会导致 DataSourceInitializationConfiguration 初始化
 * 不过呢，当前类的实现代码，基本是复制 DataSourceInitializationConfiguration 的哈！
 *
 * @author 芋道源码
 */
public class SqlInitializationTestConfiguration {





}
