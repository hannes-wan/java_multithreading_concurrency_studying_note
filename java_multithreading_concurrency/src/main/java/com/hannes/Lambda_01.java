package com.hannes;

public class Lambda_01 {

    // 静态内部类
    static class Like2 implements ILike{
        @Override
        public void print() {
            System.out.println("I like Lambda 2");
        }
    }

    public static void main(String[] args) {
        ILike like = new Like1();
        like.print();

        like = new Like2();
        like.print();

        // 局部内部类
        class Like3 implements ILike{
            @Override
            public void print() {
                System.out.println("I like Lambda 3");
            }
        }

        like = new Like3();
        like.print();

        // 匿名内部类
        // 没有类的名称，必须借助接口或者父类
        like = new ILike() {
            @Override
            public void print() {
                System.out.println("I like Lambda 4");
            }
        };
        like.print();

        // 用Lambda简化
        // Lambda是用来实现函数式接口的好方法
        like = () -> System.out.println("I like Lambda 5");
        like.print();
    }
}

// 定义一个函数式接口
interface ILike {
    void print();
}

// 实现类
class Like1 implements ILike {

    @Override
    public void print() {
        System.out.println("I like Lambda 1");
    }
}
