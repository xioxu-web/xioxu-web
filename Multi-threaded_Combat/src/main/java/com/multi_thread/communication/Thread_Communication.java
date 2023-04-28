package com.multi_thread.communication;
import lombok.SneakyThrows;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu123
 */
public class Thread_Communication {

    public static void main(String[] args) {
        SynContainer container = new SynContainer();
        new Productor(container).start();
        new Consumer(container).start();
    }

}
//生产者
class Productor extends Thread{
    SynContainer container;
    public Productor(SynContainer container){
        this.container=container;
    }
  //生产数据
  @SneakyThrows
  @Override
  public void run(){
      for (int i=0;i<10;i++) {
         container.push(new Product(i));
         TimeUnit.SECONDS.sleep(1);
      }
  }
}
//消费者
class Consumer extends Thread{
    SynContainer container;
    public Consumer(SynContainer container){
        this.container=container;
    }
    /**
     *消费数据
     */
    @Override
    public void run(){
        for (int i=0;i<10;i++) {
            int id = container.pop().id;
        }
    }
}
//产品
class Product{
   int id;
   public Product(int id){
     this.id=id;
   }
}
//缓冲区
class SynContainer{
    Product[] products=new Product[10];
    //容器计数器
    int count=0;
    //生产者放入产品
    public synchronized void push(Product product){
       if(count==products.length){
           try {
               System.out.println("生产者等待中...");
               this.wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
       products[count]=product;
       count++;
       System.out.println("生产了一个产品...");
       System.out.println("容器内有"+count+"个产品");
       this.notify();
    }
  //消费者去消费产品
  public synchronized Product pop(){
    //判断是否能消费
    if(count==0){
        try {

            System.out.println("消费者等待中...");
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //如果可以消费
    count--;
    System.out.println("消费了一个产品...");
    System.out.println("容器内有"+count+"产品");
    Product product=products[count];
    this.notify();
    return product;
  }

}
