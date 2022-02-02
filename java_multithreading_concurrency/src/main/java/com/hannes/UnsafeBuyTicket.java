package com.hannes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 不安全的买票
// 线程不安全，有负数
// 锁住buy方法就安全了
public class UnsafeBuyTicket {
    public static void main(String[] args) {
        BuyTicket station = new BuyTicket();
        new Thread(station, "me").start();
        new Thread(station, "you").start();
        new Thread(station, "others").start();
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class BuyTicket implements Runnable {

    private int ticketNums = 100;
    boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            try {
                buy();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // synchronized 加锁
    // 默认synchronized(this)，锁的是这个类本身
    private synchronized void buy() {
        if (ticketNums <= 0) {
            flag = false;
            return;
        }

        System.out.println(Thread.currentThread().getName() + "拿到" + ticketNums--);
    }
}
