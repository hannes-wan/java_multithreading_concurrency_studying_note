package com.hannes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 测试：生产者消费者模型
// 利用缓冲区解决 ----> 缓冲区
public class ProducerAndConsumerModel_01 {
    public static void main(String[] args) {
        SynContainer synContainer = new SynContainer();
        new Thread(new Producer(synContainer)).start();
        new Thread(new Consumer(synContainer)).start();

    }
}

@AllArgsConstructor
@NoArgsConstructor
class Producer implements Runnable{

    private SynContainer container;

    @Override
    // 生产
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("生产了"+i+"只鸡");
            container.push(new Chicken(i));
        }
    }
}

@AllArgsConstructor
@NoArgsConstructor
class Consumer implements Runnable {

    private SynContainer container;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费了第"+container.pop().getId()+"只鸡");
        }
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Chicken {
    private int id;
}

class SynContainer {

    // 一个容器大小
    Chicken[] chickens = new Chicken[10];
    private int count = 0;

    // 生产者放入产品
    public synchronized void push (Chicken chicken) {

        // 如果容器满了，需要等待消费者消费
        if (count == chickens.length) {
            // 通知消费者消费，等待生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果没有满，丢入产品
        chickens[count] = chicken;
        count++;

        // 通知消费者消费
        // notifyAll 唤醒进入这个对象的线程
        this.notifyAll();

    }

    // 消费者消费产品
    public synchronized Chicken pop() {
        // 判断能否消费
        if (count == 0) {
            // 等待生产者生产，消费者等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果可以消费
        count--;
        Chicken chicken = chickens[count];

        // 吃完了，通知生产者生产
        // notifyAll 唤醒进入这个对象的线程
        this.notifyAll();

        return chicken;
    }
}
