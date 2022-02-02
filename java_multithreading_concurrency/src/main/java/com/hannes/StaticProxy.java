package com.hannes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


// 静态代理模式总结：
// 真实对象和代理对象都要实现同一个接口
// 代理对象要代理真实角色

// 好处：
// 代理对象可以做很多真实对象做不了的事情
// 真实对象专注做自己的事情
public class StaticProxy {

    public static void main(String[] args) {

        /*
         把Runnable接口的唯一的run方法写成sout，传进Thread
         Runnable run = ()-> System.out.println("I love you");
         new Thread( run ).start();
        */

        new Thread( ()-> System.out.println("I love you") ).start();
        new WeddingCompany(new You()).HappyMarry();

        /*
        WeddingCompany weddingCompany = new WeddingCompany(new You());
        weddingCompany.HappyMarry();
         */
    }
}

interface Marry {
    void HappyMarry();
}

// 真实角色，你去结婚
class You implements Marry{

    @Override
    public void HappyMarry(){
        System.out.println("小宛今天结婚了");
    }
}

// 代理角色，帮助你结婚
@NoArgsConstructor
@AllArgsConstructor
class WeddingCompany implements Marry {

    // 代理谁 -> 真实目标角色
    private Marry target;

    @Override
    public void HappyMarry() {
        before();

        // 这是真实角色
        this.target.HappyMarry();

        after();
    }

    private void before(){
        System.out.println("布置现场");
    }

    private void after(){
        System.out.println("收尾款");
    }
}

