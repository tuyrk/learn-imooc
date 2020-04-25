# JDK11&12 新特性解读

> https://www.imooc.com/learn/553
>
> 简介：Java 语言如今已被广泛应用到科技行业的各个领域，成为当前最热门的计算机语言之一。Java 语言版本更新速度很快，每个新版本都能或多或少的影响当前的开发模式。学习 Java 新版本的特性，能够更加清晰的了解 Java 未来的发展方向，以及梳理编程的思想。

## 第1章 课程介绍

> 本章将介绍课程的整体内容、课程安排以及JDK 与 OpenJDK 的关系。

### 1-1 课程介绍 (04:02)

- OpenJDK与JDK的区别

  通常所说的JDK是指SUN或者Oracle JDK，商业实现。开源只用于研究使用

  OpenJDK是指开源项目，软件源安装的都是OpenJDK

- 多版本JDK的安装和配置

- JDK11新特性介绍及演示

- JDK12新特性介绍及演示

### 1-2 OpenJDK 与 JDK 的区别_x264 (04:47)

- OpenJDK与JDK之间的关系

  OpenJDK是JDK的开放源代码版本

- OpenJDK与JDK之间的区别

  **授权协议不同**、OpenJDK不包含部署功能、OpenJDK源代码不完整

  部分源代码使用开源代码替换、OpenJDK只包含最精简的JDK、不能使用Java商标

### 1-3 初识 JDK11 和 JDK12 的新特性_x264 (03:21)

- 什么是JEP（Java Enhancement Proposals）

- JEP发布地址

  JDK11：http://openjdk.java.net/projects/jdk/11/

  JDK12：http://openjdk.java.net/projects/jdk/12/

- JDK11重要的新特性

  JEP181:基于嵌套的访问控制、JEP309:动态类文件常量、JEP315:改进Aarch64内部函数

  JEP321:标准HTTP客户端、JEP323:本地变量Lambda语法、JEP327:Unicode10

  JEP330:启动单文件源代码程序、JEP333:可伸缩低延迟垃圾收集器

- JDK11其他新特性

  JEP318:无操作垃圾收集器、JEP320:移除JavaEE和CORBA模块、JEP324:Curve25519和448算法的密钥协议

  JEP328:Flight Recorder、JEP329:ChaCha20和Poly1305加密算法、JEP331:低开销的Heap Profiling

  JEP332:支持TLS1.3、JEP335:弃用Nashorn JavaScript引擎、JEP336:弃用Pack200工具和API

- JDK12重要的新特性

  JEP230:微基准测试、JEP325:增强的switch语句、JEP341:默认的类数据共享

  JEP344:可中止的G1垃圾收集器、JEP346:G1归还未使用的内存

- JDK12其他新特性

  JEP189:低暂停的垃圾收集器-Shenandoah、JEP334:JVM常量API、JEP340:保留Aarch64端口


## 第2章 多版本 JDK 的安装和配置

> 本章将介绍 JDK11、12 的下载和安装，以及多版本 JDK 在一台机器上的配置，做到方便切换 JDK 版本。友情提示：安装和配置过程面向 Linux 和 Mac OS，Windows 系统可以参考下载资料中的相关文档。

### 2-1 安装 JDK11 和 JDK12_x264 (06:47)

- 下载安装文件：http://jdk.java.net/archive/

  ```shell
  wget https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_osx-x64_bin.tar.gz
  wget https://download.java.net/java/GA/jdk12.0.2/e482c34c86bd4bf8b56c0b35558996b9/10/GPL/openjdk-12.0.2_osx-x64_bin.tar.gz
  ```

  注：直接使用JDK14学习本课程，JDK8为默认的开发环境。

- 配置环境变量：JAVA_HONE和PATH

  ```java
  vim ~/.bash_profile
  ```

  ```shell
  export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home"
  export PATH=$PATH:$JAVA_HOME/bin
  ```

