深入浅出Java多线程
===
第1章 多线程背景知识介绍
---
进程  
1. 进程是程序（任务）的执行过程，是动态性的。  
放在磁盘文件中的文件并不是进程，只有在运行状态的才可称之为进程。
2. 持有资源（共享内存，共享文件）和线程。进程是资源和线程的载体

线程
1. 线程是系统中最小的执行单元
2. 同一进程中有多个线程
3. 线程共享进程的资源

线程的交互：多个线程需要正确的通信才能进行工作。  
交互方式有互斥和同步两种。
- 互斥是当一个线程正在运行时其他的线程只能等待，只有在当前线程完成后才可以运行；
- 同步是两个或多个线程同时进行运行。

第2章 Java 线程初体验
---
`Java`对线程的支持体现在`java.lang`的包下的`class thread`和`interface Runnable`  
共通的方法是`public void run()`，为我们提供线程实际工作执行的代码

**Thread常用方法：**
1. 线程的创建  
    ```
    Thread();
    Thread(String name);
    Thread(Runnable target);
    Thread(Runnable target,String name);
    ```
2. 线程的方法  
    ```
    void start();//启动线程 Java虚拟机调用该线程的run()方法。
    static void sleep(long millis);//线程休眠(暂停执行) 时间可以精确到纳秒，使其他线程等待当前线程终止
                                  //此操作受到系统计时器和调度程序精度和准确性的影响。线程不丢失任何监视器的所属权。
    static void sleep(long millis, int nanos);
    void join();//使其他线程等待当前线程终止,指明其他其他线程必须等待当前结束后才能运行
    void join(long millis);//参数表示其他线程最长等待的时间
    void join(long millis,int nanos);
    static void yield();//当前运行线程释放处理器资源
    ```
3. 获取线程引用  
    ```
    static Thread currentThread();//返回当前运行的线程引用（静态方法，返回Thread类型）
    ```  
4. 线程的信息  
    `.getName()` 返回线程的名字  
    `.setName(String Name)` 改变线程的名字  

**`Thread`类 和 `Runnable`接口的区别与联系：**  
1. `Runnable`只是一个接口，它里面只有一个`run()`方法，没有`start()`方法， 所以即使实现了`Runnable`接口，那也无法启动线程，必须依托其他类。  
2. `Thread`类，有一个构造方法，参数是`Runnable`对象，也就是说可以通过`Thread`类来启动`Runnable`实现的多线程。  

- 在同一时间内同一处理器或同一个核只能运行一条线程，当一条线程休眠之后，另一条线程才获得了我们处理器的时间

- 一个`.java`文件中可以有多个类，但是只能有一个`public`修饰的类

- `Runnable`接口之中没有`getName()`方法，我们可以使用`Thread`的静态方法`currentThreat().getName();`获取该对象的名字。

- 若实现接口通过`Thread.currentThread().getName()`方法获取当前线程名称，继承`Thread`，则`getName()`方法获取当前线程。

### 实例：隋唐演义
> 历史是人民群众创建的，英雄可以推动历史的发展  

**1. 演员简介：**  
&emsp;&emsp;军队 `ArmyRunnable`  
&emsp;&emsp;英雄 `KeyPersonThread`  
&emsp;&emsp;舞台 `Stage`  
```java
//军队线程 模拟作战双方行为
public class ArmyRunnable implements Runnable {
    ArmyRunnable armyTaskOfSuiDynasty = new ArmyRunnable();
    ArmyRunnable armyTaskOfArmyOfRevolt = new ArmyRunnable();
    
    //使用Runnable 接口创建线程
    Thread armyOfSuiDynasty = new Thread(armyTaskOfSuiDynasty,"隋军");
    Thread armyOfRevolt = new Thread(armyTaskOfarmyOfRevolt,"农民起义军");
}
```
**2. 军队：**  
1. 加入`join()`是为了让舞台线程最后停止。  
    如果不加有可能舞台线程结束，军队线程还未停止，就好比导演喊停，演员还在演！  
    可以在`join()`后面加入测试语句`System.out.println("舞台结束!");`，然后去掉或者保留`join`观察效果。
2. `volatile`关键字保证了线程可以正确地读取其他线程写入的值。  
    如果不写成`volatile`，由于可见性的问题，当前线程有可能不能读到这个值 //可见性JMM（JAVA内存模型）happens-before原则、可见性原则
    用`volatile`修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的值
3. `Thread.yield();`//当前线程让出处理器时间，公平竞争  

**3. 关键先生：**  
1. `start()`开始线程。  
2. `sleep()`方法可以使线程休眠。  
3. `yield()`让出当前线程的执行权限，随机选择线程执行。  
4. `join()`优先执行该线程，其他线程(包括主线程main)都暂停。  

第3章 Java 线程的正确停止
---
1. 错误的停止线程方法：  
    1. `stop()`方法会导致线程戛然而止，我们不清楚线程完成了什么，没完成什么，也没有时间来完成对应的线程运行的清理工作。
    2. `interrupt()`方法只能设置`interrupt`标志位（且在线程阻塞情况下，标志位会被清除，更无法设置中断标志位），无法停止线程  
        > 1.`Interrupted` 相当于 旗标 (isInterrupted ==>true false)  
         2.线程中使用`sleep`等方法造成线程堵塞时， 标志位被清除，`Interrupted`方式无效 抛出`InterruptedException`（中断异常）  
         3.`interrupt`方法是用来唤醒被阻塞的线程的，如果线程使用了`wait`, `sleep`, `join`方法，那么线程就会进入阻塞状态，使用`interrupt`方法会终止这种状态，此时线程的`sleep`等方法下面的`catch`语句就会接收到这个打断，从而启动线程，继续做要做的事情。如果线程在阻塞之前使用`interrupt`方法，那么在阻塞的时候，会抛出异常，并把中断标志位设置成`false`。
