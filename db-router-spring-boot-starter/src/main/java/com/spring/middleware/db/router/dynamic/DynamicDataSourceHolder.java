package com.spring.middleware.db.router.dynamic;

import com.spring.middleware.db.router.Enum.DBTypeEnum;

/**
 * 数据源上下文
 * @author xiaoxu123
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    public static void set(DBTypeEnum dbTypeEnum){
        contextHolder.set(dbTypeEnum);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        System.out.println("--------以下操作为master（写操作）--------");
    }

    public static void slave() {
        set( DBTypeEnum.SLAVE);
        System.out.println("--------以下操作为slave（读操作）--------");
    }

    public static void clear() {
        contextHolder.remove();
    }


}