- 重新加载配置文件：source命令

  ```shell
  source ~/.bash_profile
  ```

- 查看JDK版本信息：java -version

### 2-2 配置多版本 JDK_x264 (06:40)

- 设置多版本环境变量：JAVA_8_HOME、JAVA_11_HOME、JAVA_12_HOME

  ```shell
  export JAVA_8_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home"
  export JAVA_14_HOME="/Library/Java/JavaVirtualMachines/jdk-14.0.1.jdk/Contents/Home"
  ```

- 配置默认环境变量：JAVA_HOME

  ```shell
  export JAVA_HOME=$JAVA_8_HOME
  export PATH=$PATH:$JAVA_HOME/bin
  ```

- 设置命令别名，动态切换JDK版本：alias

  ```shell
  alias jdk8="export JAVA_HOME=$JAVA_8_HOME"
  alias jdk14="export JAVA_HOME=$JAVA_14_HOME"
  ```

- 查看JDK版本信息：java -version


## 第3章 JDK11 新特性介绍及演示

> 本章中将介绍 JDK11 中的新特性，部分核心特性会结合代码演示操作，帮助同学们更好的理解。

### 3-1 基于嵌套的访问控制 (11:31)

> JEP181:基于嵌套的访问控制

摘要：在private、public、protected的基础上，JVM又提供了一种新的访问控制机制：Nest

目标：如果在一个类中嵌套了多个子类，那么子类中可以访问彼此的私有成员

代码实例：

```java
public class NestAccessExample {
    private static class X {
        void test() throws Exception {
            Y y = new Y();
            y.y = 1; // 基于嵌套的访问控制

            Field field = Y.class.getDeclaredField("y");
            field.setInt(y, 2);
        }
    }

    private static class Y {
        private int y;
    }

    public static void main(String[] args) throws Exception {
        new X().test();
    }
}
```

JDK8报错：Exception in thread "main" java.lang.IllegalAccessException: Class com.tuyrk.jdk11.NestAccessExample “X can not access a member of class com.tuyrk.jdk11.NestAccessExample”Y with modifiers "private"

注：JDK11之前则需设置`field.setAccessible(true);`。JDK11的反射则可以访问源码级别的私有属性。

### 3-2 动态类文件常量 (09:11)

> JEP309:动态类文件常量

摘要：增加一个常量类型-CONSTANT_Dynamic

目标：降低开发新形式的可实现类文件约束带来的成本和干扰

代码实例：

```java
public class DynamicTest {
    private static void test() {
        System.out.println("hello");
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findStatic(DynamicTest.class, "test", MethodType.methodType(void.class));
        mh.invokeExact();
    }
}
```

### 3-3 改进 Aarch64 函数 (06:50)

> JEP315:改进Aarch64内联函数

摘要：改进现有的字符串和数组函数，并在Aarch64处理器上为java.lang.Math sin、cos、log函数实现新的内联函数

目标：专用的CPU架构可提高应用程序的性能

代码实例：

```java
private static void mathOnJdk11() {
  long startTime = System.currentTimeMillis();
  for (int i = 0; i < 10_000_000; i++) {
    Math.sin(i);
    Math.cos(i);
    Math.log(i);
  }
  long endTime = System.currentTimeMillis();
  System.out.println(TimeUnit.NANOSECONDS.toNanos(endTime - startTime) + "nanos");
}
```

JDK8：7201nanos

JDK11：761nanos

### 3-4 标准 HTTP 客户端 (11:47)

> JEP321:标准HTTP客户端

摘要：在JDK9中就已经引入了HTTPClient，不过一直处于孵化状态，到了JDK11，HTTPClient API结束了孵化状态，作为一个标准API提供在java.net.http包中。

目标：取代HttpURLConnection

缺点：

1. HttpURLConnection在设计时考虑多种协议，但如今协议都不存在了
2. 早于HTTP1.1，太抽象。
3. 使用不友好，提供的API不符合常用的编程思想
4. 只能以阻塞模式工作，严重问题。
5. 难维护，使用超级少。 多用Apache HTTPClient

