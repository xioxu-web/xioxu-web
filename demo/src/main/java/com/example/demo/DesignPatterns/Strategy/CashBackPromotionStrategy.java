package com.example.demo.DesignPatterns.Strategy;

/**
 * @author xiaoxu123
 * 返现策略
 */
public class CashBackPromotionStrategy implements PromotionStrategy{


    @Override
    public void doPromotion() {
        System.out.println("返现促销,返现的金额放到 xiaoxu 用户的钱包中");
    }
}
