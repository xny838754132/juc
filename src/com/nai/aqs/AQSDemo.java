package com.nai.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
    final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("--come in");
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("--come in");
            } finally {
                lock.unlock();
            }
        }, "B").start();


    }
}