代码实例：

1. 同步Get方法

   ```java
   private static void syncGet(String uri) throws Exception {
     HttpClient client = HttpClient.newHttpClient();
     HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
     HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
     System.out.println(response.statusCode());
     System.out.println(response.body());
   }
   ```

2. 异步Get方法

   ```java
   private static void asyncGet(String uri) throws Exception {
     HttpClient client = HttpClient.newHttpClient();
     HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
     CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
     future.whenComplete((response, e) -> {
       if (e != null) {
         e.printStackTrace();
       } else {
         System.out.println(response.statusCode());
         System.out.println(response.body());
       }
     }).join();
   }
   ```

### 3-5 Lambda 参数的本地变量语法 (18:14)

> JEP323:Lambda参数的本地变量语法

摘要：允许var在声明隐式类型的lambda表达式的形式参数时使用

目标：将隐式类型的lambda表达式中的形式参数声明的语法与局部变量声明的语法对齐

代码实例：

1. Java8特性：lambda表达式语法

   ```java
   private static void lambdaInJava8() {
     // 参数列表，->，函数体
     new Thread(new Runnable() {
       @Override
       public void run() {
         System.out.println("before java8");
       }
     }).start();
   
     new Thread(() -> System.out.println("in java8")).start();
   
     List<String> list = Arrays.asList("java8", "jdk8", "1.8");
     list.forEach(w -> {
       System.out.println("lambda in java8: " + w);
     });
   }
   ```

2. Java10新特性，局部变量类型推断

   > JDK10允许使用var关键字来定义变量：var i=10
   >
   > - 必须初始化，否则编译报错
   >
   > - 一旦初始化完成过后类型就会确定，不能再赋予其他类型的值
   >
   >   即i不能赋值为“abc”或者2.0等非int类型的值）

   ```java
   private static void varInJava10() {
     int var = 10;
     var i = 10; // int
     var str = "java10"; // String
     var list = new ArrayList<String>(); // ArrayList<String>
     var map = Map.of(1, "a", 2, "b"); // Map<Integer, String>
   
     for (var entry : map.entrySet()) {
       System.out.println(entry);
     }
   
     // i = "abc"; // 和JavaScript中的var不同，不能赋值其他类型
     // var a; // 必须初始化赋值
   }
   
   class ErrorUseVar {
     // JDK10只能允许在局部变量中使用var
     // var i = 10;
     /*var f1(var str) {
       return 10;
     }*/
   }
   ```

3. Java11新特性：Lambda表达式可以使用var来标识变量

   ```java
   private static void lambdaWithVarInJava11() {
     List<Integer> nums = Arrays.asList(8, 7, 9);
     nums.sort((@NotNull Integer s1, @NotNull Integer s2) -> {
       if (s1.equals(s2)) {
         return 0;
       }
       return s1 > s2 ? 1 : -1;
     });
     // 可以给lambda表达式添加注解
     nums.sort((@NotNull var s1, @NotNull var s2) -> {
       if (s1.equals(s2)) {
         return 0;
       }
       return s1 > s2 ? 1 : -1;
     });
     nums.sort((s1, s2) -> {
       if (s1.equals(s2)) {
         return 0;
       }
       return s1 > s2 ? 1 : -1;
     });
     System.out.println(nums);
   }
   ```

   ```java
   (var x, y) -> x.process(y)         // Cannot mix 'var' and 'no var' in implicitly typed lambda expression
   (var x, int y) -> x.process(y)     // Cannot mix 'var' and manifest types in explicitly typed lambda expression
   ```

   ```java
   // var x -> x.foo() // error
   ```

### 3-6 Unicode 10 (03:13)

> JEP327:Unicode10

