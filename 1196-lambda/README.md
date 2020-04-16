# 一课掌握Lambda表达式语法及应用

> http://www.imooc.com/learn/1196
>
> 简介：如何应对传统项目开发中遇到的代码冗余的问题？如何提升开发效率？如何精简代码？学会Lambda必不可少~ Lambda表达式是函数式编程思想的一种体现，本课程先讲解了Lambda表达式基本操作语法和底层原理，再结合Stream完成项目重构，帮助你夯实Lambda表达式基础，轻松应对工作和面试。另外，JDK8以后，源码中开始应用Lambda表达式，学习它对以后钻研源码非常重要。

## 第1章 课程介绍

> 理清本课程学习目的，对lambda基础有简单的认识。

### 1-1 课程介绍 (02:25)

> - 什么是lambda表达式
> - lambda表达式基础知识
> - lambda表达式高级扩展

<img src="https://tva1.sinaimg.cn/large/00831rSTgy1gdk97i0rdaj312v0jfgq1.jpg" alt="课程介绍" style="zoom:30%;" />

## 第2章 Java为什么引入Lambda表达式

> 从实际问题入手，介绍lambda表达对于程序优化带来的好处

### 2-1 为什么引入Lambda表达式 (08:44)

内容介绍

> - 什么是Lambda
> - Model Code As Data
> - 功能接口设计及优化
> - 为什么要使用Lambda表达式

什么是Lambda

> - Lambda表达式也被称为箭头函数、匿名函数、闭包
> - Lambda表达式体现的是轻量级函数式编程思想
> - `->`符号是Lambda表达式核心操作符号，符号左侧是操作参数，符号右侧是操作表达式
> - JDK8新特性

Model Code as Data

> - Model Code as Data，编码及数据，尽可能轻量级的将代码封装为数据
> - 解决方案：接口&实现类（匿名内部类）
> - 存在问题：语法冗余、this关键字、变量捕获、数据控制等

项目问题：功能接口的设计及优化

> 需求环境：线程类的创建
> 解决方案：匿名内部类实现
> 解决方案：Lambda表达式实现

1. 传统模式下，新线程的创建

   ```java
   new Thread(new Runnable() {
       @Override
       public void run() {
           System.out.println("threading..." + Thread.currentThread().getId());
       }
   }).start();
   ```

2. JDK8新特性，Lambda表达式优化线程模式

   ```java
   new Thread(() -> {
     System.out.println("lambda threading..." + Thread.currentThread().getId());
   }).start();
   ```

为什么要使用Lambda表达式

> - 他不是解决未知问题的新技术
> - 对现有解决方案的语义化优化
> - 需要根据实际需求考虑性能问题

## 第3章 Lambda表达式的基础知识

> 什么是函数式接口、Lambda表达式的基础语法、方法重载与Lambda表达式以及底层构建原理。

### 3-1 函数式接口概述和定义 (05:19)

内容介绍

> - 函数式接口的概念和使用方式
> - Lambda语法及使用
> - Lambda表达式运行原理

函数是接口（function interface）

> - 函数式接口，就是Java类型系统中的接口
> - 函数式接口，是只包含一个接口方法的特殊接口
> - 语义化检测注解：`@FunctionalInterface`

1. 用户身份认证标记接口

   ```java
   @FunctionalInterface
   public interface IUserCredential {
     /**
      * 通过用户账号，验证用户身份信息的接口
      *
      * @param username 要验证的用户账号
      * @return 返回身份信息[系统管理员、用户管理员、普通用户]
      */
     String verifyUser(String username);
   }
   ```

2. 消息传输格式化转换接口

   ```java
   @FunctionalInterface
   public interface IMessageFormat {
     /**
      * 消息转换方法
      *
      * @param message 要转换的消息
      * @param format  转换的格式[xml/json]
      * @return 返回转换后的数据
      */
     String format(String message, String format);
   }
   ```

### 3-2 默认方法和静态方法 (09:16)

> - 默认接口方法
> - 静态接口方法
> - 来自Object继承的方法

1. 用户身份认证标记接口

   ```java
   @FunctionalInterface
   public interface IUserCredential {
     // 通过用户账号，验证用户身份信息的接口
     String verifyUser(String username);
   
     // boolean test();
     @Override
     String toString();
   
     default String getCredential(String username) {
       // 模拟方法
       if ("admin".equals(username)) {
         return "admin + 系统管理员用户";
       } else if ("manager".equals(username)) {
         return "manager + 用户管理员用户";
       }
       return "commons + 普通会员用户";
     }
   }
   ```

   ```java
   public class UserCredentialImpl implements IUserCredential {
     @Override
     public String verifyUser(String username) {
       if ("admin".equals(username)) {
         return "系统管理员";
       } else if ("manager".equals(username)) {
         return "用户管理员";
       }
       return "普通会员";
     }
   }
   ```

