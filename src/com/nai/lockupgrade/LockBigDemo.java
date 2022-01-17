package com.nai.lockupgrade;

public class LockBigDemo {
    static final Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println("--------1");
            }
            synchronized (objectLock) {
                System.out.println("--------2");
            }
            synchronized (objectLock) {
                System.out.println("--------3");
            }
            synchronized (objectLock) {
                System.out.println("--------4");
            }
        }, "t1").start();
    }
}