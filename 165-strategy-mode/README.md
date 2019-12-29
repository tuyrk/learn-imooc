# 模式的秘密--策略模式

> http://www.imooc.com/learn/165
>
> 简介：策略模式是在日常开发中使用最为广泛的设计模式之一。在本视频中，@Arthur 将用简单生动的例子带你领略策略模式的真意、如何用 Java 语言实现策略模式、最后还将用实际的行业案例告诉你策略模式的强大功能。小伙伴们，还等什么呢，快来加入学习吧！！

## 第1章 引子：什么是策略模式
> 本章通过分析字处理软件处理排版和在线购物支付的场景，引入策略模式的概念。针对概念设计了一个虚拟的应用，分析应用需求。

### 1-1 策略模式简介 (06:27)

课程大纲

> 什么是策略模式
> 策略模式如何实现
> 策略模式总结篇：适用场景、优缺点
> 实例案例分享

日常生活中的策略

> Word文档中的排版布局方式
> 购物支付过程中选择支付渠道

案例示意图

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gadlrfqla3j319i0pknmh.jpg" alt="案例示意图" style="zoom:35%;" />

策略模式的定义

> 策略模式将可变的部分从程序中抽象分离成算法接口，在该接口下分别封装一系列算法实现，并使他们可以相互替换，从而导致客户端程序独立于算法的改变。

## 第2章 光荣与梦想——鸭子应用：策略模式的实现
> 通过代码的编写，剖析策略模式的实现

### 2-1 光荣与梦想：模拟应用背景介绍 (02:49)

模拟应用背景

> 通过代码来实现策略模式，通过开发与重构虚拟应用。使用策略模式实现：在不变更代码框架的前提下，通过开发与重构，使用策略模式，不断拥抱需求的变化，从而满足客户需求

项目背景：鸭子应用

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gadlyz4j7yj31dy0kpk8w.jpg" alt="项目背景：鸭子应用" style="zoom:30%;" />

### 2-2 求索之路：鸭子如何才能飞 (07:05)

> 原有功能

代码编写

1. 超类，所有的鸭子都要继承此类。抽象了鸭子的行为：显示和鸣叫

   ```java
   public abstract class Duck {
       // 鸭子发出叫声。通用行为，由超类实现
       public void quack() {
           System.out.println("嘎嘎嘎...");
       }
   
       // 显示鸭子的外观。鸭子的外观各不相同，声明为abstract，由子类实现
       public abstract void display();
   }
   ```

2. 绿头鸭

   ```java
   public class MallardDuck extends Duck {
       @Override
       public void display() {
           System.out.println("我的头是绿色的");
       }
   }
   ```

3. 红头鸭

   ```java
   public class RedHeadDuck extends Duck {
       @Override
       public void display() {
           System.out.println("我的头是红色的");
       }
   }
   ```

### 2-3 柳暗花明：策略让鸭子飞上天 (07:13)

什么是组合

> **在类中增加一个私有域，引用另一个已有的类的实例，通过调用引用实例的方法从而获得新的功能，这种设计被称作组合（或复合）**



在原有功能的基础上新增飞行的功能

1. 继承。在父类中提供实现方法，子类通过继承获得父类中的飞行行为

   ```java
   public void fly(){
       System.out.println("用翅膀飞行");
   }
   ```

   优点：简单易用，已有应用可以快速添加飞行的能力

   缺点：粗暴丑陋，不具有灵活性，对未来变更支持差

2. 抽象方法。在父类中提供抽象方法，强迫子类是自己的飞行行为

   ```java
   public abstract void fly();
   ```

   优点：足够灵活。不会忘记复写代码

   缺点：每个子类都要实现一遍代码，即使是相同的行为也不例外。代码重复，没有复用代码。如果有一个BUG，修改相当麻烦。

   **继承是重用代码的利器，但继承并不总是最好的工具**

