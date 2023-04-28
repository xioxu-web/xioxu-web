package com.multi_thread.callback;

public interface Functor<E> {
   void handle(E e);
}
