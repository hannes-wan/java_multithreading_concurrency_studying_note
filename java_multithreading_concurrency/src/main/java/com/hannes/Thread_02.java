package com.hannes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@NoArgsConstructor
@AllArgsConstructor
public class Thread_02 extends Thread{
    private String url;
    private String name;

    @Override
    public void run(){
        Downloader_02 downloader = new Downloader_02();
        downloader.downloader(url,name);
        System.out.println("下载了：" + name);
    }

    public static void main(String[] args) {
        Thread_02 t1 = new Thread_02("https://img9.doubanio.com/view/group_topic/l/public/p289640904.jpg","1.jpg");
        Thread_02 t2 = new Thread_02("https://img1.doubanio.com/view/group_topic/l/public/p289640909.jpg","2.jpg");
        Thread_02 t3 = new Thread_02("https://img3.doubanio.com/view/group_topic/l/public/p289640910.jpg","3.jpg");

        t1.start();
        t2.start();
        t3.start();
    }
}

// 下载器
class Downloader_02 {
    public void downloader(String url, String name){
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常");
        }
    }
}
