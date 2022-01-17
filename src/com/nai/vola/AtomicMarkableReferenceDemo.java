package com.nai.vola;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {

    static AtomicMarkableReference<Integer> atomicMarkableReference = new AtomicMarkableReference<>(100, false);

    public static void main(String[] args) {
        new Thread(() -> {
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t" + "--默认修改标志:" + marked);
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            atomicMarkableReference.compareAndSet(100, 101, marked, !marked);
        }, "t1").start();

        new Thread(()->{
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t" + "--默认修改标志:" + marked);
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
            boolean set = atomicMarkableReference.compareAndSet(100, 2020, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "\t" + "isSuccess:" + set);
            System.out.println(Thread.currentThread().getName() + "\t" + atomicMarkableReference.getReference());
        },"t2").start();
    }
}