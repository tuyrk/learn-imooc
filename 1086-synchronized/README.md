# Java高并发之魂：synchronized深度解析

> https://www.imooc.com/learn/1086
>
> 简介：高并发问题向来是Java程序员进阶的重点，也是面试的难点。想要打通高并发的奇经八脉，synchronized是你不得不趟过的坑，本课程从synchronized，从使用方法到底层原理源码，娓娓道来。还对常见面试题和更深层扩展方面的思考，做出了讲解。本课程由浅入深，适合各阶段工程师观看。悟空老师的实战课【Java并发核心知识精讲+Java内存模型+死锁】已经上线啦，传送门如下 https://coding.imooc.com/class/362.html

## 第1章 课程介绍

> 介绍了课程结构、要点、前置知识和开发环境。

### 1-1 课程介绍 (07:58)

前置知识

- 了解Java基本语法
- 了解多线程基本知识



章节介绍

1. Synchronized**简介**：作用、地位、不控制并发的后果
2. 两种用法：**对象锁**和**类锁**
3. 多线程访问同步方法的**7种**情况：是否static、Synchronized方法等
4. Synchronized的**性质**：可重入、不可中断
5. **原理**：加解锁原理、可重入原理、可见性原理
6. Synchronized的**缺陷**：效率低、不够灵活、无法预判是否成功获取到锁
7. 常见**面试**问题：使用注意点、如何选择Lock或Synchronized等
8. **思考题**：如何提高性能、JVM如何决定哪个线程获取锁等
9. 总结

开发环境

- JDK1.8
- IDEA 2018.2

学习路径：

- 实战课：Java并发核心知识体系精讲

  https://coding.imooc.com/class/362.html

## 第2章 Synchronized简介

> 让同学们对Synchronized关键字有整理概念，从官网解释引出通俗解释，便于理解。从Synchronized关键字的地位说明该关键字的重要性。代码演示不用并发手段会带来的问题，吸引同学们带着疑问继续学习。分享IDEA的调试技巧，便于同学们实际操作。

- 作用：官方解释、通俗易懂的解释
- 地位
- 不使用并发手段的后果

### 2-1 synchronized的作用 (03:11)

官方解释

> Synchronized methods enable a simple strategy for preventing thread interference and memory consistency errors: if an object is visable to more than one thread, all reads or wirtes or that object's variables are done through synchronized methods 

翻译

> 同步方法支持一种简单的策略来防止线程干扰和内存一致性错误：如果一个对象对多个线程可见，则对该对象变量的所有读取或写入都是通过同步方法完成的



一句话说出Synchronized的作用

> 能够保证在同一时刻最多只有一个线程执行该段代码，以达到保证并发安全的效果

### 2-2 synchronized的地位 (00:51)

- Synchronized是Java的关键字，被Java语言原生支持
- Synchronized是最基本的同步互斥手段
- Synchronized是并发编程中的元老级角色，是并发编程的必学内容

### 2-3 不用并发手段的后果预警 (01:06)

代码实战：两个线程同时`a++`，最后结果会比预计的少。

### 2-4 后果的代码演示和原因分析 (07:23)

```java
public class DisappearRequest1 implements Runnable {
    private static DisappearRequest1 instance = new DisappearRequest1();
    private static int a = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println("a = " + a);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            a++;
        }
    }
}
```

原因：

> `count++`，它看上去只是一个操作，实际上包含了三个动作（非原子操作）：
>
> 1. 读取count
> 2. 将count加1
> 3. 将count的值写入到内存中
>
> 非原子操作还有：`a--`、`a+=1`、`a-=1`等

## 第3章 Synchronized的两种用法（对象锁和类锁）

> 对使用方法进行系统分类。涵盖了所有该关键字会用到的情况。

### 3-1 Synchronized的两种用法介绍 (02:11)

1. 对象锁：3-2

   > 包括方法锁（默认锁对象为this当前实例对象）和同步代码块锁（自己指定锁对象）

2. 类锁：3-7

   > 指synchronized修饰静态的方法或指定锁为class对象

### 3-2 对象锁的形式1-同步代码块 (13:07)

第一个用法：对象锁

