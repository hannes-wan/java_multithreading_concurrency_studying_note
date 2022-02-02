package com.hannes;

/*
 * 创建线程方法之一，继承Thread类，重写run()方法，调用start开启线程
 * 注意：线程开启不一定立即执行，由CPU调度执行
 */

public class Thread_01 extends Thread{

    @Override
    // run方法线程体
    public void run(){
        for (int i = 0; i < 20; i++) {
            System.out.println("我在看代码" + i);
        }
    }

    // main线程，主线程
    public static void main(String[] args) {

        Thread_01 thread_01 = new Thread_01();

        thread_01.start();

        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习多线程" + i);
        }
    }
}
