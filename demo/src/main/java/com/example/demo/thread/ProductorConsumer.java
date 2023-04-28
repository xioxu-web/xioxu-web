package com.example.demo.thread;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaoxu123
 * Objec中的 wait/notifyAll 实现多个线程等待通知机制
 */
public class ProductorConsumer {


    public static void main(String[] args) {
        LinkedList<Object> linkedList =new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool( 15 );
        for (int i = 0; i <5 ; i++) {
            executorService.submit( new Productor( linkedList, 8 ) );
        }
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Consumer(linkedList));
        }

    }

   //生产者
    static class Productor implements Runnable{
      private  List<Integer> list;
      private int maxLength;

    public Productor(List list,int maxLength){
        this.list=list;
        this.maxLength=maxLength;
      }
        @Override
        public void run() {
          while(true){
            synchronized (list){
                try {
              while(list.size()==maxLength) {
                  System.out.println( "生产者:" + Thread.currentThread().getName() + "list已达到最大容量,进行wait" );
                  list.wait();
                  System.out.println( "生产者:" + Thread.currentThread().getName() + "停止生产数据" );
              }
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("生产者:"+Thread.currentThread().getName()+"生产数据"+i);
                    list.add(i);
                    list.notifyAll();
                } catch (InterruptedException e) {
                      e.printStackTrace();
                  }

              }
            }
          }
        }

    //消费者
    static class Consumer implements Runnable{
      private List<Integer> list;

      public Consumer(List list){
        this.list=list;
      }
        @Override
        public void run() {
          while(true){
            synchronized (list){
                try{
              while(list.isEmpty()) {
                  System.out.println( "生产者:" + Thread.currentThread().getName() + "list为空，进行wait" );
                  list.wait();
                  System.out.println( "消费者" + Thread.currentThread().getName() + "  退出wait" );
              }
                    Integer element = list.remove( 0 );
                    System.out.println("消费者消费数据:"+Thread.currentThread().getName()+element);
                    list.notifyAll();
                } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
            }
          }
        }
    }



