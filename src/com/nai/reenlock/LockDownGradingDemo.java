package com.nai.reenlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDownGradingDemo {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        writeLock.lock();
        System.out.println("---111");
        readLock.lock();
        System.out.println("read---");
        writeLock.unlock();
        readLock.unlock();
    }
}