package com.hannes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// 死锁：多个线程互相抱着对方需要的资源，然后形成僵持
public class DeadLock {
    public static void main(String[] args) {
        Makeup g1 = new Makeup(0, "灰姑娘");
        Makeup g2 = new Makeup(1, "白雪公族");

        g1.start();
        g2.start();
    }
}

// 口红
class Lipstick {

}

// 镜子
class Mirror {

}

@AllArgsConstructor
@NoArgsConstructor
class Makeup extends Thread {

    // 用static保证需要的资源只有一个
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    // 选择
    int choice;
    // 使用化妆用具的人
    String name;

    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeup() throws InterruptedException {
        if (choice == 0) {
            // 获得口红的锁
            // 如果把锁写在锁外面，就能避免死锁
            // 写在外面就能释放资源了
            synchronized (lipstick) {
                System.out.println(this.name + "获得口红");
                Thread.sleep(1000);

                // 获得镜子的锁
                synchronized (mirror) {
                    System.out.println(this.name + "获得镜子");
                }
            }
        } else {
            // 获得镜子的锁
            synchronized (mirror) {
                System.out.println(this.name + "获得镜子");
                Thread.sleep(1000);

                // 获得口红的锁
                synchronized (lipstick) {
                    System.out.println(this.name + "获得口红");
                }
            }
        }
    }
}
