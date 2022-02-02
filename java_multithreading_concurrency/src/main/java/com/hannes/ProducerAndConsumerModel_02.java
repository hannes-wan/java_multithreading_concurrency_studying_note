package com.hannes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProducerAndConsumerModel_02 {
    public static void main(String[] args) {
        ArtWorks artWorks = new ArtWorks();
        new Thread(new Performer(artWorks)).start();
        new Thread(new Audience(artWorks)).start();
    }
}

// 生产者：演员
@Data
@NoArgsConstructor
@AllArgsConstructor
class Performer implements Runnable {

    ArtWorks artWorks;

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i%2 == 0) this.artWorks.play("快乐大本营");
            else this.artWorks.play("天天向上");
        }
    }
}

// 消费者：观众
@Data
@NoArgsConstructor
@AllArgsConstructor
class Audience implements Runnable {

    ArtWorks artWorks;

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            artWorks.watch();
        }
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
// 产品：节目
class ArtWorks {

    // 演员表演，观众等待
    // 观众观看，演员等待

    private String artWorkName; // 节目名称
    private boolean flag = true;

    public synchronized void play(String artWorkName) {
        if (!this.isFlag()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("演员表演了" + artWorkName);

        // 通知观众观看
        this.notifyAll();   // 通知唤醒
        this.setArtWorkName(artWorkName);
        this.setFlag(!isFlag());
    }

    public synchronized void watch() {
        if (this.isFlag()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("观看了" + this.getArtWorkName());

        // 通知演员表演
        this.notifyAll();
        this.setFlag(!isFlag());
    }
}