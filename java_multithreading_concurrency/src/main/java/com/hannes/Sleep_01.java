package com.hannes;

// 模拟网络延时：放大问题的发生性
// 线程是不安全的
public class Sleep_01 implements Runnable{
    private int ticket = 10;

    @Override
    public void run() {
        for(; ticket > 0; ticket --){

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "拿到了第 " + ticket +" 张票");
        }
    }

    public static void main(String[] args) {
        Sleep_01 sleep = new Sleep_01();

        new Thread(sleep, "hannes").start();
        new Thread(sleep, "marry").start();
        new Thread(sleep, "stack").start();
    }
}
