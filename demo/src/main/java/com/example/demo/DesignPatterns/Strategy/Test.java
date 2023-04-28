package com.example.demo.DesignPatterns.Strategy;

/**
 * @author xiaoxu123
 */
public class Test {

   /* public static void main(String[] args) {
        PromotionActivity promotionActivity1 = new PromotionActivity( new CashBackPromotionStrategy() );
        PromotionActivity promotionActivity2 = new PromotionActivity( new MinusPromotionStrategy() );
        promotionActivity1.executePromotionStrategy();
        promotionActivity2.executePromotionStrategy();

    }*/

    public static void main(String[] args) {
        String PromotionKey = "LIJIAN";
        PromotionActivity promotionActivity = new PromotionActivity( PromotionStrategyFactory.getPromotionStrategy( PromotionKey ) );

        promotionActivity.executePromotionStrategy();
    }
}