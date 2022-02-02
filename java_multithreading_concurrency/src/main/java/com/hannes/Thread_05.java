package com.hannes;

// 模拟龟兔赛跑
public class Thread_05 implements Runnable{

    private static String winner;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            // 模拟兔子睡觉
            if(Thread.currentThread().getName().equals("rabbit")&& i%10==0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(gameOver(i)) break;
            System.out.println(Thread.currentThread().getName() + "跑了" + i + "步");
        }
    }

    private boolean gameOver(int steps){
        if (winner != null) return true;
        else if (steps >= 99){
            winner = Thread.currentThread().getName();
            System.out.println("winner is " + winner);
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Thread_05 thread_05 = new Thread_05();

        new Thread(thread_05,"rabbit").start();
        new Thread(thread_05,"tortoise").start();

    }
}
