package com.nai.reenlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> myResource.write(finalI + "", finalI + ""), String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> myResource.read(finalI + ""), String.valueOf(i)).start();
        }
    }

}

class MyResource {

    Map<String, String> map = new HashMap<>();

    Lock lock = new ReentrantLock();

    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "--正在写入");
            map.put(key, value);
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println(Thread.currentThread().getName() + "\t" + "--完成写入");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void read(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "--正在读取");
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t" + "--完成读取,result=" + result);
        } finally {
            rwLock.readLock().unlock();
        }
    }
}