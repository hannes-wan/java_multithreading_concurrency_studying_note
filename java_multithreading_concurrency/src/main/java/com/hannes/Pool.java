package com.hannes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {
    public static void main(String[] args) {
        // 创建服务，创建线程池
        // 传入的的池子的大小
        ExecutorService service = Executors.newFixedThreadPool(10);

        // 执行
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());

        // 关闭连接
        service.shutdown();
    }
}

class MyThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + i);
        }
    }
}
