package com.nai.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

class ClickNumber {

    int number = 0;

    public synchronized void add() {

        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void addAtomicInteger() {
        atomicInteger.incrementAndGet();
    }

    AtomicLong atomicLong = new AtomicLong();

    public void addAtomicLong() {
        atomicLong.incrementAndGet();
    }

    LongAdder longAdder = new LongAdder();

    public void addLongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);

    public void addLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}

public class LongAdderCalcDemo {

    public static final int SIZE_THREAD = 50;

    public static final int _1W = 10000;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long startTIme;
        long endTime;

        CountDownLatch countDownLatch1 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch2 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch3 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch4 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch5 = new CountDownLatch(SIZE_THREAD);
        //-------------------------------------
        startTIme = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.add();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch1.countDown();
                }
            }, String.valueOf(i)).start();
        }

        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime - startTIme) + ",addSync:" + clickNumber.number);
        //---------------------------------------------------------------------
        startTIme = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.addAtomicInteger();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch2.countDown();
                }
            }, String.valueOf(i)).start();
        }

        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime - startTIme) + ",addAtomicInteger:" + clickNumber.atomicInteger.get());

        //------------------------------------------------
        startTIme = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.addAtomicLong();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch3.countDown();
                }
            }, String.valueOf(i)).start();
        }

        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime - startTIme) + ",addAtomicLong:" + clickNumber.atomicLong.longValue());

        //-----------------------------------------------------------
        startTIme = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.addLongAdder();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch4.countDown();
                }
            }, String.valueOf(i)).start();
        }

        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime - startTIme) + ",addLongAdder:" + clickNumber.longAdder.longValue());

        //------------------------------------------------
        startTIme = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.addLongAccumulator();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch5.countDown();
                }
            }, String.valueOf(i)).start();
        }

        countDownLatch5.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime - startTIme) + ",addLongAccumulator:" + clickNumber.longAccumulator.longValue());
    }
}