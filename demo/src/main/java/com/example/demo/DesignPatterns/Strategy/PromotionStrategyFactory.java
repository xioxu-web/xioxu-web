package com.example.demo.DesignPatterns.Strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxu123
 * @deprecated 策略工厂
 */
public class PromotionStrategyFactory {

    private static Map PROMOTION_STRATEGY_MAP  =new HashMap<String,PromotionStrategy>();

    static{
        PROMOTION_STRATEGY_MAP.put(PromotionKey.LIJIAN,new MinusPromotionStrategy());
        PROMOTION_STRATEGY_MAP.put(PromotionKey.MANJIAN,new FullOffPromotionStrategy());
        PROMOTION_STRATEGY_MAP.put(PromotionKey.FANXIAN,new CashBackPromotionStrategy());

    }

    private static final PromotionStrategy NON_PROMOTION=new EmptyPromotionStrategy();

    /**
     * 不允许外部调用
     */
    private PromotionStrategyFactory(){

    }

    public static PromotionStrategy getPromotionStrategy(String promotionKey){
        PromotionStrategy promotionStrategy = (PromotionStrategy) PROMOTION_STRATEGY_MAP.get( promotionKey );
        return promotionStrategy==null? NON_PROMOTION :promotionStrategy;
    }


    /**
     * 填充 map 中的 key
     */
    private interface PromotionKey {
        String LIJIAN = "LIJIAN";
        String FANXIAN = "FANXIAN";
        String MANJIAN = "MANJIAN";

    }
}
