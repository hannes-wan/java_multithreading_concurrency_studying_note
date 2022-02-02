package com.hannes;

// 建议线程正常通知 -> 利用次数，不建议死循环
// 建议使用标志位 -> 设置一个标志位
// 不要使用stop或者destroy等过时或者JDK不建议使用的方法
public class Stop implements Runnable{

    // 设置一个标志位
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        for (; flag; i++)
            System.out.println("run...thread " + i);
    }

    public static void main(String[] args) {
        Stop stop = new Stop();
        new Thread(stop).start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("main" + i);
            if (i >= 900){
                // 调用stop方法切换标志位，让线程停止
                stop.stop();
            }
        }

    }

    public void stop(){
        flag = false;
    }
}