3. 组合。将飞行行为抽象为接口，在父类中持有该接口，并由该接口代理飞行行为

   ```java
   public interface FlyingStrategy {
       void performFly();
   }
   ```

   ```java
   private FlyingStrategy flyingStrategy;
   public void fly() {
       flyingStrategy.performFly();
   }
   ```

   优点：足够灵活。复用代码，更易于维护

   缺点：策略类会增多，所有策略类都需要对外暴露

   Favor composition over inheritance
   **复合优先于继承**/**多用组合，少用继承**

### 2-4 脚踏实地：用代码放飞鸭子 (07:00)

> 添加策略接口，实现鸭子飞行

代码编写

1. 策略接口，实现鸭子的飞行行为

   ```java
   public interface FlyingStrategy {
       // 飞行行为的方法
       void performFly();
   }
   ```

2. 实现鸭子的飞行行为，用翅膀飞行的类

   ```java
   public class FlyWithWin implements FlyingStrategy {
       @Override
       public void performFly() {
           System.out.println("振翅高飞");
       }
   }
   ```

3. 超类，所有的鸭子都要继承此类。抽象了鸭子的行为：显示和鸣叫

   ```java
   @Data
   public abstract class Duck {
       // 鸭子发出叫声，通用行为，由超类实现
       public void quack() {
           System.out.println("嘎嘎嘎...");
       }
       // 显示鸭子的外观，鸭子的外观各不相同，声明为abstract，由子类实现
       public abstract void display();
   
       /*=====新增代码实现飞行功能=====*/
       // 组合进飞行的策略接口
       private FlyingStrategy flyingStrategy;
       
       // 鸭子的飞行功能
       public void fly() {
           // 由飞行策略接口执行飞行的动作
           flyingStrategy.performFly();
       }
   }
   ```
   
4. 绿头鸭。传入飞行策略

   ```java
   public class MallardDuck extends Duck {
       public MallardDuck() {
           super.setFlyingStrategy(new FlyWithWin());
       }
   
       @Override
       public void display() {
           System.out.println("我的头是绿色的");
       }
   }
   ```
   
5. 红头鸭。传入飞行策略

   ```java
   public class RedHeadDuck extends Duck {
       public RedHeadDuck() {
           super.setFlyingStrategy(new FlyWithWin());
       }
   
       @Override
       public void display() {
           System.out.println("我的头是红色的");
       }
   }
   ```
   
6. 测试类

   ```java
   public class DuckTest {
       public static void main(String[] args) {
           // Duck duck = new MallardDuck();
           // Duck duck = new RedHeadDuck();
           Duck duck = new SpaceDuck();
           
           duck.display();
           duck.quack();
           duck.fly();
       }
   }
   ```

### 2-5 拥抱变化：用策略模式提供高复用性代码 (06:53)

> 复用代码，新增策略方式

业务场景

> 需要增加橡胶鸭、大黄鸭，它们都有一个共同的特点，不会飞行。
> 不会飞也是一种飞行行为，所以可以通过良好的抽象复用代码

代码编写

1. 实现鸭子的飞行行为，不会飞行的策略类

   ```java
   public class FlyNoWay implements FlyingStrategy {
       @Override
       public void performFly() {
           System.out.println("我不会飞行！");
       }
   }
   ```

2. 橡胶鸭

   ```java
   public class RubberDuck extends Duck {
       public RubberDuck() {
           super.setFlyingStrategy(new FlyNoWay());
       }
   
       @Override
       public void display() {
           System.out.println("我全身发黄，嘴巴很红");
       }
       @Override
       public void quack() {
           System.out.println("嘎~嘎~嘎~");
       }
   }
   ```
   
3. 大黄鸭

   ```java
   public class BigYellowDuck extends Duck {
       public BigYellowDuck() {
           super.setFlyingStrategy(new FlyNoWay());
       }
   
       @Override
       public void display() {
           System.out.println("我身体很大，全身黄黄");
       }
   }
   ```
   
4. 测试类

   ```java
   public class DuckTest {
       public static void main(String[] args) {
           // Duck duck = new RubberDuck();
           Duck duck = new BigYellowDuck();
           duck.display();
           duck.quack();
           duck.fly();
       }
   }
   ```

### 2-6 万变不离其宗：向宇宙进军 (04:33)

>  新增策略方式

业务场景

> 现在又需增加太空鸭，太空鸭不能自己飞行，需要借助先进的科学技术。

代码编写

1. 实现鸭子的飞行行为，使用火箭飞行策略类

   ```java
   public class FlyWithRocket implements FlyingStrategy {
       @Override
       public void performFly() {
           System.out.println("用火箭在太空遨游");
       }
   }
   ```

2. 太空鸭

   ```java
   public class SpaceDuck extends Duck {
       public SpaceDuck() {
           super();
           super.setFlyingStrategy(new FlyWithRocket());
       }
   
       @Override
       public void display() {
           System.out.println("我头戴宇航盔");
       }
       @Override
       public void quack() {
           System.out.println("我通过无线电与你通信");
       }
   }
   ```
   
3. 测试类

   ```java
   public class DuckTest {
       public static void main(String[] args) {
           Duck duck = null;
           duck = new SpaceDuck();
           duck.display();
           duck.quack();
           duck.fly();
       }
   }
   ```
   

## 第3章 总结篇
> 本章在概念引入和代码实战的基础上，结合代码中的细节总结了策略模实现要素，适用场景，优缺点。最后通过一个行业案例分享，介绍了实际工作中的策略模式应用。

### 3-1 知识点总结 (06:37)

策略模式中的设计原则

> 找出应用中需要变化的部分，把他们独立出来，不要和那些不需要变化的代码混在一起
> 面向接口编程，而不是面向实现编程
> 多用组合，少用继承

策略模式的实现

> 通过分离变化得出的策略接口Strategy
> Strategy接口的实现类
> 客户程序中有一个Strategy
> 在客户程序中选择和组装正确的Strategy实现类



策略模式优点

> 使用了组合，使架构更加灵活
> 富有弹性，可以较好的应对变化（开闭原则）
> 更好的代码复用性（相对于继承）
> 消除大量的条件语句

策略模式缺点

> 客户代码需要了解每个策略实现的细节
> 增加了对象的数目



策略模式的适用场景

> 许多相关的类仅仅是行为差异
> 运行时选取不同的算法变体
> 通过条件语句在多个分支中选择其中一个