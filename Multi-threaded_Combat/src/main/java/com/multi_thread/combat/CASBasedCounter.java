package com.multi_thread.combat;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicInteger;
/*
* 使用CAS实现一个线程安全的计数器
* */
public class CASBasedCounter {
  public static void main(String[] args) {
    CASCounter casCounter = new CASCounter();
    for (int i=0;i<1000;i++) {
      new Thread(() -> System.out.println(casCounter.incrementAndGet())).start();
    }
  }
}
class CASCounter{
    private volatile long value;

  public long getValue() {
    return value;
  }
  /*
  * 定义
  * */
   private boolean CompareAndSwap(Long expectedValue,Long newValue){
     synchronized (this){
       if(value==expectedValue){
         value=newValue;
         return true;
       }else {
         return  false;
       }
     }
   }

   public long incrementAndGet(){
     long oldvalue;
     long newvalue;
     do {
       oldvalue=value;
       newvalue=oldvalue+1;
     }while(!CompareAndSwap(oldvalue,newvalue));
       return newvalue;
     }

}