2. 正确的停止线程方法：  
    在线程执行中设置状态标识，通过控制此状态标识，来控制线程的正常完整的结束。  
    另外，`volatile`关键字是保证所有其他线程里的变量的赋值都能同步到当前内存里变量的值。  
    这样做的好处是，线程有机会使得一个业务步骤被完整地执行，在执行完业务步骤后有充分的时间去做代码的清理工作，使得线程代码在实际中更安全
    
第4章 线程交互
---
**争用条件：**
1. 定义：当多个线程同时共享访问同一数据（内存区域）时，每个线程都尝试操作该数据，从而导致数据被破坏（corrupted)，这种现象称为争用条件
2. 原因：每个线程在操作数据时，会先将数据初值读【取到自己获得的内存中】，然后在内存中进行运算后，重新赋值到数据。 
3. 争用条件：线程1在还【未重新将值赋回去时】，线程1阻塞，线程2开始访问该数据，然后进行了修改，之后被阻塞的线程1再获得资源，而将之前计算的值覆盖掉线程2所修改的值，就出现了数据丢失情况

**互斥与同步：**
1. 互斥  
同一时间，只能有一个线程访问关键数据或临界区
2. 同步  
是一种通信机制，一个线程操作完成后，以某种方式通知其他线程
3. 实现方法
    > 1. 【互斥】构建锁对象`private final Object lockObj = new Object();`
    > 2. 通过`synchronized(lockObj){  互斥的代码块  }`加锁操作，加锁操作会开销系统资源，降低效率
    > 3. 在某线程的条件不满足任务时，使用`lockObj.wait();`对线程进行阻挡，防止其继续竞争`CPU`资源，滞留在`wait set`中，等待唤醒，【唤醒后继续完成业务】
    > 4. 【同步】在某一代码正确执行完业务后，通过`lockObj.notifyAll()`唤醒所有在`lockObj`对象等待的线程

互斥：关键数据在一个时间被一个线程使用。  
互斥的实现：`private final Object lockObj = new Object()`,`synchronized(intrinsic lock)`  

同步：两个线程之间的一种交互的操作（一个线程发出消息另外一个线程响应）  
同步的实现：`wait();`、`notify();`、`notifyAll();`，都是属于`Java`中的`Object`对象的成员函数  
**注：** 调用`wait();`和`notifyAll();`方法使线程进入等待或者唤醒不是在同一个线程的同一次操作中执行的，当操作结束，唤醒了所有的等待线程之后，线程又将有着公平的机会竞争CPU资源。  

`wait set`类似于线程的休息室，访问共享数据的代码称为临界区`critical section`。  
一个线程获取锁，然后进入临界区,发现某些条件不满足，然后调用锁对象上的`wait()`方法，然后线程释放掉锁资源，进入锁对象上的`wait set`。其他线程可以获取所资源，然后执行，完了以后调用`notify()/notifyAll()`，通知锁对象上的等待线程。  

第5章 进阶展望
---
要点回顾：
1. 线程如何创建以及线程的基本概念
2. 可见性以及`volatile`关键字
3. 争用条件
4. 如何通过`synchronized`让线程的互斥
5. 如何通过`wait/notify`、`notifyAll`让线程同步

**展望：**
1. Java Memory Mode:  
    - JMM描述了java线程如何通过内存进行交互
    - 了解happens-before
    - synchronized,volatile & final
2. Locks % Condition：
    - 锁机制和等待条件的高层实现
    - java.util.concurrent.locks
3. 线程安全性
    - 原子性与可见性
    - java.util.concurrent.atomic
    - synchronized & volatile
    - DeadLocks(死锁)
4. 多线程常用的交互模型
    - Producer-Consumer模型
    - Read-Write Lock模型
    - Future模型
    - Worker Thread模型
    - 了解哪些类实现了这些模型可以用来直接使用
5. Java5中并发编程工具：
    - java.util.concurrent
    - 线程池ExecutorService
    - Callable & Future
    - BlockingQueue
6. 推荐书本
    - CoreJava
    - Java Concurrency In Practice
    
**Java多线程特性：**
1. 原子性：`Java`内存模型只保证了基本读取和赋值是原子性操作，如果要实现更大范围操作的原子性，可以通过`synchronized`和`Lock`来实现。由于`synchronized`和`Lock`能够保证任一时刻只有一个线程执行该代码块，那么自然就不存在原子性问题了，从而保证了原子性。
2. 可见性：对于可见性，`Java`提供了`volatile`关键字来保证可见性。当一个共享变量被`volatile`修饰时，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值。而普通的共享变量不能保证可见性，因为普通共享变量被修改之后，什么时候被写入主存是不确定的，当其他线程去读取时，此时内存中可能还是原来的旧值，因此无法保证可见性。  
   另外，通过`synchronized`和`Lock`也能够保证可见性，`synchronized`和`Lock`能保证同一时刻只有一个线程获取锁然后执行同步代码，并且在释放锁之前会将对变量的修改刷新到主存当中。因此可以保证可见性。
3. 有序性：在`Java`内存模型中，允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性。    
    在`Java`里面，可以通过`volatile`关键字来保证一定的“有序性”。另外可以通过`synchronized`和`Lock`来保证有序性，很显然，`synchronized`和`Lock`保证每个时刻是有一个线程执行同步代码，相当于是让线程顺序执行同步代码，自然就保证了有序性。   
    另外，`Java`内存模型具备一些先天的“有序性”，即不需要通过任何手段就能够得到保证的有序性，这个通常也称为`happens-before`原则。如果两个操作的执行次序无法从`happens-before`原则推导出来，那么它们就不能保证它们的有序性，虚拟机可以随意地对它们进行重排序。