package com.nai.vola;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        System.out.println(Thread.currentThread().getName() + ":" + "come in");
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {

        }
        System.out.println(Thread.currentThread().getName() + ":" + "持有锁成功");
    }

    public void myUnLock() {
        atomicReference.compareAndSet(Thread.currentThread(), null);
        System.out.println(Thread.currentThread().getName() + ":" + "释放锁成功");
    }

    public static void main(String[] args) {
        AtomicReferenceDemo atomicReferenceDemo = new AtomicReferenceDemo();
        new Thread(() -> {
            atomicReferenceDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicReferenceDemo.myUnLock();
        },"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            atomicReferenceDemo.myLock();
            atomicReferenceDemo.myUnLock();
        },"t2").start();

    }


}