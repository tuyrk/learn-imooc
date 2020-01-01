# 模式的秘密---模板方法模式

title: 145-模式的秘密---模板方法模式
date: 2019-12-29 22:04:13
categories: [慕课网]
tags: [设计模式]

---

> http://www.imooc.com/learn/145
>
> 简介：模板方法模式是在日常开发中使用最为广泛的设计模式之一。通过本视频，你可以了解什么是模板方法模式，如何用 Java 语言实现模板方法模式。@Arthur 将用简单生动的例子带你领略模板方法模式的真意，最后还将用实际的行业案例告诉你模板方法模式的强大功能。

@[TOC]

## 第1章 模板方法模式初探
> 本章通过实际生活中的例子引出模板方法模式的定义，通过咖啡与茶的故事，带领大家进入模板方法的世界

### 1-1 什么是模板方法模式 (09:36)

内容介绍

> 什么是模板方法模式
> 如何实现模板方法模式
> 模板方法模式的特点
> 模板方法模式在项目中的应用

模板方法模式

> 模板方法模式定义了一个操作的算法框架，而将一些步骤延迟到子类中实现，使得子类可以在不改变一个算法结构的同时就重新定义该算法的某些特定步骤。

案例介绍：提神饮料的配置模板

> 1. 把水煮沸（boil water）
> 2. 泡饮料（brew）
> 3. 把饮料倒进杯子（pour in cup）
> 4. 加调味料（add condiments）

## 第2章 模板方法模式的代码实现
> 本章通过 Java 代码具体演示了模板方法模式的实现

### 2-1 用抽象基类定义框架 (08:17)

代码编写

1. 抽象基类，为所有子类提供一个算法框架。提神饮料

   ```java
   public abstract class RefreshBeverage {
       // 制备饮料的模板方法
       // 封装了所有子类共同遵循的算法框架
       public final void prepareBeverageTemplate() {
           // 步骤一：将水煮沸
           boilWater();
           // 步骤二：泡制饮料
           brew();
           // 步骤三：将饮料倒入杯中
           pourInCup();
           // 步骤四：加入调味料
           addCondiments();
       }
   
       // 基本方法，将水煮沸
       private void boilWater() {
           System.out.println("将水煮沸");
       }
   
       // 抽象的基本方法，泡制饮料
       protected abstract void brew();
   
       // 基本方法，将饮料倒入杯中
       private void pourInCup() {
           System.out.println("将饮料倒入杯中");
       }
   
       // 抽象的基本方法，加入调味料
       protected abstract void addCondiments();
   }
   ```

### 2-2 具体子类实现延迟步骤 (08:31)

1. 具体子类，提供了咖啡的具体实现

   ```java
   public class Coffee extends RefreshBeverage {
       @Override
       protected void brew() {
           System.out.println("用沸水冲泡咖啡");
       }
   
       @Override
       protected void addCondiments() {
           System.out.println("加入糖和牛奶");
       }
   }
   ```

2. 具体子类，提供了制备茶的具体实现

   ```java
   public class Tea extends RefreshBeverage {
       @Override
       protected void brew() {
           System.out.println("用80度的热水浸泡茶叶5分钟");
       }
   
       @Override
       protected void addCondiments() {
           System.out.println("加入柠檬");
       }
   }
   ```

3. 测试类

   ```java
   public class RefreshReverageTest {
       public static void main(String[] args) {
           System.out.println("制备咖啡...");
           RefreshBeverage coffee = new Coffee();
           coffee.prepareBeverageTemplate();
           System.out.println("咖啡好了！");
   
           System.out.println("================");
   
           System.out.println("制备茶...");
           RefreshBeverage tea = new Tea();
           tea.prepareBeverageTemplate();
           System.out.println("茶好了！");
       }
   }
   ```

### 2-3 钩子使子类更灵活 (06:47)

业务场景

> 目前的提神饮料的步骤是固定的，当有些人不需要加入调味料时，使用钩子方法进行个性化扩展



代码编写

