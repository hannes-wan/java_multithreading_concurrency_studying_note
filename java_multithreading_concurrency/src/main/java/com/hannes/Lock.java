package com.hannes;

import java.util.concurrent.locks.ReentrantLock;

public class Lock {
    public static void main(String[] args) {
        TestLock testLock = new TestLock();

        new Thread(testLock,"thread1").start();
        new Thread(testLock,"thread2").start();
        new Thread(testLock,"thread3").start();
    }
}

class TestLock implements Runnable{

    int ticketNums = 10;

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {

        for(;ticketNums > 1; ticketNums --) {

            try {
                // 加锁
                lock.lock();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ---> " + ticketNums);

            } finally {
                // 解锁
                lock.unlock();
            }
        }
    }
}
