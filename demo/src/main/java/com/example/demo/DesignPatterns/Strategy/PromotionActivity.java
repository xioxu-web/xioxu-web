package com.example.demo.DesignPatterns.Strategy;

/**
 * @author xiaoxu123
 * @deprecated 促销活动
 */
public class PromotionActivity {

    private PromotionStrategy promotionStrategy;

    /**
     * 构造器注入
     */
    public PromotionActivity(PromotionStrategy promotionStrategy){
      this.promotionStrategy=promotionStrategy;
    }

    public void executePromotionStrategy(){
        promotionStrategy.doPromotion();
    }

}