2. 消息传输格式化转换接口

   ```java
   @FunctionalInterface
   public interface IMessageFormat {
     // 消息转换方法
     String format(String message, String format);
   
     // 消息合法性验证方法
     static boolean verifyMessage(String msg) {
       return msg != null;
     }
   }
   ```

   ```java
   public class MessageFormatImpl implements IMessageFormat {
     @Override
     public String format(String message, String format) {
       System.out.println("消息转换...");
       return message;
     }
   }
   ```

3. 测试类

   ```java
   public static void main(String[] args) {
     IUserCredential ic = new UserCredentialImpl();
     System.out.println(ic.verifyUser("admin"));
     System.out.println(ic.getCredential("admin"));
   
     String msg = "hello world";
     if (IMessageFormat.verifyMessage(msg)) {
       IMessageFormat format = new MessageFormatImpl();
       System.out.println(format.format("hello", "json"));
     }
   }
   ```

### 3-3 Lambda表达式和函数式接口的关系 (04:21)

函数式接口(function interface)

> - 函数式接口，只包含一个操作方法
> - Lambda表达式，只能操作一个方法
> - Java中的Lambda表达式核心就是一个函数式接口

1. 匿名内部类，实现接口的抽象方法

   ```java
   IUserCredential ic2 = new IUserCredential() {
     @Override
     public String verifyUser(String username) {
       return "admin".equals(username) ? "管理员" : "会员";
     }
   };
   System.out.println(ic2.verifyUser("manager"));
   System.out.println(ic2.verifyUser("admin"));
   ```

2. Lambda表达式，针对函数式接口的简单实现

   ```java
   IUserCredential ic3 = (username) -> {
     return "admin".equals(username) ? "lbd管理员" : "lbd会员";
   };
   System.out.println(ic3.verifyUser("manager"));
   System.out.println(ic3.verifyUser("admin"));
   ```

### 3-4 jdk中常见的函数式接口 (14:23)

Java类型系统内建函数式接口

> - java.lang.Runable
> - Java.lang.Comparable
> - java.lang.Comparator
> - java.io.FileFilter

JDK8提供了java.util.function包，提供了常用的函数式功能接口

| 接口           | 功能                                             |
| -------------- | ------------------------------------------------ |
| Predicate      | 接收参数T对象，返回一个boolean类型结果           |
| Consumer       | 接收参数T对象，没有返回值                        |
| Function       | 接收参数T对象，返回R对象                         |
| Supplier       | 不接受任何参数，直接通过get()获取指定类型的对象  |
| UnaryOperator  | 接收参数T对象，执行业务处理后，返回更新后的T对象 |
| BinaryOperator | 接收两个T对象，执行业务处理后，返回一个T对象     |

1. java.util.function.Predicate<T>

   接收参数对象T，返回一个boolean类型结果

   ```java
   Predicate<String> pre = (String username) -> {
     return "admin".equals(username);
   };
   System.out.println(pre.test("manager"));
   System.out.println(pre.test("admin"));
   ```

2. java.util.function.Comsumer<T>

   接收参数对象T，不返回结果

   ```java
   Consumer<String> con = (String message) -> {
     System.out.println("要发送的消息：" + message);
     System.out.println("消息发送完成");
   };
   con.accept("hello world");
   con.accept("lambda expression");
   ```

3. java.util.function.Function<T, R>

   接收参数对象T，返回结果对象R

   ```java
   Function<String, Integer> fun = (String gender) -> {
     return "male".equals(gender) ? 1 : 0;
   };
   System.out.println(fun.apply("male"));
   System.out.println(fun.apply("female"));
   ```

4. java.util.function.Supplier<T>

   不需要接收参数，提供T对象的创建工厂

   ```java
   Supplier<String> sup = () -> {
     return UUID.randomUUID().toString();
   };
   System.out.println(sup.get());
   System.out.println(sup.get());
   System.out.println(sup.get());
   ```

5. java.util.function.UnaryOperator<T>

   接收参数对象T，返回结果对象T

   ```java
   UnaryOperator<String> uo = (String img) -> {
     img += "[100x200]";
     return img;
   };
   System.out.println(uo.apply("原图--"));
   ```

