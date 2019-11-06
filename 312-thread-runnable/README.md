# 细说多线程之Thread VS Runnable

> https://www.imooc.com/learn/312
>
> 简介：本课程将带领大家更加深入地学习Java多线程的机制，深入理解创建线程的两种方式Thread和Runnable之间的差异，掌握线程的生命周期和守护线程的概念，以及如何使用jstack生成线程快照。

第1章 课前准备
---

> 了解学习本课程前的必备知识

[《深入浅出Java多线程》](https://www.imooc.com/learn/202)

>比较Thread和Runnable这两种线程创建的方式，需要知道Thread和Runnable的基本创建方式。

课程目标和学习内容
- 线程创建的两种方式比较
- 线程的生命周期
- 线程的守护神：守护线程

第2章 `Thread` VS `Runnable`
---

> 本章首先回顾了线程创建的两种方式，而后通过一个程序案例，深入比较了这两种创建线程的方式。

### 2-1 回顾线程创建的两种方式
1. 继承`Thread`类
    ```java
    class MyThread extents Thread {
        // ...
        @Override
        public void run() {
            // ...
        }
    }
    
    MyThread mt = new MyThread(); // 创建线程
    mt.start(); // 启动线程
    ```
2. 实现`Runnable`接口
    ```java
    class MyRunnable implements Runnable {
        // ...
        @Override
        public void run() {
            // ...
        }
    }
    
    MyRunnable mr = new MyRunnable();
    Thread td = new Thread(mr); // 创建线程
    td.start(); // 启动线程
    ```
### 2-2 应用`Thread`模拟卖票
`Thread`与`Runnable`的区别：
1. `Runnable`方式可以避免`Thread`方式由于`Java`单继承特性带来的缺陷

  单继承：Java语言中只能继承一个父类，而可以实现多个接口

2. `Runnable`的实例对象可以被多个线程（`Thread`实例）共享，适合于多个线程处理**同一资源**



针对Thread和Runnable对于同一资源处理：

```java
class MyThread extends Thread{}
class MyRunnable implements Runnable{}

public static void main(String[] args) {
    // 注意：此时t1,t2,t3是堆内存中实例出的三个对象，对于它们所有属性也独立的
    MyThread t1 = new MyThread();
    MyThread t2 = new MyThread();
    MyThread t3 = new MyThread();
    
    // 注意：t1,t2,t3虽然是堆中的三个不同对象，但是此时的r是一个，所以三个线程引用r中的资源也是共有的
    MyRunnable r = new MyRunnable();
    Thread t1 = new Thread(r);
    Thread t2 = new Thread(r);
    Thread t3 = new Thread(r);
}
```
结论：Runnable可以实现资源共享，而Thread资源独立



注：

- `a--`、`a++`、`++a`、`--a`、`a+=1`等为非原子操作（non-atomic operation），需要加锁

第3章 线程的生命周期和守护线程
---

> 本章归纳总结了线程的生命周期，讲解了守护线程，并演示了如何使用jstack生成线程快照

### 3-1 线程的生命周期
<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8odd59oopj30zk0k0gmb.jpg" style="zoom:50%;" />

1. 创建：新建一个线程对象，如`Thread thd = new Thread()`
2. 就绪：创建了线程对象后，调用了线程的`start()`方法（注意：此时线程只是进入了线程队列，等待获取`CPU`服务，具备了运行的条件，但并不一定已经开始运行了）
3. 运行：处于就绪状态的线程，一旦获取了`CPU`资源，便进入到运行状态，开始执行`run()`方法里面的逻辑
4. 终止：线程的`run()`方法执行完毕，或者调用线程的`stop()`方法（该方法已经废弃），线程便进入终止状态
5. 阻塞：一个正在执行的线程在某种情况下，由于某种原因而暂时让出了`CPU`资源，暂停了自己的执行，便进入了阻塞状态，如调用了`sleep()`、`wait()`、`yield()`等方法
### 3-2 守护线程理论知识
`Java`线程分两类：
1. 用户线程：运行在前台，执行具体任务

  例如：程序的主线程，连接网络的子线程等

2. 守护线程：运行在后台，为其他前台线程服务

  特点：一旦所有用户线程都结束运行，守护线程会随`JVM`一起结束工作

  应用：

  1. 数据库连接池中的监测线程
  2. `JVM`虚拟机启动后的监测线程
     - 垃圾回收线程
     - 监测内存使用的线程
     - 监测程序锁持有的线程  



如何设置守护线程：
- 可以通过调用`Thread`类的`setDaemon(true)`方法来设置当前的线程为守护线程  

**注意事项**：

- `setDaemon(true)`必须在`start()`方法之前调用，否则会抛出`IllegalThreadStateException`异常
- 在守护线程中产生的新线程也是守护线程
- 不是所有的任务都可以分配给守护线程来执行，比如读写操作或者计算逻辑
### 3-3 守护线程代码示例
```java
public static void main(String[] args) {
    System.out.println("进入主线程" + Thread.currentThread().getName());
    DaemonThread daemonThread = new DaemonThread();
    Thread thread = new Thread(daemonThread);
    thread.setDaemon(true);//设置为守护线程
    thread.start();
    Scanner scanner = new Scanner(System.in);
    scanner.next();
    // 主线程为唯一用户线程
    // thread为守护线程
    // 主线程退出导致数据的不完整性
    System.out.println("退出主线程" + Thread.currentThread().getName());
}
```

thread线程运行完成后程序不会立刻终止，此时thread守护线程需等待main主用户线程scanner之后才会结束运行。

### 3-4 使用jstack生成线程快照
位置：`jdk/bin/jstack.exe`

|程序|工具作用|
|:---:|:---:|
|jstack|生成JVM线程快照|
|jstat.exe|监测虚拟机的运行状态|
|jconsole.exe|Java监视和管理控制台GUI|
|jvisualvm.exe|界面化工局|
|jmap.exe|生成堆快照|
虚拟机的运行状态：内存的使用情况、类加载情况、锁持有的情况



`jstack`

- 作用：生成`JVM`当前时刻线程的快照（`threaddump`，即当前进程中所有线程的信息）
- 目的：帮助定位程序问题出现的原因(查找一些程序问题)，如长时间停顿、`CPU`占用率过高、死锁、阻塞等；看出哪些是守护线程，哪些是用户线程
- 使用：`jstack -l <pid>`----其中`<pid>`可以通过任务栏管理器查看
  1. 可以通过线程名后是否有`daemon`判断是否为守护线程，有-是、无-否
  2. 查看线程的状态`java.lang.Thread.State`帮助定位程序出现的一系列问题(死锁、阻塞)
  3. `tid`、`nid`可以帮助查找`CPU`占有率很高的线程
  4. `prio`代表优先级



线程状态：  
- `New`尚未启动的线程（创建）；
- `Runnable`在`Java`虚拟机中执行的线程，即将或正在运行的线程（就绪，运行）；
- `Terminated`已退出的线程（终止）；
- `Blocked`、`Waiting`、`Timed_Waiting`对应阻塞状态。
    1. `Blocked`：等待监视器锁定的线程，如被`synchronized`阻塞的线程。
    2. `Waiting`：无限期等待另一个线程执行特定操作的线程，调用`join`方法时。
    3. `Timed_Waiting`：等待另一个线程在指定等待时间内执行操作的线程，如`sleep`方法。
    
第4章 课程总结
---

> 课程总结

建议：使用实现`Runnable`接口的方式创建线程

补充：

1. 程序中的同一资源指的是同一个`Runnable`对象

  如果把其他类的对象也作为共享资源，则`Thread`和`Runnable`都是可以用来处理同一资源

2. 安全的卖票程序中需要加入同步（`synchronized`）



Thread vs Runnable，线程的生命周期，守护线程，jstack生成线程快照