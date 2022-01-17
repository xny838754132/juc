package com.nai;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    static volatile int x = 0;

    private static final List<Integer> list = new ArrayList<>();
    private static final List<Integer> list2 = new ArrayList<>();

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {


    }

    private static void m4() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("--i:" + i);
            }
            System.out.println("内 t1.interrupt()后 02：" + Thread.currentThread().isInterrupted());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }, "t1");
        t1.start();

        System.out.println("t1线程的中断标识默认值：" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
        System.out.println("t1.interrupt()后 01：" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 线程已经处于非活跃状态，已经执行结束了
        System.out.println("t1.interrupt()后 03：" + t1.isInterrupted());
    }

    private static void m2() {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("isInterrupted=true");
                    break;
                }
                System.out.println("isInterrupted=" + false);
            }
        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
        System.out.println("--" + t1.isInterrupted());

        // 修改t1的线程中断位为true
        new Thread(t1::interrupt, "t1").start();
    }

    private static void m1() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println("is atomicBoolean.get = true 程序结束");
                    break;
                }
                System.out.println("atomicBoolean=" + atomicBoolean);
            }
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> atomicBoolean.set(true)).start();
    }
}