1. 抽象基类，为所有子类提供一个算法框架。提神饮料

   ```java
   public abstract class RefreshBeverage {
       // 制备饮料的模板方法
       // 封装了所有子类共同遵循的算法框架
       public final void prepareBeverageTemplate() {
           // 步骤一：将水煮沸
           boilWater();
           // 步骤二：泡制饮料
           brew();
           // 步骤三：将饮料倒入杯中
           pourInCup();
           // 步骤四：加入调味料
           if (isCustomerWantsCondiments()) {
               addCondiments();
           }
       }
   
       // hook方法（钩子函数）提供一个默认或空的实现，具体的子类可以自行决定是否挂钩以及如何挂钩
       // 询问用户是否加入调料
       protected boolean isCustomerWantsCondiments() {
           return true;// 默认需要加入调味料
       }
   
       // 基本方法，将水煮沸
       protected void boilWater() {
           System.out.println("将水煮沸");
       }
   
       // 抽象的基本方法，泡制饮料
       protected abstract void brew();
   
       // 基本方法，将饮料倒入杯中
       private void pourInCup() {
           System.out.println("将饮料倒入杯中");
       }
   
       // 抽象的基本方法，加入调味料
       protected abstract void addCondiments();
   }
   ```
   
2. 具体子类，提供了制备茶的具体实现

   ```java
   public class Tea extends RefreshBeverage {
       // 子类通过覆盖的形式选择挂载钩子函数
       @Override
       protected boolean isCustomerWantsCondiments() {
           return false;
       }
   
       @Override
       protected void brew() {
           System.out.println("用80度的热水浸泡茶叶5分钟");
       }
   
       @Override
       protected void addCondiments() {
           System.out.println("加入柠檬");
       }
   }
   ```

## 第3章 模板方法模式总结
> 本章总结了模板方法模式的实现要素，介绍了模板方法模式在行业中的应用案例。

### 3-1 模板方法模式总结 (10:12)

模板方法模式的实现要素分析

> 抽象基类
>
> 1. 基本方法：对各种具体子类而言是相同，具有共性的步骤
>
> 2. 抽象方法：只知道具体原则，而不知道实现细节，需要延迟到子类实现的步骤
>
> 3. 钩子方法：在基类中提供了一个默认或空的实现，子类可通过覆盖的形式选择挂载钩子函数
>
> 4. 模板方法：Template方法（final），将基本方法、抽象方法、钩子方法按照业务逻辑的要求，汇总而成一个模板方法。
>    一定要声明为final，以防止被子类复写。子类可替换父类中的可变逻辑，但不能改变整体逻辑结构
>
> 具体子类
>
> 1. 实现基类中的抽象方法：以提供个性化的、具体的、独特的实现
> 2. 覆盖钩子方法：更加个性化的影响模板方法局部的行为

模板方法模式的实现要素

> 准备一个抽象类，将部分逻辑以具体方法的形式实现，然后声明一些抽象方法交由子类实现剩余逻辑，用钩子方法给与子类更大的灵活性。最后将方法汇总构成一个不可改变的模板方法。

模板方法模式的适用场景

> 1. 算法或操作遵循相似的逻辑
> 2. 重构时（把相同的代码抽取到父类中）
> 3. 重要、复杂的算法，核心算法设计为模板算法

模板方法模式的优点

> 封装性好：封装了一个算法的框架，将算法的具体步骤封装成一个通用的模板方法
> 复用性好：抽取共性的方式，大部分代码在父类实现，个性化逻辑由子类实现
> 屏蔽细节：很多共性作为私有方法在抽象基类中实现，对子类屏蔽了很多细节
> 便于维护：好的复用性可以减少代码框架的设计，支持更好灵活的业务变更

模板方法模式的缺点

> Java单继承。一个类只能继承于一个父类
> 每一个不同的实现都需要一个子类来实现，导致类的个数增加，使得系统更加庞大

### 3-2 行业案例分享 (03:34)

大型系统的日志分析处理

- 种类繁多数据巨大的日志

- 抽取共性

- 获得规律

  1. 获得文件：抽象基类
  2. 打开文件：抽象基类
  3. 读取日志结构：抽象基类
  4. *处理单行日志*：个性化需求，延迟到子类实现
  5. 清理工作：抽象基类

  钩子函数：`beforeProcessOneFile();`、`afterProcessOneFile();`