6. java.util.functionBinaryOperator<T>

   接收两个T对象，返回一个T对象结果

   ```java
   BinaryOperator<Integer> bo = (Integer i1, Integer i2) -> {
     return i1 > i2 ? i1 : i2;
   };
   System.out.println(bo.apply(12, 13));
   ```


### 3-5 Lambda表达式基本语法 (10:17)

> - Lambda表达式基本语法
> - 带参数的Lambda表达式
> - 带返回值的Lambda表达式

Lambda表达式的基本语法

1. 声明：就是和Lambda表达式绑定的接口类型

2. 参数：包含在一对圆括号中，和绑定的接口中的抽象方法中的参数个数及顺序一致

3. 操作符：->

4. 执行代码块：包含在一对大括号中，出现在操作符号的右侧

   ```
   [接口声明] = (参数) -> {指定代码块}
   ```

> 1. lambda表达式，必须和接口进行绑定
> 2. lambda表达式的参数，可以附带0个到n个参数，括号中的参数类型可以不用指定，JVM在运行时会自动根据绑定的抽象方法中的参数进行推导
> 3. lambda表达式的返回值，如果代码块只有一行，并且没有大括号，不用写return关键字，单行代码的执行结果会自动返回。如果添加了大括号，或者有多行代码，必须通过return关键字返回执行结果

1. 没有参数，没有返回值的Lambda表达式

   ```java
   interface ILambda1 {
     void test();
   }
   ```

   ```java
   ILambda1 i1 = () -> {
     System.out.println("hello lambda");
     System.out.println("welcome to lambda");
   };
   i1.test();
   
   ILambda1 i12 = () -> System.out.println("hello");
   i12.test();
   ```

2. 带有参数，没有返回值的Lambda表达式

   ```java
   interface ILambda2 {
     void test(String name, int age);
   }
   ```

   ```java
   ILambda2 i2 = (String name, int age) -> {
     System.out.println(name + " age: my year's old is " + age);
   };
   i2.test("jerry", 18);
   
   ILambda2 i22 = (name, age) -> {
     System.out.println(name + " age: my year's old is " + age);
   };
   i22.test("tom", 22);
   ```

3. 带有参数，带有返回值的Lambda表达式

   ```java
   interface ILambda3 {
     int test(int x, int y);
   }
   ```

   ```java
   ILambda3 i3 = (x, y) -> {
     int z = x + y;
     return z;
   };
   System.out.println(i3.test(11, 22));
   
   ILambda3 i32 = (x, y) -> x + y;
   System.out.println(i32.test(100, 200));
   
   ILambda3 i33 = Integer::sum;
   System.out.println(i33.test(23, 32));
   ```

### 3-6 变量捕获-变量的访问操作 (06:17)

> - 匿名内部类中的变量捕获
> - Lambda表达式中的变量捕获

1. 匿名内部类型中对于变量的访问

   ```java
   public void testInnerClass() {
     String s2 = "局部变量";
   
     new Thread(new Runnable() {
       String s3 = "内部变量";
   
       @Override
       public void run() {
         // 1. 访问全局变量
         // System.out.println(this.s1); // this关键字~表示的是当前内部类型的对象
         System.out.println(s1);
   
         // 2. 局部变量的访问
         System.out.println(s2);
         // s2 = "hello"; // 不能对局部变量进行数据的修改[final]
   
         // 3. 内部变量的访问
         System.out.println(s3);
         System.out.println(this.s3);
       }
     }).start();
   }
   ```

2. Lambda表达式变量捕获

   ```java
   public void testLambda() {
     String s2 = "局部变量Lambda";
     new Thread(() -> {
       String s3 = "内部变量Lambda";
   
       // 1. 访问全局变量
       System.out.println(this.s1); // this关键字~表示的是所属方法所在类型的对象
   
       // 2. 访问局部变量
       System.out.println(s2);
       // s2 = "hello"; // 不能进行数据修改，默认推导变量的修饰符：final
   
       // 3. 访问内部变量
       System.out.println(s3);
       s3 = "lambda内部变量直接修改";
       System.out.println(s3);
     }).start();
   }
   ```

总结：Lambda表达式中的变量操作优化了匿名内部类中的this关键字，不再单独建立对象作用域，表达式本身就是所属对象类型的一部分，在语法语义上使用更加简洁

