package com.example.delay_queue_demo.domain;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *延时队列实体类Bean
 *
 * @author xiaoxu123
 */
public class ItemVo<T> implements Delayed {
    //到期时间
    private long expriedTime;
    /**
     *业务实体-泛型
     */
    private T data;

    public ItemVo(Long expriedTime,T data){
       super();
       this.expriedTime=expriedTime+System.currentTimeMillis();
       this.data=data;
    }

    public long getExpriedTime() {
        return expriedTime;
    }

    public T getData() {
        return data;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        Long time=unit.convert(this.expriedTime-System.currentTimeMillis(),unit);
        return time;
    }

    @Override
    public int compareTo(Delayed o) {
        long time=getDelay(TimeUnit.NANOSECONDS)-o.getDelay(TimeUnit.NANOSECONDS);
        int result = (time == 0) ? 0 : ((time < 0) ? -1 : 0);
        return result;
    }
}
