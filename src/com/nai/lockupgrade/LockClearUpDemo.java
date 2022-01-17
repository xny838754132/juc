package com.nai.lockupgrade;

public class LockClearUpDemo {
    public static void main(String[] args) {

    }

    static Object objectLock = new Object();

    public void m1() {
        Object objectLock = new Object();
        synchronized (objectLock) {
            System.out.println("---hello lock");
        }
    }
}