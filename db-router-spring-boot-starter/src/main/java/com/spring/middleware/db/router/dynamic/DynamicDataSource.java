package com.spring.middleware.db.router.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * 定义动态数据源
 * @author xiaoxu123
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    @Nullable
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.get();
    }
}
