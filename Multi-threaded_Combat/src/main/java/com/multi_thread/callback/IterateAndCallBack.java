package com.multi_thread.callback;

import java.util.ArrayList;
import java.util.Arrays;

public class IterateAndCallBack {
  public static void main(String[] args) {
    final MyList<Integer> list = new MyList<>();
    list.addAll(Arrays.asList(2,4,6,8,10));
    list.each(new Functor<Integer>() {
      @Override
      public void handle(Integer integer) {
        System.out.println(integer);
      }
    });
  }
}
class MyList<E> extends ArrayList<E>{
    void each(Functor<E> functor) {
      for (E e:this) {
        functor.handle(e);
      }
    }
}
