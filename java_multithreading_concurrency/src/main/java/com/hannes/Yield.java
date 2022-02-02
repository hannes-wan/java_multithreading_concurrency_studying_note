package com.hannes;

// 测试礼让
// 礼让不一定成功，看CPU心情
public class Yield {
    public static void main(String[] args) {

        // 直接在这儿实现Runnable，省事儿
        Runnable run = () -> {
            System.out.println(Thread.currentThread().getName() + "线程开始执行");
            Thread.yield();
            System.out.println(Thread.currentThread().getName() + "线程停止执行");
        };

        new Thread(run, "a").start();
        new Thread(run, "b").start();
    }
}
