package com.example.demo.DesignPatterns.Strategy;

/**
 * @author xiaoxu123
 * @deprecated 无促销
 */
public class EmptyPromotionStrategy implements PromotionStrategy{

    @Override
    public void doPromotion() {
        System.out.println("无促销");
    }
}
