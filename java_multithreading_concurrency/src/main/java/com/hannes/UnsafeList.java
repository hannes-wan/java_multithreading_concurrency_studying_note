package com.hannes;

import java.util.ArrayList;
import java.util.List;

// 插入同时发生时，可能会两个插入只发生一次，会导致最后的数 < 10000
// 如果针对向list add方法加锁锁住list对象，就安全了
public class UnsafeList {
    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                // 锁住这个list对象就行
                // 这样写也相当于在run方法里写同步块
                synchronized (list) {
                    list.add(Thread.currentThread().getName());
                }
            }).start();
        }

        Thread.sleep(1000);
        System.out.println(list.size());
    }
}
