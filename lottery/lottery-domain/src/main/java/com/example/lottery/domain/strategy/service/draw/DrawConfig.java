package com.example.lottery.domain.strategy.service.draw;
import com.example.lottery.domain.strategy.model.vo.AwardRateInfo;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DrawConfig{
   private final int HASH_INCREMENT = 0x61c88647;

   //数组初始化长度
   private final int RATE_TUPLE_LENGTH = 128;

   //存放概率与奖品对应的散列结果
   protected Map<Long, String[]> rateTupleMap= new ConcurrentHashMap<>();
   //奖品区间概率值
   protected  Map<Long, List<AwardRateInfo>> awardRateInfoMap = new ConcurrentHashMap<>();

   public void initRateTuple(Long strategyId,List<AwardRateInfo> awardRateInfoList){
     //保存奖品概率信息
     awardRateInfoMap.put(strategyId,awardRateInfoList);

     rateTupleMap.computeIfAbsent(strategyId,k->new String[RATE_TUPLE_LENGTH]);
     
   }
}