- 代码块形式：手动指定锁对象
- 方法锁形式：synchronized修饰普通方法，锁对象默认为this



> 代码块形式：手动指定锁对象

```java
private Object lock1 = new Object();
private Object lock2 = new Object();

@Override
public void run() {
    synchronized (lock1) {
        System.out.println("我是对象锁的代码块形式-lock1。我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "，lock1-运行结束。");
    }

    synchronized (lock2) {
        System.out.println("我是对象锁的代码块形式-lock2。我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "，lock2-运行结束。");
    }
}
```

结论：当有多个synchronized同步代码块时，如果使用this为锁对象（即同一个锁对象）则只能运行其中的一个同步代码块，不能与其他同步代码块并行运行。

注：当业务复杂时，锁对象也会变得非常复杂。JDK提供了相应非常完善的工具类，例如：JUC包下的`CountDownLatch`倒计时,门闩、`CyclicBarrier`、`Semaphore`信号量。

### 3-3 调试技巧-看线程生命周期 (03:28)

在调试时查看当前线程的状态：

打好断点=>使用Debug启动程序=>当运行到断点时,右击断点出现图1

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8pl2tiw58j30nk0aiq3q.jpg" alt="image-20191107170337910" style="zoom:40%;" alt="图1" />

`All`：暂停所有线程，包括JVM

`Thread`：暂停当前线程



Debug=>Debugger=>Frames=>Thread-0=>run,Thread=>Evaluate Expression

执行：`this.getState()`

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8plcqxdrij30hy0jymz1.jpg" style="zoom:33%;" alt="图2"/>

### 3-4 对象锁的形式2-普通方法锁 (03:58)

> 方法锁形式：synchronized修饰普通方法，锁对象默认为this

```java
public synchronized void method() throws InterruptedException {
    System.out.println("我是对象锁的方法修饰符形式，我叫" + Thread.currentThread().getName());
    Thread.sleep(3000);
    System.out.println(Thread.currentThread().getName() + "，运行结束");
}
```

### 3-5 类锁的概念 (04:54)

第二个用法：类锁

- 概念（★重要）：Java类可能有很多个对象，但是只有一个Class对象
- 形式1：synchronized加载static静态方法上
- 形式2：`synchronized(*.class)`代码块



概念：

- 只有一个Class对象：Java类可能会有很多个对象，但是只有1个Class对象。特殊的对象。
- 本质：所谓的类锁，不过是Class对象的锁而已。
- 用法和效果：类锁只能在同一时刻被一个对象拥有。

类锁就是使用特殊对象锁（Class对象）作为同步互斥锁，因为其只有一个，所以不同的实例会互斥，在同一时刻只有一个线程可以访问该类实例中被类锁锁住的方法。

### 3-6 类锁的形式1-静态方法锁 (03:52)

> 形式1：synchronized加载static静态方法上

两个线程为不同的实例：

```java
Thread t1 = new Thread(instance1);
Thread t2 = new Thread(instance2);
```

```java
public static synchronized void method() throws InterruptedException {
    System.out.println("我是类锁的静态方法形式，我叫" + Thread.currentThread().getName());
    Thread.sleep(3000);
    System.out.println(Thread.currentThread().getName() + "，运行结束");
}
```

### 3-7 类锁的形式2--.class (05:08)

> 形式2：`synchronized(*.class)`代码块

两个线程为不同的实例：

```java
Thread t1 = new Thread(instance1);
Thread t2 = new Thread(instance2);
```

```java
public void method() throws InterruptedException {
    synchronized (SynchronizedClassClass5.class) {
        System.out.println("我是类锁的 synchronized(*.class) 形式，我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "，运行结束");
    }
}
```

### 3-8 消失的请求解决方案 (04:25)

解决方案1：对象锁-普通方法形式

```java
@Override
public synchronized void run() {
    for (int i = 0; i < 100000; i++) {
        a++;
    }
}
```

解决方案2：对象锁-同步代码块形式

```java
@Override
public void run() {
    for (int i = 0; i < 100000; i++) {
        synchronized (this) {
            a++;
        }
    }
}

```

解决方案3：类锁-*.class形式

