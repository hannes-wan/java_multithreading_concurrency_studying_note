package com.hannes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UnsafeBank {
    public static void main(String[] args) {
        Account account = new Account(100, "结婚基金");
        new Thread(new Drawing(account, 50, 0), "hannes").start();
        new Thread(new Drawing(account, 100, 0), "marry").start();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
// 银行账户
class Account{
    private int money;
    private String name;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
// 取钱
class Drawing implements Runnable{
    // 账户
    private Account account;
    // 取了多少钱
    private int drawingMoney;
    // 现在手里有多少钱
    private int nowMoney;

    @Override
    // synchronized默认锁的是this，相当于synchronized = synchronized(this)，所以不能直接锁run方法
    // 上面new了两个对象，两个线程操作的是两个对象，如果直接加synchronized就是两把锁而不是同一把锁
    // 如果直接加，锁住的是银行，两个人取同一个账户应该锁账户，而不是锁银行
    // 需要用同步块来加锁
    // 锁Account之所以能成功是因为Account是共有的
    public synchronized void run() {

        // synchronized 同步块，对account进行加锁
        synchronized (account) {
            // 先判断有没有钱
            if (account.getMoney() - drawingMoney < 0) {
                System.out.println(Thread.currentThread().getName() + "余额不足");
                return;
            }

            // sleep可以放大问题的发生性
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            account.setMoney(account.getMoney() - drawingMoney);
            nowMoney += drawingMoney;

            System.out.println(account.getName() + "余额为：" + account.getMoney());
            System.out.println(Thread.currentThread().getName() + "手里的钱：" + nowMoney);
        }
    }
}