### 3-7 Lambda表达式类型检查 (07:18)

> - 表达式类型检查
> - 参数类型检查

1. 接口

   ```java
   @FunctionalInterface
   interface MyInterface<T, R> {
     R strategy(T t, R r);
   }
   ```

2. 测试方法

   ```java
   public static void test(MyInterface<String, List> inter) {
     List list = inter.strategy("hello", new ArrayList());
     System.out.println(list);
   }
   ```

3. 调用方法

   - 匿名内部类

     ```java
     test(new MyInterface<String, List>() {
       @Override
       public List strategy(String s, List list) {
         list.add(s);
         return list;
       }
     });
     ```

   - Lambda表达式

     ```java
     test((x, y) -> {
       y.add(x);
       return y;
     });
     ```

- 表达式类型检查

  ```
  (x, y) -> {...} ==> test(param) ==> param = MyInterface ==> lambda表达式 ==> MyInterface类型
  ```

  Lambda表达式的类型检查：Lambda表达式`(x, y) => {...}`，当将它交给test作为param参数时，JVM会推导param是Interface类型的参数，所以当前的Lambda表达式就属于MyInterface类型。MyInterface接口就是Lambda表达式的目标类型(target typing)

- 参数类型检查

  ```
  (x, y) -> {...} ==> MyInterface(T t, R r) ==> MyInterface<String, List> inter ==> T=String, R=List ==> lambda ==> (x, y) == strategy(T t, R r) ==> x=T=String, y=R=List
  ```

### 3-8 方法重载和Lambda表达式 (05:00)

> - Java类型系统中的方法重载
> - 方法重载的实现
> - 当方法重载遇上Lambda表达式

1. 接口方法

   ```java
   interface Param1 {
     void outInfo(String info);
   }
   ```

   ```java
   interface Param2 {
     void outInfo(String info);
   }
   ```

2. 定义重载方法

   ```java
   public class App38 {
     public void lambdaMethod(Param1 param) {
       param.outInfo("hello param1 lambda");
     }
   
     public void lambdaMethod(Param2 param) {
       param.outInfo("hello param2 lambda");
     }
   }
   ```

3. 方法调用

   ```java
   App38 app38 = new App38();
   app38.lambdaMethod(new Param1() {
     @Override
     public void outInfo(String info) {
       System.out.println(info);
     }
   });
   app38.lambdaMethod(new Param2() {
     @Override
     public void outInfo(String info) {
       System.out.println(info);
     }
   });
   
   app38.lambdaMethod((Param2) (info) -> {
     System.out.println(info);
   });
   app38.lambdaMethod((Param2) System.out::println);
   ```

   ```
   lambda表达式存在类型检查 => 自动推导lambda表达式的目标类型
   lambdaMethod() => 方法 => 重载方法
   		=> Param1 函数式接口
   		=> Param2 函数式接口
          调用方法 => 传递Lambda表达式 => 自动推导 => Param1 | Param2
   ```

### 3-9 Lmabda表达式底层构建原理 (06:19)

> - Lambda表达式底层解析运行原理
> - Lambda表达式在JVM底层解析成私有静态方法和匿名内部类型
> - 通过实现接口的匿名内部类型中接口方法调用静态实现方法，完成Lambda表达式的执行

1. 源码

   ```java
   public class App39 {
     public static void main(String[] args) {
       IMarkUp mu = (message) -> System.out.println(message);
       mu.markUp("Lambda!");
     }
   }
   
   interface IMarkUp {
     void markUp(String msg);
   }
   ```

2. 编译源代码

   ```shell
   javac App39.java
   ```

3. 反编译查看编译后的代码

   ```shell
   javap -p App39
   ```

   ```java
   public class App39 {
     public App39();
     public static void main(java.lang.String[]);
     private static void lambda$main$0(java.lang.String);
   }
   ```

4. 查看底层详细编译过程

   ```shell
   java -Djdk.internal.lambda.dumpProxyClasses App39
   ```

   ```shell
   javap -p App39\$\$Lambda\$1
   ```

   ```java
   final class App39$$Lambda$1 implements IMarkUp {
     private App39$$Lambda$1();
     public void markUp(java.lang.String);
   }
   ```

5. 最终运行源码

   ```java
   public class App39 {
     public static void main(String[] args) {
       new App39$$Lambda$1().markUp("Lambda!");
     }
     private static void lambda$main$0(java.lang.String message){
       System.out.println(message);
     }
   
     final class App39$$Lambda$1 implements IMarkUp {
       private App39$$Lambda$1() { }
       public void markUp(java.lang.String msg) {
         App39.lambda$main$0(msg);
       }
     }
   }
   
   interface IMarkUp {
     void markUp(String msg);
   }
   ```