```java
@Override
public void run() {
    for (int i = 0; i < 100000; i++) {
        synchronized (DisappearRequest1.class) {
            a++;
        }
    }
}

```

解决方案4：类锁-静态方案形式

```java
@Override
public void run() {
    method();
}

public static synchronized void method() {
    for (int i = 0; i < 100000; i++) {
        a++;
    }
}

```

## 第4章 多线程访问同步方法的7种具体情况

> ★面试常考
>
> 除了普通用法外，对各种情况进行讲解，让同学们有整体思维，并且理解原理。

### 4-1 七种常见情况之123 (06:38)

多线程访问同步方法的7种情况：

1. 两个线程同时访问一个对象的同步方法
2. 两个线程访问的是两个对象的同步方法
3. 两个线程访问的是synchronized的静态方法
4. 同时访问同步和非同步方法
5. 访问同一个对象的不同的普通同步方法
6. 同时访问静态synchronized个非静态synchronized方法
7. 方法抛出异常后，会释放锁



1. 两个线程同时访问一个对象的同步方法

   线程同步。`SynchronizedObjectMethod3`

   对象锁的普通方法形式。this锁

2. 两个线程访问的是两个对象的同步方法

   线程异步。

3. 两个线程访问的是synchronized的静态方法

   线程同步。`SynchronizedClassStatic4`

### 4-2 七种常见情况之4和5 (08:21)

4. 同时访问同步和非同步方法

   线程异步。`SynchronizedYesAndNo6`

   非同步方法不受到影响

5. 访问同一个对象的不同的普通同步方法

   线程同步。`SynchronizedDifferentMethod7`

### 4-3 七种常见情况之6和7 (11:25)

6. 同时访问静态synchronized个非静态synchronized方法

   线程异步。`SynchronizedStaticAndNormal8`

   `this锁`与`*.class锁`不是同一把锁

7. 方法抛出异常后，会释放锁

   `SynchronizedException9`

   synchronized与Lock接口：

   - 在Lock接口中，只要是没有显式手动释放锁，即便抛出异常Lock也是不会释放锁的。
   - synchronized一旦抛出异常会自动释放锁。

### 4-4 7种情况总结 (03:00)

3点核心思想：

1. 一把锁只能同时被一个线程获取，没有拿到锁的线程必须等待（对应第1、5种情况）

2. 每个实例都对应有自己的一把锁(即this锁)，不同实例之间互不影响；（对应第2、4、6种情况）

   例外：锁对象是*.class以及synchronized修饰的事static方法的时候，所有对象公用同一把锁(即类锁)（对应第3种情况）

3. 无论是方法正常执行完毕或者方法抛出异常，都会释放锁（对应第7种情况）



补充：

- synchronized方法调用非synchronized方法，此时是线程安全的么？

  答：不是的。一旦出了synchronized方法，由于另一个方法未被synchronized修饰，所以这个方法是可以被多个线程同时访问的。

## 第5章 Synchronized的性质

> 重点对可重入性作深入讲解，对粒度进行代码展示。

- 可重入性质
- 不可中断性质

### 5-1 可重入性质-理论部分 (07:54)

锁的种类：自选锁、互斥锁、可重入锁（递归锁）、中断锁、读写锁

可重入锁：`synchronized`、`ReentrantLock`



什么是可重入：

> 指的是同一线程的外层函数获得锁之后，内层函数可以直接再次获取该锁。

好处：

> 避免死锁、提升封装性

- 假设方法A和方法B都被synchronized修饰，线程执行到方法A并且获得了这把锁。此时方法A想要去调用方法B，由于方法B也被synchronized修饰，所以也需要获取锁。线程在这个时候既想要获取锁又不想释放锁，造成了永久等待，所以形成了死锁。

- 可重入性避免了一次一次的解锁加锁。

粒度：

> 指的是加锁的一个范围（scope）。线程而非调用（用3种情况来说明和pthread的区别）

​	**synchronized的粒度为线程**

​	pthread（Linux中的PROCESS线程）的粒度为调用



代码验证：

1. 情况1:证明同一个方法是可以重入的

2. 情况2:证明可重入不要求是同一个方法（不同方法）

