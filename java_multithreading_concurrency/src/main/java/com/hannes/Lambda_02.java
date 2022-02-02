package com.hannes;

// Lambda表达式其实代表的是只有一个方法的类
public class Lambda_02 {
    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                System.out.println("我在看代码" + i);
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                System.out.println("我在吃饭" + i);
            }
        }).start();

        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习多线程" + i);
        }
    }
}
