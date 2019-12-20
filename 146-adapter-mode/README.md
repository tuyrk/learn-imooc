# 模式的秘密---适配器模式

> https://www.imooc.com/learn/146
>
> 简介：所谓适配器，就是连接“源”和“目标”的纽带。本课程由生活中常见的例子入手，深入浅出的讲解适配器模式的含义以及该模式的角色关系，讲解在适配器模式中如何通过“组合”和“继承”实现代码重用，为你的 Java 达人锻造添砖加瓦

## 第1章 适配器模式简介
> 通过生活中的适配器，引导大家了解什么是适配器模式

### 1-1 什么是适配器模式 (03:13)

生活中的适配器

> 翻译软件
> 插座适配器

适配器模式定义

> 适配器模式将一个类的接口，转换成客户期望的另一个接口。使得原本由于接口不兼容而不能一起工作的那些类可以在一起工作

适配器模式类图

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga3gitkzjoj30x40f2wmr.jpg" alt="适配器模式类图" style="zoom:40%;" />

适配器分类

> 1. 聚合（组合）
>    定义：采用聚合（组合）方式的适配器称为对象适配器
>    特点：把“被适配者”作为一个对象聚合到适配器类中，以修改目标接口包装被适配者
>
> 2. 继承
>    定义：采用继承方式的适配器称为类适配器
>    特点：通过多重继承不兼容接口，实现对目标接口的匹配，单一的为某个类而实现适配

## 第2章 适配器模式的实现
> 分别采用组合和继承的方式实现适配器模式，并讲解两种设计模式的区别

### 2-1 使用组合的方式实现插座适配器 (08:00)

怎们实现的？

1. 实现目标接口 ThreePlug
2. 聚合GbTwoPlug类到当前适配器类中为成员变量
3. 把"被适配者"作为一个对象聚合到适配器类中，以修改目标接口包装被适配者



代码编写

1. 三相插座接口ThreePlug.java

   ```java
   public interface ThreePlug {
       // 使用三相电流供电
       void powerWithThree();
   }
   ```

2. 二相插座接口GbTwoPlug.java

   ```java
   public class GbTwoPlug {
       // 使用二相电流供电
       public void powerWithTwo() {
           System.out.println("使用二相电流供电");
       }
   }
   ```

3. 二相转三相的插座适配器-聚合方式TwoPlugAdapter.java

   ```java
   public class TwoPlugAdapter implements ThreePlug{
       private GbTwoPlug twoPlug;
       public TwoPlugAdapter(GbTwoPlug twoPlug) {
           this.twoPlug = twoPlug;
       }
   
       @Override
       public void powerWithThree() {
           System.out.println("通过转换-聚合方式");
           twoPlug.powerWithTwo();
       }
   }
   ```

4. 笔记本电脑NoteBook.java

   ```java
   public class NoteBook {
       // 期望使用三相插座进行充电
       private ThreePlug threePlug;
       public NoteBook(ThreePlug threePlug) {
           this.threePlug = threePlug;
       }
   
       // 使用插座进行充电
       public void charge() {
           threePlug.powerWithThree();
       }
   
       public static void main(String[] args) {
           GbTwoPlug twoPlug = new GbTwoPlug();
           // 使用二相转三相的适配器
           ThreePlug threePlug = new TwoPlugAdapter(twoPlug);
           // 使用三相插座进行充电
           NoteBook noteBook = new NoteBook(threePlug);
           noteBook.charge();
       }
   }
   ```

### 2-2 使用继承的方式实现插座适配器 (05:10)

代码编写：

1. 二相转三相的插座适配器-继承方式TwoPlugAdapterExtends.java

   ```java
   public class TwoPlugAdapterExtends extends GbTwoPlug implements ThreePlug {
       @Override
       public void powerWithThree() {
           System.out.println("通过转换-继承方式");
           this.powerWithTwo();
       }
   }
   ```

2. 笔记本电脑NoteBook.java

   ```java
   public class NoteBook {
       // 期望使用三相插座进行充电
       private ThreePlug threePlug;
       public NoteBook(ThreePlug threePlug) {
           this.threePlug = threePlug;
       }
   
       // 使用插座进行充电
       public void charge() {
           threePlug.powerWithThree();
       }
   
       public static void main(String[] args) {
           ThreePlug threePlug = new TwoPlugAdapterExtends();
           NoteBook noteBook = new NoteBook(threePlug);
           noteBook.charge();
       }
   }
   ```

   

   类适配器

   > 类适配器模式通过多重继承不兼容接口，实现对目标接口的匹配，单一的为某个类而实现适配（Java类单一继承）

   <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga3hwd5cxaj30to0c6q8t.jpg" alt="类适配器" style="zoom:45%;" />

## 第3章 适配器模式总结
> 引入适配器的变体，启发灵活使用适配器。说明了适配器在开发中体现的作用

### 3-1 适配器模式的总结 (01:58)

适配器模式是一种编程思想

> 只要是把不兼容的转化成兼容的、匹配的，我们就叫它适配器

模式的变体

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga3h2mrlv3j30u60bejya.jpg" alt="模式的变体" style="zoom:45%;" />

适配器的作用

> 1. 透明：通过适配器，客户端可以调用同一个接口，因而对客户端来说是透明的。这样做更简单、更直接、更紧凑
>
> 2. 重用：复用了现存的类，解决了现存类和复用环境要求不一致的问题。
>
> 3. 低耦合：将**目标类**和**适配者类**解耦，通过引入一个适配器类重用现有的适配者类，而无需修改原有代码（遵循开闭原则）
>
>    开闭原则：对修改关闭，对扩展开放