3. 情况3:证明可重入不要求是同一个类中（不同类）

   子类重写父类的synchronized方法，然后在子类中利用super调用父类的这个synchronized方法，如果不具备可重入性，则代码会产生死锁。

### 5-2 可重入性质-代码演示 (10:32)

补充：

- 递归三要素：
  1. 一定有一种可以退出程序的情况
  2. 总是在尝试将一个问题化简到更小的规模
  3. 父问题与子问题不能有重叠的部分



1. 情况1:证明同一个方法是可以重入的

   ```java
   /**
    * 可重入粒度测试：递归调用本方法
    */
   public class SynchronizedRecursion10 {
       int a = 0;
   
       public static void main(String[] args) {
           SynchronizedRecursion10 synchronizedRecursion10 = new SynchronizedRecursion10();
           synchronizedRecursion10.method1();
       }
   
       private synchronized void method1() {
           System.out.println("this is method1, a = " + a);
           if (a == 0) {
               a++;
               method1();
           }
       }
   }
   ```

2. 情况2:证明可重入不要求是同一个方法（不同方法）

   ```java
   /**
    * 可重入粒度测试：调用类内另外的方法
    */
   public class SynchronizedOtherMethod11 {
       public static void main(String[] args) {
           SynchronizedOtherMethod11 synchronizedOtherMethod11 = new SynchronizedOtherMethod11();
           synchronizedOtherMethod11.method1();
       }
   
       public synchronized void method1() {
           System.out.println("I'm method1");
           method2();
       }
   
       private synchronized void method2() {
           System.out.println("I'm method2");
       }
   }
   ```

3. 情况3:证明可重入不要求是同一个类中（不同类）

   ```java
   /**
    * 可重入粒度测试：调用父类的方法
    */
   public class SynchronizedSuperClass12 {
       public synchronized void doSomething() {
           System.out.println("我是父类方法");
       }
   }
   
   class TestClass extends SynchronizedSuperClass12 {
       public static void main(String[] args) {
           TestClass t = new TestClass();
           t.doSomething();
       }
   
       @Override
       public synchronized void doSomething() {
           System.out.println("我是子类方法");
           super.doSomething();
       }
   }
   ```

### 5-3 不可中断性质 (02:10)

一旦锁已经被其他线程获得，如果当前线程想要获得锁，那么当前线程只能选择等待或者阻塞，直到其他线程释放这个锁。如果其他线程永远不释放锁，那么当前线程只能永远地等待下去。

相比之下，`JUC包`下的`Lock`类，可以拥有中断的能力。第一点，如果我觉得我等的时间太长了，有权中断现在已经获取到锁的线程的执行；第二点，如果我觉得我等待的时间太长了不想等待了，也可以退出。

`Lock类`相比`synchronized`更为灵活，但是需要注意更多的点，在编码中避免出错。


## 第6章 深入原理

> 知其所以然，学习原理。学习monitor指令、可重入和可见性原理

- 加锁和释放锁的原理：现象、时机、深入JVM查看字节码
- 可重入原理：加锁次数计数器
- 保证可见性的原理：内存模型

### 6-1 加锁和释放锁的原理 (08:55)

1. 现象：每一个类的实例对应一把锁，每一个synchronized方法都必须获取调用该方法的类的实例的锁方能执行，否则线程会阻塞。方法一旦执行就会独占这把锁直到该方法返回或者抛出异常才将锁释放。释放锁之后，其他阻塞线程才有机会获取到锁，进入到可执行状态。

2. 获取和释放锁的时机：内置锁（监视器锁，MonitorLock）

3. 等价代码：

   ```java
   /**
    * synchronized锁与lock锁
    */
   public class SynchronizedToLock13 {
       public static void main(String[] args) {
           SynchronizedToLock13 s = new SynchronizedToLock13();
           s.method1();
           s.method2();
       }
   
       Lock lock = new ReentrantLock();
   
       public synchronized void method1() {
           System.out.println("我是Synchronized形式的锁。");
       }
   
       public void method2() {
           lock.lock();
           try {
               System.out.println("我是lock形式的锁。");
           } finally {
               lock.unlock();
           }
       }
   }
   ```

