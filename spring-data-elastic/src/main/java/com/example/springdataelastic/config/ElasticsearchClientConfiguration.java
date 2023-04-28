package com.example.springdataelastic.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author xiaoxu123
 */
@Configuration
public class ElasticsearchClientConfiguration{
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClientConfiguration.class);

    /**
     * 建立连接超时时间
     */
    public static int  CONNECT_TIMEOUT_MILLIS=1000;

    /**
     *建立传输数据时超时时间
     */
    public static int SOCKET_TIMEOUT_MILLIS=30000;

    /**
    * 从连接池获取连接的超时时间
    */
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;

    /**
     * 路由节点的最大连接数
     */
    public static int MAX_CONN_PER_ROUTE = 10;

    /**
     * client最大连接数量
     */
    public static int MAX_CONN_TOTAL= 30;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.connTimeout}")
    private int connTimeout;

    @Value("${elasticsearch.socketTimeout}")
    private int socketTimeout;

    @Value("${elasticsearch.connectionRequestTimeout}")
    private int connectionRequestTimeout;

    @Bean(name ="client",destroyMethod ="close")
    public  RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectionRequestTimeout(connectionRequestTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectTimeout(connTimeout));
        return new RestHighLevelClient(builder);
    }

}
