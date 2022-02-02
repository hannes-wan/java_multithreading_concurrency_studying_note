package com.hannes;

/*
    实现runnable接口，重写run方法
    执行线程需丢入runnable接口实现类，调用start方法
 */

public class Thread_03 implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("我在看代码" + i);
        }
    }

    // main线程，主线程
    public static void main(String[] args) {
        Thread_03 thread_03 = new Thread_03();
        new Thread(thread_03).start();

        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习多线程" + i);
        }
    }
}
