# Java多线程
## 线程、进程、多线程
- 普通方法调用和多线程
![531642586115_.pic](https://s2.loli.net/2022/02/02/68ypuz9DmaXWAGh.jpg)

- 线程和进程
![541642586197_.pic](https://s2.loli.net/2022/02/02/1FpzHxdOlocKvsT.jpg)

- 核心概念
![551642586268_.pic](https://s2.loli.net/2022/02/02/5Jj3UVHcXsGeBQf.jpg)

## 线程创建
- Tread、Runnable、Callable
    - 继承Tread类（重点）
    - 实现Runnable接口（重点）
    - 实现Callable（了解）

### 继承Thread
![561642587094_.pic](https://s2.loli.net/2022/02/02/vadLyPfnR1oX3Wc.jpg)

```java
public class Thread_01 extends Thread{
	
	@Override
	// run方法线程体
	public void run(){
		for (int i = 0; i < 20; i++) {
			System.out.println("我在看代码" + i);
		}
	}
	
	// main线程，主线程
	public static void main(String[] args) {
		
		Thread_01 thread_01 = new Thread_01();
		
		thread_01.start();
		
		for (int i = 0; i < 20; i++) {
			System.out.println("我在学习多线程" + i);
		}
	}
}
```
```java
@NoArgsConstructor
@AllArgsConstructor
public class Thread_02 extends Thread{
    private String url;
    private String name;

    @Override
    public void run(){
        Downloader downloader = new Downloader();
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
class Downloader {
    public void downloader(String url, String name){
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常");
        }
    }
}
```

### 实现Runnable接口
![561642587094_.pic](https://s2.loli.net/2022/02/02/vadLyPfnR1oX3Wc.jpg)

```java
/*
    实现runnable接口，重写run方法
    执行线程需丢入runnable接口实现类，调用start方法
 */

public class Thread_03 implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("我在看代码" + i);
        }
    }

    // main线程，主线程
    public static void main(String[] args) {
        Thread_03 thread_03 = new Thread_03();
        new Thread(thread_03).start();

        for (int i = 0; i < 20; i++) {
            System.out.println("我在学习多线程" + i);
        }
    }
}
```
![581642596684_.pic](https://s2.loli.net/2022/02/02/Vfvi2uA1DtNwmJF.jpg)

### 实现Callable接口
![591642599157_.pic](https://s2.loli.net/2022/02/02/rs6ynL3Yp2gSmk7.jpg)

```java
/ 实现Callable接口
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
```

## 静态代理
![601642656428_.pic](https://s2.loli.net/2022/02/02/JstHkeAP78aMDj9.jpg)

```java
// 静态代理模式总结：
// 真实对象和代理对象都要实现同一个接口
// 代理对象要代理真实角色

// 好处：
// 代理对象可以做很多真实对象做不了的事情
// 真实对象专注做自己的事情
public class StaticProxy {

    public static void main(String[] args) {

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
```

## Lambda表达式
- Lambda表达式代表的其实是一个函数式接口的实现**类**

![611642656655_.pic](https://s2.loli.net/2022/02/02/kiZtogxXJu3myvV.jpg)

![621642657419_.pic](https://s2.loli.net/2022/02/02/dJEmSokATV5t1vK.jpg)

![631642657509_.pic](https://s2.loli.net/2022/02/02/qiYb7tSr8jpW1cA.jpg)

```java
public class Lambda {

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
```

[https://www.runoob.com/java/java8-lambda-expressions.html](https://www.runoob.com/java/java8-lambda-expressions.html)

## 线程停止
![641642663686_.pic](https://s2.loli.net/2022/02/02/82RFkHma3YntENK.jpg)

![651642663730_.pic](https://s2.loli.net/2022/02/02/F1Cjwq7hHMaQyzT.jpg)

![661642663841_.pic](https://s2.loli.net/2022/02/02/R26CNAeg8FZMIlO.jpg)

![671642663887_.pic](https://s2.loli.net/2022/02/02/xfIm82AQFpgOlvP.jpg)

```stop
// 建议线程正常通知 -> 利用次数，不建议死循环
// 建议使用标志位 -> 设置一个标志位
// 不要使用stop或者destroy等过时或者JDK不建议使用的方法
public class Stop implements Runnable{

    // 设置一个标志位
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        for (; flag; i++)
            System.out.println("run...thread " + i);
    }

    public static void main(String[] args) {
        Stop stop = new Stop();
        new Thread(stop).start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("main" + i);
            if (i >= 900){
                // 调用stop方法切换标志位，让线程停止
                stop.stop();
            }
        }

    }

    public void stop(){
        flag = false;
    }
}
```

## 线程休眠
![681642664864_.pic](https://s2.loli.net/2022/02/02/kcVeqt2J4SuQsKZ.jpg)

```java
public class Sleep_02{

    public static void main(String[] args) {
        Date date = new Date(System.currentTimeMillis());

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(date));
                date = new Date(System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## 线程礼让
![691642666188_.pic](https://s2.loli.net/2022/02/02/itNLcCopP2mnEBy.jpg)

```java
// 测试礼让
// 礼让不一定成功，看CPU心情
public class Yield {
    public static void main(String[] args) {
        // 直接在这儿实现Runnable，省事儿
        Runnable run = () -> {
            System.out.println(Thread.currentThread().getName() + "线程开始执行");
            Thread.yield();
            System.out.println(Thread.currentThread().getName() + "线程停止执行");
        };

        new Thread(run, "a").start();
        new Thread(run, "b").start();
    }
}
```

## Join
![701642666603_.pic](https://s2.loli.net/2022/02/02/AJEFO9qWmIjwxH5.jpg)

## 线程状态观测
![711642667100_.pic](https://s2.loli.net/2022/02/02/Cu381xkqnOiTjwG.jpg)

```java
public class State {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("##########################");
        });

        Thread.State state = thread.getState();
        System.out.println(state);

        thread.start();

        // 观察启动后
        state = thread.getState();
        System.out.println(state);

        // Thread.State.TERMINATED 是线程终止的状态
        while (state != Thread.State.TERMINATED) {
            Thread.sleep(100);
            state = thread.getState();  // 更新线程状态
            System.out.println(state);
        }

    }
}
```

## 线程优先级
![721642676024_.pic](https://s2.loli.net/2022/02/02/ST7LC9yBEjwdh4n.jpg)

```java
// 优先级看CPU心情
// 只是意味着获得调度的概率低
public class Priority {
    public static void main(String[] args) {
        Runnable run = () -> System.out.println(Thread.currentThread().getName() + "-->" + Thread.currentThread().getPriority());
        Thread t1 = new Thread(run, "t1");
        Thread t2 = new Thread(run, "t2");
        Thread t3 = new Thread(run, "t3");
        Thread t4 = new Thread(run, "t4");
        Thread t5 = new Thread(run, "t5");

        t1.start();

        t2.setPriority(1);
        t2.start();

        t3.setPriority(4);
        t3.start();

        t4.setPriority(Thread.MAX_PRIORITY);
        t4.start();

        t5.setPriority(8);
        t5.start();
    }
}
```

## 守护进程
![741642680510_.pic](https://s2.loli.net/2022/02/02/a5VtE3OsTBeKqCL.jpg)

```java
// 守护线程实例
public class Daemon {
    public static void main(String[] args) {
        Runnable you = () -> {
            for (int i = 0; i < 80; i++) System.out.println("Smoke weed everyday");
            System.out.println("##### Goodbye world #####");
        };

        Runnable god = () -> {
            for(;;) System.out.println("God bless you");
        };

        // 设置上帝线程为守护线程
        Thread thread = new Thread(god);
        thread.setDaemon(true);
        thread.start();

        // 用户线程启动
        new Thread(you).start();
    }
}
```

## 线程同步
![771642681702_.pic](https://s2.loli.net/2022/02/02/LeuqPaS1FGgHnvl.jpg)

![781642685774_.pic](https://s2.loli.net/2022/02/02/DJSn6gfYodsmruK.jpg)

![791642685805_.pic](https://s2.loli.net/2022/02/02/ksb9XADeZMStnpw.jpg)

![801642686395_.pic](https://s2.loli.net/2022/02/02/ro52dZfPAugb4tB.jpg)

### 同步方法
![821642689215_.pic](https://s2.loli.net/2022/02/02/nWoApNUf1wDbdRG.jpg)

![811642689198_.pic](https://s2.loli.net/2022/02/02/4HyVF5IPMYtnf3A.jpg)

![831642689272_.pic](https://s2.loli.net/2022/02/02/LiJDGelcwom3Z8k.jpg)

```java
// 不安全的买票
// 线程不安全，有负数
// 锁住buy方法就安全了
public class UnsafeBuyTicket {
    public static void main(String[] args) {
        BuyTicket station = new BuyTicket();
        new Thread(station, "me").start();
        new Thread(station, "you").start();
        new Thread(station, "others").start();
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class BuyTicket implements Runnable {

    private int ticketNums = 100;
    boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            try {
                buy();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // synchronized 加锁
    private synchronized void buy() {
        if (ticketNums <= 0) {
            flag = false;
            return;
        }

        System.out.println(Thread.currentThread().getName() + "拿到" + ticketNums--);
    }
}
```

![841642689656_.pic](https://s2.loli.net/2022/02/02/65KpP4cE8obIuYg.jpg)

```java
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
```

```java
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
```

```java
// 测试JUC安全类型的集合
// 就能不用加锁了
public class JUC {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                list.add(Thread.currentThread().getName());
            }).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());

    }
}
```

## 死锁
![861642742194_.pic](https://s2.loli.net/2022/02/02/t1hZf4YagIGAKDL.jpg)

```java
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
```

## Lock 锁
![871642742948_.pic](https://s2.loli.net/2022/02/02/fSjVhHQgEmKTpwt.jpg)

- ReentrantLock（可重入锁）

```java
public class Lock {
    public static void main(String[] args) {
        TestLock testLock = new TestLock();

        new Thread(testLock,"thread1").start();
        new Thread(testLock,"thread2").start();
        new Thread(testLock,"thread3").start();
    }
}

class TestLock implements Runnable{

    int ticketNums = 10;

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {

        for(;ticketNums > 1; ticketNums --) {

            try {
                // 加锁
                lock.lock();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ---> " + ticketNums);

            } finally {
                // 解锁
                lock.unlock();
            }
        }
    }
}
```

- Synchronized和Lock的对比
![881642745640_.pic](https://s2.loli.net/2022/02/02/4vb3pwWh9arDO1j.jpg)

## 线程协作
### 生产者消费者
![891642749182_.pic](https://s2.loli.net/2022/02/02/eTDZtVY19AgICEQ.jpg)

![901642749254_.pic](https://s2.loli.net/2022/02/02/hf9vXu78R2Cg1xr.jpg)

![921642749346_.pic](https://s2.loli.net/2022/02/02/l1pgwoVjanqX3LT.jpg)

![931642749403_.pic](https://s2.loli.net/2022/02/02/RLCpE3T2jFrhc7B.jpg)

#### 管程法
![941642749459_.pic](https://s2.loli.net/2022/02/02/Yyu4htn1vjC8AUz.jpg)

```java
// 测试：生产者消费者模型
// 利用缓冲区解决 ----> 缓冲区
public class ProducerAndConsumerModel {
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
```

#### 信号灯法
![951642749530_.pic](https://s2.loli.net/2022/02/02/QhfFyvNz7Ss38pr.jpg)

```java
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
```

#### 线程通信-分析
![961642749582_.pic](https://s2.loli.net/2022/02/02/V9hRQjklWAGdowT.jpg)

## 线程池
![971642753809_.pic](https://s2.loli.net/2022/02/02/d9Mmx76LXhHSGUJ.jpg)

![981642753833_.pic](https://s2.loli.net/2022/02/02/TC7zoFkJrvxiZ2P.jpg)

```java
public class Pool {
    public static void main(String[] args) {
        // 创建服务，创建线程池
        // 传入的的池子的大小
        ExecutorService service = Executors.newFixedThreadPool(10);

        // 执行
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());

        // 关闭连接
        service.shutdown();
    }
}

class MyThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + i);
        }
    }
}
```

