package com.multi_thread.combat.lock;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
  public static void main(String[] args) {
    final LockTes1 lockTes1 = new LockTes1();
    new Thread(lockTes1).start();
    new Thread(lockTes1).start();
    new Thread(lockTes1).start();
  }
}

class LockTes1 implements Runnable{
    int ticketNum=10;
    private final ReentrantLock lock=new ReentrantLock();
  @Override
  public void run() {
    while(true) {
      try {
        lock.lock();
      if (ticketNum > 0) {
          Thread.sleep(1000);
        System.out.println(ticketNum--);
      }else{
         break;
      }
    }catch (Exception e){
        e.printStackTrace();
      }finally {
        lock.unlock();
      }
      }
  }
}