## 第4章 Lambda表达式在集合中的运用

> 介绍方法引用、Java Stream 如何操作集合及底层原理。

### 4-1 方法引用 (12:19)

> - 方法引用
> - Stream API
> - Stream操作原理
> - 操作集合元素

Lambda表达式方法引用

> - 方法引用是结合Lambda表达式的一种语法特性
> - 静态方法引用、实例方法引用、构造方法引用

1. Person类型

   ```java
   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   class Person {
     private String name;// 姓名
     private String gender;// 性别
     private int age;//年龄
   
     // 静态方法
     public static int compareByAge(Person p1, Person p2) {
       return p1.getAge() - p2.getAge();
     }
     // 实例方法
     public int compareByName(Person p1, Person p2) {
       return p1.getName().hashCode() - p2.getName().hashCode();
     }
   }
   
   @FunctionalInterface
   interface IPerson {
     // 抽象方法：通过指定类型的构造方法初始化对象数据
     Person initPerson(String name, String gender, int age);
   }
   ```

2. 存储Person对象的列表

   ```java
   List<Person> personList = new ArrayList<>();
   personList.add(new Person("tom", "男", 16));
   personList.add(new Person("jerry", "女", 15));
   personList.add(new Person("shuke", "男", 30));
   personList.add(new Person("beita", "女", 26));
   personList.add(new Person("damu", "男", 32));
   ```

3. 进行排序

   - 匿名内部类实现方式

     ```java
     Collections.sort(personList, new Comparator<Person>() {
       @Override
       public int compare(Person o1, Person o2) {
         return o1.getAge() - o2.getAge();
       }
     });
     ```

   - Lambda表达式实现方式

     ```java
     Collections.sort(personList, (o1, o2) -> {
       return o1.getAge() - o2.getAge();
     });
     ```

   - 静态方法引用

     ```java
     Collections.sort(personList, Person::compareByAge);
     ```

   - 实例方法引用

     ```java
     Person p = new Person();
     Collections.sort(personList, p::compareByName);
     ```

   - 构造方法引用：绑定函数式接口

     ```java
     IPerson ip = Person::new;
     Person person = ip.initPerson("jerry", "男", 22);
     ```

总结：

1. 静态方法引用的使用

  类型名称.方法名称() => 类型名称::静态方法名称

2. 实例方法引用的使用

  创建类型对应的一个对象 => 对象应用::实例方法名称

3. 构造方法引用：绑定函数式接口

  需要有全参构造方法

### 4-2 Stream概述 (05:16)

> - 什么是Stream
> - Stream的作用

1. 添加测试数据：存储多个账号的列表

   ```java
   List<String> accounts = new ArrayList<>();
   accounts.add("tom");
   accounts.add("jerry");
   accounts.add("beita");
   accounts.add("shuke");
   accounts.add("damu");
   ```

2. 业务要求：长度大于等于5的有效账号

   - foreach循环

     ```java
     for (String account : accounts) {
       if (account.length() >= 5) {
         System.out.println("有效账号：" + account);
       }
     }
     ```

   - 迭代方式进行操作

     ```java
     Iterator<String> it = accounts.iterator();
     while (it.hasNext()) {
       String account = it.next();
       if (account.length() >= 5) {
         System.out.println("it有效账号：" + account);
       }
     }
     ```

   - Stream结合Lambda表达式，完成业务处理

     ```java
     List validAccounts = accounts.stream().filter(s -> s.length() >= 5).collect(Collectors.toList());
     System.out.println(validAccounts);
     ```

### 4-3 Stream API (09:27)

> - Stream聚合操作
> - API：intermediate中间/记录操作【无状态|有状态】
> - API：terminal终结/结束操作【非短路|短路】

1. 聚合操作

2. Stream的处理流程

   数据源=>数据转换=>获取结果

3. 获取Stream对象

   1. 从集合或者数组中获取[**]

      ```java
      collection.stream(); // accounts.stream()
      collection.parallelStream();
      Arrays.stream(T t);
      ```

   2. BufferReader

      ```java
      java.io.bufferReader.lines();
      ```

   3. 静态工厂

      ```java
      java.util.stream.IntStream.range();
      java.nio.file.Files.walk();
      ```

   4. 自定义构建

      ```java
      java.util.Spliterator;
      ```

   5. 更多方式

      ```java
      random.ints();
      pattern.splitAsStream();
      ```

