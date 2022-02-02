package com.hannes;

// 并发问题：多个线程操作同一个资源的情况下，线程不安全，数据紊乱
public class Thread_04 implements Runnable{

    private int ticket = 10;

    @Override
    public void run() {
        for(; ticket >= 0; ticket --){
            System.out.println(Thread.currentThread().getName() + "拿到了第 " + ticket +" 张票");
        }
    }

    public static void main(String[] args) {
        Thread_04 thread_04 = new Thread_04();

        new Thread(thread_04, "hannes").start();
        new Thread(thread_04, "marry").start();
        new Thread(thread_04, "stack").start();
    }
}
