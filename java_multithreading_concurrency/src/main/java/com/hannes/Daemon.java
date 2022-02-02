package com.hannes;

// 守护线程实例
public class Daemon {
    public static void main(String[] args) {
        Runnable you = () -> {
            for (int i = 0; i < 80; i++) System.out.println("Smoke weed everyday");
            System.out.println("##### Goodbye world #####");
        };

        Runnable god = () -> {
            for(;;) System.out.println("God bless you");
        };

        // 设置上帝线程为守护线程
        Thread thread = new Thread(god);
        thread.setDaemon(true);
        thread.start();

        // 用户线程启动
        new Thread(you).start();
    }
}
