package com.example.demo.DesignPatterns;

/**
 * @author xiaoxu123
 * @deprecated java单例设计模式
 */
public class Singleton {

    /**
     * 设计成声明试
     */
    private volatile static Singleton instance;

    private Singleton(){
    }
    public static Singleton getInstance() {
       if(instance==null){
           synchronized (Singleton.class){
               if(instance==null) {
                   instance = new Singleton();
               }
           }
       }
        return instance;
    }


}
