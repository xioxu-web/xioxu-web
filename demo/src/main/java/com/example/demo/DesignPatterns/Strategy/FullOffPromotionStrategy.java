package com.example.demo.DesignPatterns.Strategy;

/**
 * @author xiaoxu123
 * @deprecated 满减策略
 */
public class FullOffPromotionStrategy implements PromotionStrategy{

    @Override
    public void doPromotion() {
        System.out.println("满减促销,满200减20");
    }
}