4. 中间操作API{intermediate}

   操作结果是一个Stream，中间操作可以有一个或多个连续的中间操作。

   需要注意的是，中间操作只是记录操作方式，不作具体执行，直到结束操作发生时，才做数据的最终执行

   中间操作就是业务逻辑处理

   中间操作过程：

   - 无状态：数据处理时，不受前置中间操作的影响

     ```java
     map/filter/peek/parallel/sequential/unorderd
     ```

   - 有状态：数据处理时，受到前置中间操作的影响

     ```java
     distinct/sorted/limit/skip
     ```

5. 终结操作|结束操作{terminal}

   需要注意：一个Stream对象，只能有一个terminal操作。这个操作一旦发生就会真实处理数据，生成对应的处理结果

   终结操作：

   - 非短路操作：当前的Stream对象必须处理完集合中所有数据，才能得到处理结果

     ```java
     forEach/forEachOrdered/toArray/reduce/collect/min/max/count/iterator
     ```

   - 短路操作（Short-circuiting）：当前的Stream对象在处理过程中，一旦满足某个条件，就可以得到结果

     ```java
     anyMatch/allMatch/noneMatch/findFirst/findAny等
     ```

     适用场景：无限大的Stream => 有限大的Stream

### 4-4 Stream操作集合中的数据-上 (13:55)

> - 类型转换：其他类型（创建/获取） => Stream对象
> - 类型转换：Stream对象 => 其他类型
> - Stream常见的API操作

1. 批量数据 => Stream对象

   - 多个数据

     ```java
     Stream<String> stream1 = Stream.of("admin", "tom", "damu");
     ```

   - 数组

     ```java
     String[] strArrays = {"xueqi", "biyao"};
     Stream<String> stream2 = Arrays.stream(strArrays);
     ```

   - 列表

     ```java
     List<String> list = new ArrayList<>();
     list.add("少林");
     list.add("武当");
     list.add("青城");
     list.add("崆峒");
     list.add("峨眉");
     Stream<String> stream3 = list.stream();
     ```

   - 集合

     ```java
     Set<String> set = new HashSet<>();
     set.add("少林罗汉拳");
     set.add("武当长拳");
     set.add("青城剑法");
     Stream<String> stream4 = set.stream();
     ```

   - Map

     ```java
     Map<String, Integer> map = new HashMap<>();
     map.put("tom", 1000);
     map.put("jerry", 1200);
     map.put("shuke", 1000);
     Stream<Map.Entry<String, Integer>> stream5 = map.entrySet().stream();
     ```

2. Stream对象对于基本数据类型的功能封装

   - int/long/double

     ```java
      IntStream.of(new int[]{10, 20, 30}).forEach(System.out::println);
     IntStream.of(10, 20, 30).forEach(System.out::println);
     IntStream.range(1, 5).forEach(System.out::println);
     IntStream.rangeClosed(1, 5).forEachOrdered(System.out::println);
     ```

3. Stream对象 => 转换得到指定的数据类型

   - 数组

     ```java
     String[] array1 = stream1.toArray(String[]::new); // [admin, tom, damu]
     ```

   - 字符串

     ```java
     String str1 = stream1.collect(Collectors.joining()); // admintomdamu
     String str2 = stream1.collect(Collectors.joining(" ")); // admin tom damu
     String str3 = stream1.collect(Collectors.joining(",", "^", "$")); // ^admin,tom,damu$
     ```

   - 列表

     ```java
     List<String> lists = stream1.collect(Collectors.toList()); // [admin, tom, damu]
     ```

   - 集合

     ```java
     Set<String> sets = stream1.collect(Collectors.toSet()); // [tom, admin, damu]
     ```

   - Map

     ```java
     Map<String, String> maps = stream1.collect(Collectors.toMap(x -> x, y -> "value:" + y)); // {tom=value:tom, admin=value:admin, damu=value:damu}
     ```

### 4-5 Stream操作集合中的数据-下 (13:29)