摘要：升级现有的平台API，支持[Unicode10标准](https://www.unicode.org/versions/Unicode10.0.0/)

目标：支持最新版本的Unicode，主要体现在以下类中：

> java.lang包：Character、String
>
> java.awt.font包：NumericShaper
>
> java.text包：Bidi、BreakIterator、Normalizer

代码实例：

```java
System.out.println("\uD83E\uDDDA"); // 🧚
System.out.println("\uD83E\uDD92"); // 🦒
System.out.println("\uD83E\uDD95"); // 🦕
System.out.println("\uD83E\uDDD9"); // 🧙
System.out.println("\uD83E\uDDDB"); // 🧛
System.out.println("\uD83E\uDD2E"); // 🤮
```

### 3-7 启动单文件源代码程序 (04:01)

> JEP330:启动单文件源代码程序

摘要：增强Java启动程序以运行作为单个Java源代码文件提供的程序

目标：使用java HelloWorld.java运行源代码文件

代码实例：

```shell
cd src/main/java/com/tuyrk/jdk11
java Unicode10Example.java
```

### 3-8 可伸缩低延迟垃圾收集器（上） (09:28)

> JEP333:可伸缩低延迟垃圾收集器

摘要：Z垃圾收集器，也称为ZGC，是一个可伸缩低延迟的垃圾收集器

目标：（最核心）无论开了多大的堆内存（128G、2T），保证低于10ms的JVM停顿，远胜于前一代的G1

需要知道的知识点：垃圾回收、判断对象是否是垃圾的算法、回收垃圾对象内存的算法、垃圾收集器

#### 垃圾回收

- 什么是垃圾回收

  垃圾回收是Java虚拟机垃圾回收器提供的一种用于在空闲时间、不定时回收无任何对象引用的对象占据的内存空间的机制

- 针对垃圾回收的定义提出的一些思考与问题

  引用：引用类型的数值代表的是另一块内存的起始地址

  垃圾：无任何对象引用的对象

  回收：清理垃圾占用的内存空间，而非对象本身

  发生地点：一般为堆内存，因为大部分的对象都存储在堆内存中

  发生时间：程序空间时间不定时回收

#### 判断对象是否是垃圾的算法

> 垃圾回收算法两件必须做的基本工作：
>
> 1. 找到所有的存活对象
> 2. 回收被无用对象占用的内存空间，使得该空间可被程序再次使用

- ~~引用计数法~~

  概念：堆中每个对象都有一个引用计数器。当一个对象被创建并初始化赋值后，该变量计数器设置为1。每当有一个地方引用它时，计数器就加1。当引用失效时(超过生命周期、被新值覆盖)，计数器就减1。任何引用计数为0的对象都可以被当做垃圾收集。

  优点：引用计数收集器执行简单，判定效率高，交织在程序运行中。对程序不被长时间打断的实时环境比较有利（OC的内存管理使用该算法）

  缺点：难以检测出对象之间的循环引用。同时，引用计数器增加了程序执行的开销。所以Java语言并没有选择这种算法进行垃圾回收。

- 根搜索算法、对象引用遍历

  标记可达对象：JVM中用到的所有现代GC算法在回收前都会先找出所有仍存活的对象。根搜索算法是从离散数学中的图论引入的，程序把所有的引用关系看做一张图。下图所展示的JVM中的内存布局可以用来很好地阐释这一概念。

  <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1r4ituq8j30ev05e40u.jpg" alt="根搜索算法" style="zoom:80%;" />

  根集（ROOT Set）：正在执行的Java程序可以访问的引用变量的集合，包括：局部变量、参数、类变量。

  GC根对象：虚拟机栈中引用的对象、方法中类,静态属性引用的对象、活跃的线程等

  程序可以使用引用变量访问对象的属性和调用对象的方法

  算法基本思路：

  1. 通过GC ROOTS对象作为起始点，寻找对应的引用节点
  2. 找到引用节点后，从这些引用节点开始向下继续寻找引用节点
  3. 搜索所走过的路径，称为引用链
  4. 当一个对象到GC ROOTS没有任何引用链相连时就证明对象是不可用的

#### 回收垃圾对象内存的算法

- Tracing算法(Tracing Collector)或标记-清除算法

  两个阶段：标记、清除。

  首先标记出所有需要回收的对象，标记完成后统一回收所有被标记的对象

  标记过程：也即根搜索算法的判定垃圾收集的标记过程

  优点：不需要进行对象的移动；仅对不存活的对象进行处理，在存活对象比较多的情况下是极为高效的

  缺点：

  1. 标记和清除的效率低；需要使用空闲列表记录所有的空闲区域以及大小，对空闲列表的管理会增加分配对象时的工作量。
  2. 标记-清除后会产生大量不连续的内存碎片，虽然空闲区域的大小是足够的，但是可能没有单一的区域能够满足分配所需的大小，便会分配失败并抛出OOM异常。

  <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1rn0etemj30my085aco.jpg" alt="标记-清除算法" style="zoom:80%;" />

- Compacting算法(Compacting Collector)或标记-整理算法

  该方法不是直接对可回收对象进行清理，而是让所有对象向一端移动，然后清理掉边界以外的内存。增加句柄和句柄表实现。

  优点：

  1. 在整理之后，新对象的分配只需通过指针碰撞便可完成
  2. 空闲区域的位置是可知的，也没有内存碎片问题

  缺点：GC暂停的时间会增长，因为需要将所有的对象拷贝到新的内存，还要更新引用地址

  

  <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1rnhwpdgj30lv08jdi9.jpg" alt="标记-整理算法" style="zoom:80%;" />

### 3-9 可伸缩低延迟垃圾收集器（下） (07:48)

#### 垃圾收集器

- 串行垃圾收集器（Serial Garbage Collector）

  > 通过持有并冻结所有应用程序的线程进行工作。
  >
  > 不适合服务器环境；适合简单的命令行程序、单CPU、新生代内存较小、暂停时间要求低的程序

  说明：它为单线程环境设计，只使用一个单独的线程进行垃圾回收。是client级别默认的GC方法

  使用：通过JVM参数`-XX:+UseSerialGC`可以使用串行垃圾回收器

- 并行垃圾回收器（Parallel Garbage Collector）

  > 当执行垃圾回收时，也会冻结所有的应用程序线程
  >
  > 适用于多CPU、暂停时间较短的程序

  说明：它是JVM的默认垃圾回收器。使用多线程进行垃圾回收。

  使用：可用`-XX:UseParallelGC`来强制指定，用`-XX:ParallelGCThreads=4`来制定线程数

- 并发标记扫描垃圾回收器（CMS Garbage Collector）

  > 相比并行垃圾回收器，并发标记扫描垃圾回收器使用更多的CPU来确保程序的吞吐量。如果可以为了更好的程序性能而分配更多的CPU，那么相比并发垃圾回收器，并发标记扫描垃圾回收器是更好的选择。

  说明：并发标记垃圾回收使用多线程扫描堆内存，标记需要清理的实例并且清理被标记的实例。并发标记垃圾回收器只会在下面两种情况持有应用程序所有线程：

  1. 当标记的引用对象在tenured区域
  2. 在进行垃圾回收的时候，堆内存的数据发生改变

    使用：通过JVM参数`XX:+USeParNewGC`打开并发标记扫描垃圾回收器

- G1垃圾回收器（G1 Garbage Collector）

  > G1垃圾回收器适用于堆内存很大的情况，它将堆内存分割为不同的区域，并且并发的对其进行垃圾回收。也可以在垃圾回收之后，对剩余的堆内存空间进行压缩

  说明：G1垃圾回收器是当今回收器技术发展最前沿的成果，它是一款面向服务端应用的回收器，它能充分利用多CPU、多核环境。因此它是一款并行与并发回收器，并且它能建立可预测的停顿时间模型

  使用：通过JVM参数`-XX:+UseG1GC`使用G1垃圾回收器

- ZGC的特性

  所有阶段几乎都是并发执行的

  像G1一样划分Region，但更加灵活

  像G1一样会做Compacting压缩

  单代

- ZGC的工作过程

  1. 初始停顿标记：Pause Mark Start

     停顿JVM，标记ROOT对象，1,2,4三个对象被标记为live

     <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1txzytlbj30k505mgnb.jpg" alt="初始停顿标记" style="zoom:70%;" />

  2. 并发标记：Concurrent Mark

     并发地递归标记其他对象，5和8也被标记为live

     <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1tyg7fgwj30ho05375p.jpg" alt="并发标记" style="zoom:80%;" />

  3. 移动对象：Relocate

     对比发现3,6,7是过期对象，也就是中间的两个灰色region需要被压缩清理，所以陆续将4,5,8对象移动到最右边的新Region。移动过程中，有个forward table记录这种转向。

     <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1tytb7hdj30hu06hdho.jpg" alt="移动对象" style="zoom:80%;" />

  4. 修正指针：Remap

     最后将指针更新，指向新地址。上一个阶段的Remap，和下一个阶段的Mark是混搭在一起完成的，这样非常高效，省掉了重复遍历对象图的开销

     <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1tz8qz9pj30ia06g40c.jpg" alt="修正指针" style="zoom:80%;" />

  [CMS、G1、ZGC的堆内存实现区别](https://www.jianshu.com/p/2957b001645d)

### 3-10 JDK11 中新增加的常用 API (18:45)

java.lang.String

```java
strip() // 删除首尾空白符（包含中文空白符）
stripLeading()
stripTrailing()
isBlank() // 是否为空或仅包含空白符（包含中文空白符）
lines()
repeat(int count) // 重复字符串
```

java.nio.file.Files

```java
writeString(Path path, CharSequence csq) // 向文件写入内容
readString(Path path) // 读取文件内容
```

java.util.List

```java
List.of() // 获取集合，不可变
list.toArray(String[]::new) // 创建String数组
```

### 3-11 JDK11 其他新特性 (04:21)




## 第4章 JDK12 新特性介绍及演示

> 本章将介绍 JDK12 中的新特性，部分核心特性会结合代码演示操作，帮助同学们更好的学习理解。

### 4-1 微基准测试 (19:16)

> JEP230:微基准测试

摘要：Microbenchmark作为常规性能测试的一部分，在JDK源代码中添加一组基础的微基准测试

目标：可以基于Java Microbenchmark Harness（JMH）轻松测试JDK的性能

代码实例

> @Benchmark：方法级注解，表示当前方法需要进行基准测试
> @Fork(2)：进程的个数
> @Threads(8)：线程的个数
> @BenchmarkMode(Mode.Throughput)：基准测试类型，吞吐量
> @Warmup(iterations = 5)：预热轮数
> @Measurement(iterations = 10, time = 5)：度量，基本测试参数。
> @OutputTimeUnit(TimeUnit.MILLISECONDS)：测试结果的输出时间类型
> @Setup：初始化，测试前的准备工作
> @TearDown：清理，测试后的结束工作。资源回收

JMH使用方法：测试字符串拼接的性能

```java
@Fork(2)
@Threads(8)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 10, time = 5)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkTest {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchmarkTest.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public void testStringAdd() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
    }

    @Benchmark
    public void testStringAdds() {
        String a = "" + "1" + "2" + "3" + "4" + "5" + "6" + "7" + "8" + "9";
    }

    @Benchmark
    public void testStringBuilderAdd() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
    }
}
```

```
Benchmark                            Mode  Cnt         Score         Error   Units
BenchmarkTest.testStringAdd         thrpt   20     22305.916 ±    2473.762  ops/ms
BenchmarkTest.testStringAdds        thrpt   20  11795913.631 ± 1105845.467  ops/ms
BenchmarkTest.testStringBuilderAdd  thrpt   20     86506.156 ±    3584.597  ops/ms
```

### 4-2 增强的 switch 语句 (11:13)

> JEP325:增强的switch语句

摘要：扩展switch语句，使其可以用作语句或者表达式，并且“传统”或“简化”两种都可以使用

目标：简化switch语句的书写形式

代码实例

```java
switch (Calendar.MONDAY) {
  case Calendar.MONDAY -> System.out.println(1);
  case Calendar.SATURDAY -> System.out.println(6);
  default -> System.out.println(0);
}
```

```java
int result = switch (Calendar.MONDAY) {
  case Calendar.MONDAY -> 1;
  case Calendar.SATURDAY -> 6;
  default -> 0;
};
System.out.println(result);
```

```shell
java SwitchExample.java
javac --release 12 --enable-preview SwitchExample.java
java --enable-preview SwitchExample
```

### 4-3 默认的类数据共享 (04:20)

> JEP341:默认的类数据共享

摘要：在64位平台上使用默认类列表，增强了JDK生成的类共享（CDS），改善了开箱即用的启动时间

目标：取消了用户必需运行`-Xshare:dump`，才能使用CDS的功能

关于类数据共享：CDS（Class Data Sharing）通过将一组核心系统类装载到共享内存中，可以在多个JVM中共享这些类。减少内存占用和缩短JVM启动时间

### 4-4 G1 垃圾收集器功能增强 (14:33)

> JEP344\346:G1垃圾收集器的增强

摘要：

- 344：如果G1垃圾收集器有可能超过预期的暂停时间，则可以使用中止选项
- 346：如果应用程序活动非常低，G1应该在合理的时间段内释放未使用的Java堆内存

目标：

- 344：G1可以中止可选部分的回收已达到停顿时间目标
- 346：可以在空闲时自动将Java堆内存返还给操作系统



G1垃圾收集器-什么是G1？

> Garbage-First Garbage Collector：面向大内存(数G、数10G)、多核系统收集器、软停顿目标、可预测停顿时间，目标替换CMS，在JDK1.9作为默认垃圾收集器

G1垃圾收集器-堆内存结构

> - CMS：需要地址连续的内存空间
>
>   <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge658yzk7bj30ni05yq56.jpg" alt="CMS堆内存结构" style="zoom:80%;" />
>
> - G1：逻辑连续的内存块
>
>   <img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge659sz6ewj30mw08qdis.jpg" alt="G1堆内存结构" style="zoom:80%;" />
>
>   Region的大小：使用`-XX:G1HeapRegionSize`指定，可选1,2,4,8,16,32M。
>
>   默认`MIN_REGION_SIZE(1024*1024)`、`MAX_REGION_SIZE(32*1024*1024)`、`TARGET_REGION_NUMBER(2048)`

G1垃圾收集器-GC模式

> - young gc：年轻代的GC算法、拷贝到survivor region、晋升到old region
>
> - mixed gc：避免堆内存耗尽、不是old gc,而是young+old region、是部分,不是全部老年代
>
>   老年代占用的内存大小超过堆内存的阈值时会触发mixed gc
>
> - full gc：对象分配速度过快、old region被填满、单线程执行
>
>   对象内存分配过快，mixed gc来不及清理，导致old region被沾满会触发full gc

G1垃圾收集器-属性总结

> “服务器风格”的垃圾回收器：并行和并发、分代处理、紧凑内存(碎片整理)、预见性

G1垃圾收集器-应用场景

> G1的应用场景：垃圾收集与应用线程并发执行、空闲内存压缩暂停时间短、可预测的GC暂停时间、不需要很大的Java堆

### 4-5 JDK12 其他新特性 (03:17)




## 第5章 课程总结

> 本章中首先对课程中介绍的 JDK11、12 进行总结说明，之后分析怎样去选择 JDK 的版本，最后给出学习 Java 相关的建议。

### 5-1 课程总结 (05:50)

JDK11：ZGC、HttpClient、String

JDK12：G1增强、switch
