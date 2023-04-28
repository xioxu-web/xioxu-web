package com.example.mybatis_plus_demo.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxu123
 */
@Configuration
@EnableTransactionManagement
@MapperScan("")
public class MybatisPlusConfig {

    public static ThreadLocal<String> myTableName = new ThreadLocal<>();
    /**
     * 乐观锁插件
     *
     */
   @Bean
   public OptimisticLockerInterceptor optimisticLockerInterceptor(){
       return  new OptimisticLockerInterceptor();
   }

    /**
     *
     *SQL执行效率插件，性能分析插件
     */
   @Bean
   public PerformanceInterceptor performanceInterceptor(){
       PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
       performanceInterceptor.setFormat(true);
       performanceInterceptor.setMaxTime(5);
       return performanceInterceptor;
   }

    /**
     * 分页插件
     *
     */
   @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<>();
        tableNameHandlerMap.put("User",new ITableNameHandler() {
            @Override
            public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
                return myTableName.get();
            }
        });
        dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
        sqlParserList.add(dynamicTableNameParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        return  paginationInterceptor;
    }



}
