package com.hannes;

// 优先级看CPU心情
// 只是意味着获得调度的概率低
public class Priority {
    public static void main(String[] args) {
        Runnable run = () -> System.out.println(Thread.currentThread().getName() + "-->" + Thread.currentThread().getPriority());
        Thread t1 = new Thread(run, "t1");
        Thread t2 = new Thread(run, "t2");
        Thread t3 = new Thread(run, "t3");
        Thread t4 = new Thread(run, "t4");
        Thread t5 = new Thread(run, "t5");

        t1.start();

        t2.setPriority(1);
        t2.start();

        t3.setPriority(4);
        t3.start();

        t4.setPriority(Thread.MAX_PRIORITY);
        t4.start();

        t5.setPriority(8);
        t5.start();
    }
}