4. 深入JVM看字节码：反编译、monitor指令

### 6-2 反编译看monitor指令 (12:06)

概况：synchronized使用的锁是在Java对象头里的一个字段，表明是否被锁住。

synchronized的实现原理：进入锁和释放锁是基于monitor对象来实现同步方法和同步代码块的

monitor对象最主要是两个指令：

- monitorenter：插入到同步代码块开始的位置

- monitorexit：插入到方法结束和退出的时候

  monitorenter个数 <= monitorexit个数

补充：

- Java对象头
- class类文件的结构



如何反编译：

1. 编写Java代码

   ```java
   /**
    * 反编译字节码
    */
   public class Decompilation14 {
       private final Object object = new Object();
   
       public void insert(Thread thread) {
           synchronized (object) {
   
           }
       }
   }
   ```

2. 编译为字节码文件

   ```shell
   javac Decompilation14.java
   ```

3. 反编译/反汇编

   ```shell
   javap -verbose Decompilation14
   ```

   `-verbose`：打印所有信息



Monitorenter和Monitorexit指令

- monitorenter和monitorexit在执行的时候会让对象的锁计数器加一或减一，和操作系统的PV操作非常类似。
- 每一个对象都与一个monitor相关联，一个monitor的lock锁在同一时间只能被一个线程获取
- 一个线程在尝试获得与这个对象相关联的monitor的所有权时，会发生三种情况：
  1. 如果monitor计数器为0，意味着目前还没有被获得，线程会立刻获得锁，然后把计数器加1
  2. 如果monitor已经拿到锁的所有权，然后又重入，会导致锁计数器累加
  3. 如果monitor已经被其他线程持有，此时线程进入阻塞状态，直到monitor计数器变为0才会再次去尝试获取锁
- monitorexit：释放对于monitor的所有权（前提是已经拥有锁的所有权）。释放所有权就是将monitor计数器减一，如果为0则当前线程不再拥有monitor的所有权，同时阻塞的线程会再次尝试获取锁；如果不为0则说明上一步为重入，当前线程依然持有monitor的所有权

### 6-3 可重入原理 (01:50)

加锁次数计数器

- JVM负责跟踪对象被加锁的次数
- 线程第一次给对象加锁的时候，计数器变为1；每当这个**相同的线程**在此对象上再次获得锁时，计数器会递增
- 每当任务离开时，计数器递减，当计数器为0的时候，锁被完全释放

### 6-4 可见性原理 (03:34)

- Java内存模型：

  <img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8qrch253bj30um0qcjzl.jpg" alt="image-20191108172602915" style="zoom:35%;" alt="Java内存模型"/>

  线程使用的内存速度要比主存所使用的内存要快。

- 线程A向线程B发送数据的步骤：

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8qre9xvtwj30xe0pon3m.jpg" alt="image-20191108172750043" style="zoom:35%;" alt="线程之间的通信"/>

## 第7章 Synchronized的缺陷

> 对Synchronized关键字的缺陷进行梳理

### 7-1 Synchronized缺陷 (09:02)

1. 效率低：锁的释放情况少、试图获得锁时不能设定超时、不能中断一个正在试图获得锁的线程

   在IO操作、sleep()等不会主动释放锁

   永久等待别的线程释放锁，再获取锁。不到南墙不回头，不到黄河心不死

   解决方案：使用Lock锁

2. 不够灵活：加锁和释放锁的时机单一，每个锁仅有单一的条件（某个对象），可能是不够的。

   读写锁在读操作时不会加锁，在写操作的时候才会加锁

   解决方案：使用读写锁

3. 无法知道是否成功获取到锁

   Lock锁可以做尝试加锁。分成功、失败的情况

```java
/**
 * 展示Lock的方法
 */
public class LockExample15 {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

        boolean tryLock1 = lock.tryLock();
        boolean tryLock2 = lock.tryLock(10, TimeUnit.SECONDS);

        Condition condition = lock.newCondition();
    }
}
```

## 第8章 常见面试题

> 对常见面试题进行总结

### 8-1 常见面试问题 (07:01)