4. Stream中常见的API操作

   ```java
   List<String> accountList = new ArrayList<>();
   accountList.add("songjiang");
   accountList.add("lujunyi");
   accountList.add("wuyong");
   accountList.add("linchong");
   accountList.add("luzhishen");
   accountList.add("likui");
   accountList.add("wusong");
   ```

   - map() 中间操作，map()方法接收一个Functional接口

     ```java
     accountList = accountList.stream().map(x -> "梁山好汉：" + x).collect(Collectors.toList()); // 在每个名字前添加"梁山好汉"
     ```

   - filter() 添加过滤条件，过滤符合条件的用户

     ```java
     accountList = accountList.stream().filter(x -> x.length() > 5).collect(Collectors.toList()); // 过滤名字长度大于5的名字
     ```

   - forEach增强型循环

     ```java
     accountList.forEach(x -> System.out.println("forEach:" + x));
     ```

   - peek()中间操作，迭代数据完成数据的依次处理过程

     ```java
     accountList.stream()
                     .peek(x -> System.out.println("peek 1:" + x))
                     .peek(x -> System.out.println("peek 2:" + x))
                     .forEach(x -> System.out.println("forEach:" + x));
     ```

5. Stream中对于数字运算的支持

   ```java
   List<Integer> intList = new ArrayList<>();
   intList.add(20);
   intList.add(19);
   intList.add(7);
   intList.add(8);
   intList.add(86);
   intList.add(11);
   intList.add(3);
   intList.add(20);
   ```

   - skip()中间操作，有状态，跳过部分数据

     ```java
     intList.stream().skip(3).forEach(System.out::println); // 8 86 11 3 20
     ```

   - limit()中间操作，有状态，限制输出数据量

     ```java
     intList.stream().skip(3).limit(2).forEach(System.out::println); // 8 86
     ```

   - distinct()中间操作，有状态，剔除重复的数据

     ```java
     intList.stream().distinct().forEach(System.out::println);
     ```

   - sorted()中间操作，有状态，排序。一般在skip/limit或者filter之后进行

   - max()获取最大值

     ```java
     // Optional<Integer> max = intList.stream().max((x, y) -> x - y);
     Optional<Integer> max = intList.stream().max(Comparator.comparingInt(x -> x));
     System.out.println(max.get()); // 86
     ```

   - min()获取最小值

   - reduce()合并处理数据

     ```java
     // Optional<Integer> reduce = intList.stream().reduce((sum, x) -> sum + x);
     Optional<Integer> reduce = intList.stream().reduce(Integer::sum);
     System.out.println(reduce.get()); // 174
     ```

## 第5章 Lambda表达式在实际生产中的应用

> 手把手使用lambda表达式对一个项目进行重构，分析性能问题、线程安全问题。最后给课程做一个总结。

### 5-1 Lambda表达式重构项目 (14:06)

1. ORM查询策略接口

   ```java
   @FunctionalInterface
   public interface IStrategy<T> {
     /**
      * 测试方法
      *
      * @param t 要测试的对象
      * @return 返回测试结果
      */
     boolean test(T t);
   }
   ```

2. 按照指定策略查询数据

   ```java
   @Override
   public List<Employee> findByStrategy(IStrategy strategy) {
     List<Employee> tempList = new ArrayList<>();
     for (Employee employee : LIST) {
       if (strategy.test(employee)) {
         tempList.add(employee);
       }
     }
     return tempList;
   }
   ```

3. 根据职员名称获取职员数据

   ```java
   public List<Employee> getEmployeeByName(String name) {
     return empDAO.findByStrategy((IStrategy<Employee>) employee -> employee.getEmpName().contains(name));
   }
   ```

Lambda表达式-代码重构

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1gdvx5g9jrtj30nm0aojuh.jpg" alt="Lambda表达式-代码重构" style="zoom:80%;" />

### 5-2 Lambda和Stream性能问题 (16:26)

