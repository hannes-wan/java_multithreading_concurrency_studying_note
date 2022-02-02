package com.hannes;

public class State {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("##########################");
        });

        Thread.State state = thread.getState();
        System.out.println(state);

        thread.start();

        // 观察启动后
        state = thread.getState();
        System.out.println(state);

        // Thread.State.TERMINATED 是线程终止的状态
        while (state != Thread.State.TERMINATED) {
            Thread.sleep(100);
            state = thread.getState();  // 更新线程状态
            System.out.println(state);
        }

    }
}