1. 使用注意点：锁对象不能为空、作用域不宜过大、避免死锁

   - 锁对象不能为空：锁对象必须是一个实例对象，不能是一个空对象，需由new或者其他方法创建。锁的信息都保存在对象头中的。

   - 作用域不宜过大：作用域即synchronized所包裹的范围，范围过大会降低程序运行速度。

   - 避免死锁：

     ```java
     synchronized (instnace1) {
         synchronized (instnace2) {}
     }
     synchronized (instnace2) {
         synchronized (instnace1) {}
     }
     ```

2. 如何选择Lock和synchronized关键字？

   - 如果可以，尽量都不使用。而是使用`java.util.concurrent`包下的原子类
   - 如果synchronized适用，就优先使用synchronized。减少代码量。
   - 特别需要才使用Lock、Condition

3. 多线程访问同步方法的各种具体情况

   第四章。7种具体情况

## 第9章 思考题

> 由点到面，进一步引发同学们的深入思考

### 9-1 思考题 (05:47)

1. 多个线程等待同一个synchronized锁的时候，JVM如何选择下一个获取锁的是哪一个线程？用的是什么算法呢？

   涉及到内部锁调度机制。可能是运行时间最短的，也有可能是等待时间最长的，甚至是随机的。这和不同JVM的版本和具体实现都有关系，所以不能依赖具体算法。只能判定为随机的、不可控制的。

2. synchronized使得同时只有一个线程可以运行，性能较差，有什么办法可以提升性能？

   - 优化使用范围，不宜范围过大。临界区在符合要求的情况下尽可能的小。
   - 使用其他类型的锁，比如说读写锁

3. 我想更灵活地控制锁的获取和释放（现在释放锁的时机都被规定死了），怎们办？

   可以自己实现一个Lock接口。参考已有的优秀的锁。

4. 什么是锁的升级、降级？什么是JVM里的偏斜锁、轻量级锁、重量级锁？

   随JVM版本的提高，synchronized关键字的性能得到了显著提升

## 第10章 课程总结

> 对课程进行总结梳理，展望。

### 10-1 课程总结和展望 (13:35)

一句话介绍synchronized

> JVM会自动通过使用monitor来加锁和解锁，保证了同时只有一个线程可以同时执行指定代码，从而保证了线程安全，同时具有可重入和不可中断的性质。

思维导图：（百度脑图,别登录否则没有权限）

https://naotu.baidu.com/file/12e7658471786e71e69cf57c9316a3de

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g8rzca8dncj31lx0u01kx.jpg" alt="Synchronized关键字" style="zoom:100%;" />



**实战课：Java并发核心知识体系精讲**

> https://coding.imooc.com/class/362.html

- 实现多线程的方式到底是1种、2种还是4种？：本质只有1种方式，实现执行内容有2种方式，而包装后的外在表现形式有多种形式
- 启动线程的正确和错误的方式：包含`start()`和`run()`的源码分析、对比
- 停止线程的正确方式：包含为什么广为流传的volatile boolean是错误的？如何做到3方配合，完美实现线程的停止？
- 线程的6个状态、线程的生命周期：一图说清线程的整个生命周期、究竟什么叫“线程阻塞”？
- 并发重要方法详解：包含`wait()`、`notify()`、`notifyAll()`、`sleep()`、`join()`、`yield()`、`Thread.currentThread()`等
- 线程重要属性：包含守护线程和普通线程的区别？为什么不应该利用线程优先级设计程序？
- 线程的未捕获异常UncaughtException应该如何处理？有哪些解决方案？
- 线程安全：包含如何找出`a++`具体是在哪里出的错？3类线程不安全的现象、4种需要额外考虑线程安全的场景
- Java内存模型详解——底层原理：重排序、可见性、原子性、happens-before、volatile
- 死锁详解：必然死锁的例子、发生死锁的4个条件、如何用jstack命令和代码这两种方式定位死锁？修复死锁的3种方案、实际工程中如何避免死锁？
- 彩蛋：如何从宏观和微观两个方面来提高技术？提高技术的途径？工作中业务缠身，如何在业务开发中得到更多成长？
- 常见面试问题——配有文档总结