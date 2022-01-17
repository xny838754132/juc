package com.nai.stamped;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {
    static int number = 37;
    final static StampedLock STAMPED_LOCK = new StampedLock();

    // 写入
    public void write() {
        long stamp = STAMPED_LOCK.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t" + "===写线程准备修改");
        try {
            number = number + 13;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            STAMPED_LOCK.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "===写入线程结束修改");
    }

    // 悲观读
    public void read() {
        long stamp = STAMPED_LOCK.readLock();
        System.out.println(Thread.currentThread().getName() + "\t" + "come in readLock block,4 sends continue");
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t 正在读取中...");
        }
        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + "\t 获得成员变量值result=" + result);
            System.out.println("写线程没有修改值,因为stampedLock.readLock()读的时候,不可以写,读写互斥");
        } finally {
            STAMPED_LOCK.unlockRead(stamp);
        }
    }

    // 乐观读
    public void tryOptimisticRead() {
        long stamp = STAMPED_LOCK.tryOptimisticRead();
        int result = number;
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t 正在读取中....." +
                    i + "秒后stampedLock.validate值(true无修改,false有修改)  \t" + STAMPED_LOCK.validate(stamp));

        }
        if (!STAMPED_LOCK.validate(stamp)) {
            System.out.println("有人修改过---存在写操作！");
            stamp = STAMPED_LOCK.readLock();
            try {
                System.out.println("从乐观读 升级为悲观读 并重新获取数据");
                result = number;
                System.out.println("重新悲观读锁获取到的成员变量值result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                STAMPED_LOCK.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t finally value=" + result);
    }

    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();
        new Thread(resource::read, "readThread").start();
        new Thread(resource::tryOptimisticRead, "tryOptimisticRead").start();

    }
}