1. 基本数据类型：整型

   ```java
   List<Integer> integerList = new ArrayList<>();
   for (int i = 0; i < 1000000; i++) {
     integerList.add(random.nextInt());
   }
   // 1) Stream 64ms
   // 2) parallelStream 5ms
   // 3) 普通for循环 14ms
   // 4) 普通foreach循环 12ms
   // 5) 增强型for循环 11ms
   // 6) 迭代器 11ms
   ```

   - Stream

     ```java
     public static int testStream(List<Integer> list) {
       return list.stream().max(Integer::compare).orElse(0);
     }
     ```

   - parallelStream

     ```java
     public static int testParallelStream(List<Integer> list) {
       return list.parallelStream().max(Integer::compare).orElse(0);
     }
     ```

   - 普通for循环

     ```java
     public static int testForLoop(List<Integer> list) {
       int max = Integer.MIN_VALUE;
       for (int i = 0; i < list.size(); i++) {
         Integer current = list.get(i);
         if (current > max) {
           max = current;
         }
       }
       return max;
     }
     ```

   - 普通foreach循环

     ```java
     public static int testForEach(List<Integer> list) {
       int max = Integer.MIN_VALUE;
       for (Integer current : list) {
         if (current > max) {
           max = current;
         }
       }
       return max;
     }
     ```

   - 增强型for循环

     ```java
     public static int testStrongLoop(List<Integer> list) {
       final int[] max = {Integer.MIN_VALUE};
       list.forEach(current -> {
         if (current > max[0]) {
           max[0] = current;
         }
       });
       return max[0];
     }
     ```

   - 迭代器

     ```java
     public static int testIterator(List<Integer> list) {
       int max = Integer.MIN_VALUE;
       Iterator<Integer> it = list.iterator();
       while (it.hasNext()) {
         Integer current = it.next();
         if (current > max) {
           max = current;
         }
       }
       return max;
     }
     ```

2. 复杂数据类型：对象

   ```java
   @Data
   @AllArgsConstructor
   class Product {
       private String name; // 名称
       private Integer stock; //库存
       private Integer hot; //热度
   }
   ```

   ```java
   List<Product> productList = new ArrayList<>();
   for (int i = 0; i < 1_000_000; i++) {
     productList.add(new Product("pro_" + i, i, random.nextInt()));
   }
   // 1) Stream 92ms
   // 2) parallelStream 30ms
   // 3) 普通for循环 16ms
   // 4) 普通foreach循环 17ms
   // 5) 增强型for循环 12ms
   // 6) 迭代器 16ms
   ```

   - Stream

     ```java
     public static Product testProductStream(List<Product> list) {
       return list.stream().max(Comparator.comparingInt(Product::getHot)).orElse(null);
     }
     ```

   - parallelStream

     ```java
     public static Product testProductParallelStream(List<Product> list) {
       return list.parallelStream().max(Comparator.comparingInt(Product::getHot)).orElse(null);
     }
     ```

   - 普通for循环

     ```java
     public static Product testProductForLoop(List<Product> list) {
       Product maxHot = list.get(0);
       for (int i = 0; i < list.size(); i++) {
         Product current = list.get(i);
         if (current.getHot() > maxHot.getHot()) {
           maxHot = current;
         }
       }
       return maxHot;
     }
     ```

   - 普通foreach循环

     ```java
     public static Product testProductForEach(List<Product> list) {
       Product maxHot = list.get(0);
       for (Product current : list) {
         if (current.getHot() > maxHot.getHot()) {
           maxHot = current;
         }
       }
       return maxHot;
     }
     ```

   - 增强型for循环

     ```java
     public static Product testProductStrongLoop(List<Product> list) {
       final Product[] maxHot = {list.get(0)};
       list.forEach(current -> {
         if (current.getHot() > maxHot[0].getHot()) {
           maxHot[0] = current;
         }
       });
       return maxHot[0];
     }
     ```

   - 迭代器

     ```java
     public static Product testProductIterator(List<Product> list) {
       Product maxHot = list.get(0);
       Iterator<Product> it = list.iterator();
       while (it.hasNext()) {
         Product current = it.next();
         if (current.getHot() > maxHot.getHot()) {
           maxHot = current;
         }
       }
       return maxHot;
     }
     ```

### 5-3 线程安全问题 (06:53)

Stream并行运行原理

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1gdvzx33xcpj30lr0b2754.jpg" alt="Stream并行运行原理" style="zoom:80%;" />

```java
List<Integer> list1 = new ArrayList<>();
for (int i = 0; i < 1000; i++) {
  list1.add(i);
}
System.out.println(list1.size());
```

- 串行Stream

  ```java
  List<Integer> list2 = new ArrayList<>();
  list1.forEach(list2::add);
  System.out.println(list2.size());
  ```

- 并行Stream，线程不安全

  ```java
  List<Integer> list3 = new ArrayList<>();
  list1.parallelStream().forEach(list3::add);
  System.out.println(list3.size());
  ```

- 解决并行Stream线程安全问题

  ```java
  List<Integer> list4 = list1.parallelStream().collect(Collectors.toList());
  System.out.println(list4.size());
  ```

### 5-4 课程总结 (00:59)

> - Lambda表达式
> - Stream对象，批量数据处理增强
> - 项目代码重构和并发性能问题
