package com.distributed.demo.handler.limit;

/**
 * @author xiaoxu123
 * @deprecated 限流器
 */
public interface LimiterStrategy {

    /**
     * 返回是否应该通过
     *
     * @param key
     * @return
     */
    boolean access(String key);
}
