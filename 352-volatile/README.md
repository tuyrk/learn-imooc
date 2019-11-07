[细说Java多线程之内存可见性](https://segmentfault.com/a/1190000010113400)
===
第1章 课程简介
---
多线程-数据争用-内存可见性
- 共享变量在线程间的可见性
- `synchronized`实现可见性
- `volatile`实现可见性
    - 指令重排序
    - `as-if-serial`语义
    - `volatile`使用注意事项
- `synchronized`和`volatile`比较

第2章 可见性介绍
---
### 2-1 可见性介绍
1. 可见性：一个线程对共享变量值得修改，能够及时地被其他线程看到。
2. 共享变量：如果一个变量在多个线程的工作内存中都存在副本，那么这个变量就是这几个线程的共享变量。
3. 线程的工作内存：`Java`内存抽象出来的概念
4. `Java`内存模型（`JMM`）  
`Java`内存模型（`Java Memory Model`）描述了`Java`程序中各种变量（线程共享变量）的访问规则，以及在`JVM`中将变量存储到内存和从内存中读取出变量这样的底层细节。
5. 所有的变量都存储在主内存中  
每个线程都有自己独立的工作内存，里面保存该线程使用到的变量的副本（主内存中该变量的一份拷贝）
![](https://img2.mukewang.com/5523386a00017a3712000530.jpg)
6. 共享变量使用的两条规则  
    - 线程对共享变量的所有操作都必须在自己的工作内存中进行，不能直接从主内存中读写
    - 不同线程之间无法直接访问其他线程工作内存中的变量，线程间变量值的传递需要通过主内存来完成
7. 共享变量可见性实现的原理  
线程1对共享变量的修改要想被线程2及时看到，必须经过如下2个步骤：
    1. 把工作内存1中更新过的共享变量刷新到主内存中
    2. 将主内存中最新的共享变量的值更新到工作内存2中
    
    线程1-->工作内存1中变量`X`-->更新到主内存中-->工作内存2中的变量`X`得到更新-->线程2  
    ![](https://segmentfault.com/img/bVQA6t?w=333&h=265)
### 2-2 练习题
下面叙述错误的是：B
> A. 通过`synchronized`和`volatile`都可以实现可见性  
> B. 不同线程之间可以直接访问其他线程工作内存中的变量  
> C. 线程对共享变量的所有操作都必须在自己的工作内存中进行  
> D. 所有的变量都存储在主内存中

第3章 `synchronized`实现可见性
---
### 3-1 `synchronized`实现可见性原理
> 要实现共享变量的可见性，必须保证两点：
>   - 线程修改后的共享变量值能够及时中工作内存刷新到主内存中
>   - 其他线程能够及时把共享变量的最新值从主内存更新到自己的工作内存中

> Java语言层面支持的可见性实现方式：`synchronized`、`volatile`

1. synchronized能够实现：原子性（同步）、可见性
2. JMM关于synchronized的两条规定：
    - 线程解锁前，必须把共享变量的最新值刷新到内存中。
    - 线程加锁时，将清空工作内存中共享变量的值，从而使用共享变量时需要从主存中中重新读取最新的值。（注意：加锁与解锁需要是同意把锁）
      
    线程解锁前对共享变量的修改在下次加锁时对其他线程可见。
3. 线程执行互斥代码的过程：
    1. 获得互斥锁
    2. 清空工作内存
    3. 从主内存拷贝变量的最新副本到工作内存
    4. 执行锁内代码
    5. 将更改后的共享变量值刷新到主内存
    6. 释放互斥锁
4. 指令重排序  
    1. 重排序：代码的书写顺序与实际执行的顺序不同，指令重排序是编译器或处理器为了提高程序性能而做的优化
        - 编译器优化的重排序（编译器优化）重新安排代码的执行顺序
        - 指令级并行重排序（处理器优化）双核处理器采用了指令级并行技术，可执行多条指令
        - 内存系统的重排序（处理器优化）对读写缓存做的优化
        ```
        //代码顺序：         //执行顺序：
        int number = 1;     int result = 0;
        int result = 0;     int number = 1;
        ```
    2. `as-if-serial`：无论如何重排序，程序执行的结果应该与代码顺序执行的结果一致（`Java`编译器都会保证`Java`在单线程下遵循`as-if-serial`语义）
    
    注：  
    重排序不会给单线程带来内存可见性问题  
    多线程中程序交错执行时，重排序可能会造成内存可见性问题  
### 3-2 `synchronized`实现可见性代码
```
ready = false;
result = 0;
number = 1;
//写操作
public void write() {
   ready = true;//1.1
   number = 2;//1.2
}

//读操作
public void read() {
   if (ready) {//2.1
       result = number * 3;//2.2
   }
   System.out.println("result的值为：" + result);
}
```
执行顺序：
1. 1.1-->2.1-->2.2-->1.2
result=3;
2. 1.2-->2.1-->2.2-->1.1
result=0;

导致共享变量在线程间不可见的原因：
1. 线程的交叉执行
2. 重排序结合线程交叉执行
3. 共享变量更新后的值没有在工作内存与主内存间及时更新

synchronized解决不可见的方案：
1. 原子性：由于锁的关系，线程之间不允许交叉执行；相当于给该线程（或当前运行的有且仅有一个的线程）加了一把锁，外面的线程无法进入，更别提互相交叉执行。
2. 原子性 + `as-if-serial`语义：线程不能交叉执行，重排序对于单线程不能影响运行结果。
3. 可见性：共享变量的更新执行。

常见问题：
1. 为何不加`synchronized`也会执行可见性，主内存及时更新被获取最新值?  
因为当时定义说加`synchronized`一定会可见性，而不加也没说一定不会，只是有可能不会；因为现在`Java`做了一些优化：尽量实现可见性；但是不能保证每次都成功，只是成功概率比较大99%，但还是有1%的情况会失败。所以处于安全考虑，尽量加`synchronized`关键字100%成功。
2. 有时候依然不存在线程交叉情况，但还是会先执行第二个线程，因为第一个线程把`CPU`让位出来，所以为了避免这种情况，可以在第一个线程后附上代码：`sleep(1000);`1秒之后才有机会执行线程2。
3. `synchronized` + `sleep();`黄金搭档。
### 3-5 练习题
下面说法错误的是：C
> A. 当两个并发线程访问同一个对象`object`中的这个`synchronized(this)`同步代码块时，一个时间内只能有一个线程得到执行。  
> B. 当一个线程访问`object`的一个`synchronized(this)`同步代码块时，另一个线程仍然可以访问该`object`中的非`synchronized(this)`同步代码块。  
> C. 当一个线程访问`object`的一个`synchronized(this)`同步代码块时，其他线程对`object`中所有其它`synchronized(this)`同步代码块的访问不会被阻塞。  
> D. 当一个线程访问`object`的一个`synchronized(this)`同步代码块时，它就获得了这个`object`的对象锁。结果，其它线程对该`object`对象所有同步代码部分的访问都被暂时阻塞。

第4章 `volatile`实现可见性
---
### 4-1 `volatile`能够保证可见性
1. `volatile`关键字：  
1.能够保证`volatile`变量的可见性。  
2.不能保证`volatile`变量复合操作的原子性。
2. `volatile`如何实现内存可见性：
通过加入内存屏障和禁止重排序优化来实现的。  
1.对`volatile`变量执行写操作时，会在写操作后加入一条`store`屏障指令。  
2.对`volatile`变量执行读操作时，会在读操作前加入一条`load`屏障指令。  
`Java`内存模型中一共定义了8条操作指令来完成主内存和工作内存的交互  
通俗地讲：`volatile`变量在每次被线程访问时，都强迫从主内存中重读该变量的值，而当该变量发生变化时，又会强迫线程将最新的值刷新到主内存。这样任何时候，不同的线程总能看到该变量的最新值
3. 线程读/写`volatile`变量的过程：
    - 线程写`volatile`变量的过程：  
        1.改变线程工作内存的中`volatile`变量副本的值。  
        2讲改变后的副本的值从工作内存刷新到主内存。
    - 线程读`volatile`变量的过程：  
        1.从主内存中读取`volatile`变量的最新值到线程的工作内存中。  
        2.从工作内存中读取`volatile`变量的副本。
4. `volatile`不能保证`volatile`变量复合操作的原子性
    1. ```number++;```的步骤：
        1. 读取`number`的值
        2. 将`number`的值加1
        3. 写入最新的`number`的值
        ```
        private int number = 0;
        number++;//不是原子操作
        ```
    2. 加入`synchronized`，变为原子操作 
       ```
        synchronized(this){
            number++;
        }
        ```
    3. 变为`volatile`变量，无法保证原子性   
    ```private volatile int number = 0;```
### 4-2 `volatile`不能保证原子性
例：
`number = 5`  
1.线程`A`读取`number`的值 A=5 B=N M=5  
2.线程`B`读取`number`的值 A=5 B=5 M=5  
3.线程`B`执行加1操作 A=5 B=6 M=5  
4.线程`B`写入最新的`number`的值 A=5 B=6 M=6  
5.线程`A`执行加1操作 A=6 B=6 M=6  
6.线程`A`写入最新的`number`的值 A=6 B=6 M=6  
两次`number++`只增加了1

解决方案：  
保证`number`自增操作的原子性：
1. 使用`synchronized`关键字
    ```
    synchronized (this) {
        this.number++;
    }
    ```
2. 使用`ReentrantLock`(`java.util.concurrent.locks`包下)最好使用`try-finally`
    ```
    private Lock lock = new ReentrantLock();
    lock.lock();
    try {
        this.number++;
    } finally {
        lock.unlock();
    }
    ```
3. 使用`AtomicInteger`(`java.util.concurrent.atomic`包下)
    ```
    private AtomicInteger num = new AtomicInteger(0);
    num.getAndAdd(1);
    ```
### 4-5 `volatile`使用注意事项
要在多线程中安全的使用`volatile`变量，必须同时满足：
1. 对变量的写入操作不依赖其当前值  
不满足：`number++`、`count=count*5`等  
满足：`boolean`变量、记录温度变化的变量等
2. 该变量没有包含在具有其他变量的不变式中  
不满足：不变式`low<up`
### 4-6 `synchronized`与`volatile`比较
1. `volatile`不需要加锁，比`synchronized`更轻量级，不会阻塞线程
2. 从内存可见性角度讲：`volatile`读操作相当于加锁；`volatile`写操作相当于解锁
3. `synchronized`既能保证可见性，又能保证原子性；而`volatile`只能保证可见性，无法保证原子性

总而言之：`synchronized`稳定性高，效率低；`volatile`稳定性低，效率高。
### 4-7 练习题
下列说法中错误的是：A
> A. `volatile`是保证被修饰变量的可见性，同时也保证原子操作  
> B. `Java`中没有提供检测与避免死锁的专门机制，但应用程序员可以采用某些策略防止死锁的发生  
> C. `JAVA`中对共享数据操作的并发控制是采用加锁技术  
> D. 共享数据的访问权限都必须定义为`private`

第5章 课程总结
---
总结：
1. 什么是内存可见性
2. `Java`内存模型（`JMM`）
3. 实现可见性的方式：`synchronized`和`volatile`
    - `final`也可以保证内存的可见性
4. `synchronized`和`volatile`实现内存可见性的原理
5. `synchronized`实现可见性
    - 指令重排序
    - `as-if-serial`语义
6. `volatile`实现可见性
    - `volatile`能够保证可见性
    - `volatile`不能保证原子性(`number++`)
    - `volatile`使用注意事项
7. synchronized和volatile比较
    - volatile比synchronized更轻量级
    - synchronized比volatile使用更广泛

补充：  
1. 问：即使没有保证可见性的措施(如s`ynchronized`、`volatile`、`final`)，很多时候共享变量依然能够在主内存和工作内存见得到及时的更新？  
答：一般只有在短时间内高并发的情况下才会出现变量得不到及时更新的情况，因为`CPU`在执行时会很快地刷新缓存，所以一般情况下很难看到这种问题。
2. 对64位(`long`、`double`)变量的读写可能不是原子操作：  
`Java`内存模型允许`JVM`将没有被`volatile`修饰的64位数据类型的读写操作划分为两次32位的读写操作来进行  
导致问题：有可能会出现读取到“半个变量”的情况  
解决方法：加`volatile`关键字  
注：目前几乎所有虚拟机已经把64位数据的读写做了原子操作，不需将(`long`、`double`)使用`volatile`修饰

