package com.distributed.demo.handler.limit;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author xiaoxu123
 * @deprecated 令牌桶限流器执行对象
 */
public class TokenBucketLimiterPolicy implements LimiterPolicy{

    /**
     *重置令牌桶时间间隔
     */
    private final long resetBucketInterval;
    /**
     * 最大令牌数
     */
    private final long bucketMaxTokens;

    /**
     * 初始化令牌桶数量
     */
    private final long initTokens;

    /**
     *令牌桶的产生时间
     */
    private final long intervalPerPermit;


    /**
     * 令牌桶对象的构造器
     * @param bucketMaxTokens 桶的令牌上限
     * @param resetBucketInterval 限流时间间隔 (单位毫秒)
     * @param initTokens 初始化令牌数
     */
    public TokenBucketLimiterPolicy(long bucketMaxTokens, long resetBucketInterval, long initTokens) {
        // 最大令牌数
        this.bucketMaxTokens = bucketMaxTokens;
        // 限流时间间隔
        this.resetBucketInterval = resetBucketInterval;
        // 令牌的产生间隔 = 限流时间 / 最大令牌数
        this.intervalPerPermit = resetBucketInterval / bucketMaxTokens;
        // 初始令牌数
        this.initTokens = initTokens;
    }

    public long getResetBucketInterval() {
        return resetBucketInterval;
    }

    public long getBucketMaxTokens() {
        return bucketMaxTokens;
    }

    public long getInitTokens() {
        return initTokens;
    }

    public long getIntervalPerPermit() {
        return intervalPerPermit;
    }

    @Override
    public List<String> toParams() {
        List<String > list = Lists.newArrayList();
        list.add(String.valueOf(getIntervalPerPermit()));
        list.add(String.valueOf(System.currentTimeMillis()));
        list.add(String.valueOf(getInitTokens()));
        list.add(String.valueOf(getBucketMaxTokens()));
        list.add(String.valueOf(getResetBucketInterval()));
        return list;
    }

}
