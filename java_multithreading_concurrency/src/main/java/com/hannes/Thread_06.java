package com.hannes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;

// 实现Callable接口
// Callable可以定义返回值
// Callable可以抛出异常

@NoArgsConstructor
@AllArgsConstructor
public class Thread_06 implements Callable<Boolean> {

    private String url;
    private String name;

    @Override
    public Boolean call() {
        Downloader_06 downloader = new Downloader_06();
        downloader.downloader(url,name);
        System.out.println("下载了：" + name);
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread_06 t1 = new Thread_06("https://img9.doubanio.com/view/group_topic/l/public/p289640904.jpg","1.jpg");
        Thread_06 t2 = new Thread_06("https://img1.doubanio.com/view/group_topic/l/public/p289640909.jpg","2.jpg");
        Thread_06 t3 = new Thread_06("https://img3.doubanio.com/view/group_topic/l/public/p289640910.jpg","3.jpg");

        // 创建执行服务
        ExecutorService ser = Executors.newFixedThreadPool(3);

        // 提交执行
        Future<Boolean> r1 = ser.submit(t1);
        Future<Boolean> r2 = ser.submit(t2);
        Future<Boolean> r3 = ser.submit(t3);

        // 获取结果
        boolean rs1 = r1.get();
        boolean rs2 = r2.get();
        boolean rs3 = r3.get();

        System.out.println(rs1);
        System.out.println(rs2);
        System.out.println(rs3);

        // 关闭服务
        ser.shutdownNow();
    }
}

// 下载器
class Downloader_06 {
    public void downloader(String url, String name){
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常");
        }
    }
}
