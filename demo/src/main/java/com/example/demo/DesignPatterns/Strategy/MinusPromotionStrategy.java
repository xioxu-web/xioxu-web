package com.example.demo.DesignPatterns.Strategy;

/**
 * @author xiaoxu123
 * @deprecated 立减策略
 */
public class MinusPromotionStrategy implements PromotionStrategy {


    @Override
    public void doPromotion() {
        System.out.println("立减促销, 资源的价格直接减去配置的价格");
    }
}
