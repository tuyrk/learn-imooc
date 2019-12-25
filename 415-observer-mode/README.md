# 观察者模式

title: 415-观察者模式
date: 2019-12-20, 21:23:55
categories: [慕课网]
tags: [设计模式]

---

> http://www.imooc.com/learn/415
>
> 简介：本课程通过一个天气预报的发布和订阅案例，来讲解观察者模式在Java项目中的应用。主要包括观察者模式的结构，观察者模式的两种实现方式推模型和拉模型，以及何时使用观察者模式等内容。

@[TOC]

## 第1章 概述
> 本章首先介绍了课程的学习内容和观察者的概念，然后介绍了一个应用观察者模式的场景，该场景将作为案例贯穿整个课程。

### 1-1 课程简介 (07:29)

观察者模式的定义

> 定义对象间的一种一对多的依赖关系。当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。

案例流程图

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga5usfsg9uj30xq0h8k0e.jpg" alt="观察者模式流程图" style="zoom:35%;" />

## 第2章 观察者模式实战
> 本章介绍观察者模式的结构，以及如何用通用代码实现第一章给出的场景问题。

### 2-1 观察者模式的结构和说明 (02:00)

观察者模式结构

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga5uyn7ge2j312w09qgrf.jpg" alt="观察者模式结构" style="zoom:35%;" />

观察者模式类图

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga5uzsfjttj30pc0fwjyz.jpg" alt="观察者模式类图" style="zoom:40%;" />

ConcreteSubject是Subject接口的实现类
ConcreteObserver是Observer接口的实现类

### 2-2 学习观察者模式通用代码 (14:25)

实现步骤

> 1. 目标对象的定义
> 2. 具体的目标对象的定义
> 3. 观察者的接口定义
> 4. 观察者的具体实现



代码编写：

1. 目标类，目标对象Subject.java

   > 它知道观察它的观察者，并提供注册（添加）和删除观察者的接口

   ```java
   public class Subject {
       // 用来保存注册的观察者对象
       private List<Observer> observers = new ArrayList<>();
   
       // 增加观察者
       public void attach(Observer observer) {
           observers.add(observer);
       }
       // 删除集合中指定的观察者
       public void detach(Observer observer) {
           observers.remove(observer);
       }
   
       // 通知所有注册的观察者对象
       protected void notifyObserver() {
           observers.forEach(observer -> observer.update(this));
       }
   }
   ```

2. 观察者接口Observer.java

   > 定义一个更新的接口给那些在目标对象发生改变的时候被通知的对象

   ```java
   public interface Observer {
       /**
        * 更新的接口
        *
        * @param subject 传入的目标对象，方便获取相应的目标对象的状态
        */
       void update(Subject subject);
   }
   ```

3. 具体的目标对象ConcreteSubject.java

   > 负责把有关状态存入到相应的观察者对象中

   ```java
   @Getter
   public class ConcreteSubject extends Subject {
       // 目标对象的状态
       private String subjectState;
   
       public void setSubjectState(String subjectState) {
           this.subjectState = subjectState;
           // 当状态发生改变时，通知观察者
           this.notifyObserver();
       }
   }
   ```

4. 具体的观察者对象ConcreteObserver.java

   > 实现更新的方法，使自身的状态和目标的状态保持一致

   ```java
   @Getter
   public class ConcreteObserver implements Observer {
       // 观察者的状态
       private String observerState;
   
       /**
        * 获取目标类的状态，同步到观察者的状态中
        *
        * @param subject 传入的目标对象，方便获取相应的目标对象的状态
        */
       @Override
       public void update(Subject subject) {
           observerState = ((ConcreteSubject) subject).getSubjectState();
       }
   }
   ```

### 2-3 改造通用代码解决场景问题 (18:14)

> 订阅天气

代码编写：

1. 管理订阅者列表WeatherSubject.java

   ```java
   public class WeatherSubject {
       // 订阅者列表
       private List<Observer> observers = new ArrayList<>();
   
       // 把订阅天气的人增加到订阅者列表中
       public void attach(Observer observer) {
           observers.add(observer);
       }
       // 删除集合中的指定订阅天气的人
       public void detach(Observer observer) {
           observers.remove(observer);
       }
   
       // 通知所有已经订阅天气的人
       protected void notifyObserver() {
           observers.forEach(observer -> observer.update(this));
       }
   }
   ```

2. 观察者接口Observer.java

   > 定义一个更新的接口，给那些在目标对象发生改变的时候被通知的对象

   ```java
   public interface Observer {
       /**
        * 更新的接口
        *
        * @param subject 传入的目标对象，方便获取相应的目标对象的状态
        */
       void update(WeatherSubject subject);
   }
   ```

3. 具体的目标对象ConcreteWeatherSubject.java

   > 负责把有关状态存入到相应的观察者对象中

   ```java
   @Getter
   public class ConcreteWeatherSubject extends WeatherSubject {
       // 获取天气的内容信息
       private String weatherContent;
   
       public void setWeatherContent(String weatherContent) {
           this.weatherContent = weatherContent;
           // 内容有了，说明天气更新了，通知所有订阅的人
           this.notifyObserver();
       }
   }
   ```

4. 具体的观察者对象ConcreteObserver.java

   > 实现更新的方法，使自身的状态和目标的状态保持一致

   ```java
   @Data
   public class ConcreteObserver implements Observer {
       // 观察者的名称，是谁收到了这个信息
       private String observerName;
       // 天气的内容信息，这个消息从目标处获取
       private String weatherContent;
       // 提醒的内容，不同的观察者提醒不同的内容
       private String remindThing;
   
       /**
        * 获取目标类的状态，同步到观察者的对象中
        *
        * @param subject 传入的目标对象，方便获取相应的目标对象的状态
        */
       @Override
       public void update(WeatherSubject subject) {
           this.weatherContent = ((ConcreteWeatherSubject) subject).getWeatherContent();
           System.out.println(observerName + "收到了天气信息：" + weatherContent + "，准备去做：" + remindThing);
       }
   }
   ```

5. 订阅天气-测试类Client.java

   ```java
   public class Client {
       public static void main(String[] args) {
           // 1. 创建目标
           ConcreteWeatherSubject weather = new ConcreteWeatherSubject();
           // 2. 创建观察者
           ConcreteObserver girlObserver = new ConcreteObserver();
           girlObserver.setObserverName("女朋友");
           girlObserver.setRemindThing("约会");
   
           ConcreteObserver mumObserver = new ConcreteObserver();
           mumObserver.setObserverName("老妈");
           mumObserver.setRemindThing("超市打折");
           // 3. 注册观察者
           weather.attach(girlObserver);
           weather.attach(mumObserver);
           // 4. 目标发布天气
           weather.setWeatherContent("明天天气晴朗");
       }
   }
   ```

## 第3章 观察者模式详解
> 本章主要介绍观察者模式实现的两种方式推模型和拉模型，利用Java提供的观察者实现第一章的场景问题，观察者模式的优缺点，以及何时使用观察者模式。

### 3-1 认识观察者模式 (06:32)

目标与观察者之间的关系

> 一对多的关系
> 一对一的关系（如果观察者只有一个）
> 多对一的关系：如果目标有多个，即一个观察者订阅多个目标，此时不应该只使用一个update方法，应该一个目标对应一个update方法。

单向依赖

> 在观察者模式中，观察者和目标是单向依赖，只有观察者依赖目标，而不是目标依赖观察者。
> 主动权掌握在目标手中，只有目标知道什么时候需要通知观察者。

命名建议

> 观察者模式又被称为发布订阅模式
> 目标接口的定义，建议在名称后面跟Subject
> 观察者接口的定义，建议在名称后面跟Observer
> 观察者接口的更新方法，建议名称为update

触发通知的时机

> 一般情况下，是在完成了状态维护后触发。
> 因为通知会传递数据，不能先通知，后改数据，这会导致观察者和目标对象状态不一致。

观察者模式的调用顺序示意图

- 准备阶段

  <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga5x8a3zbcj30q00fcaeb.jpg" alt="观察者模式的调用顺序示意图-准备阶段" style="zoom:40%;" />

- 运行阶段

  <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga5x9yj30xj30t80f0gpl.jpg" alt="观察者模式的调用顺序示意图-运行阶段" style="zoom:40%;" />

通知的顺序

> 从理论上来说，当目标对象的状态发生改变时，通知所有观察者的时候，顺序是不确定的。
> 因此，观察者实现的功能，绝对不能依赖于通知的顺序
> 也就是说，多个观察者之间的顺序是平行的，相互不应该有先后依赖的关系。

### 3-2 实现的两种方式：推模型和拉模型 (07:50)

推模型

> 目标对象主动向观察者推送目标的详细信息
> 推送的信息通常是目标对象的全部或部分数据
> 相当于在广播通讯

拉模型（第二章的实现属于拉模型）

> 目标对象在通知观察者的时候，只传递少量信息
> 如果观察者需要更具体的信息，由观察者主动到目标对象中获取
> 相当于是观察者从目标对象中拉数据
> 一般这种模型的实现中，会把目标对象自身通过update方法传递给观察者

两种模型的区别

> 推模型是假定目标对象知道观察者需要的数据
> 推模型会使观察者对象难以复用。update方法是按需定制的，可能没有兼顾未考虑到的情况。
>
> 拉模型是目标对象不知道观察者具体需要什么数据，因此把自身传递给观察者，由观察者来取值
> 拉模型下，update方法的参数是目标对象本身，基本上可以适应各种情况的需要

### 3-3 利用Java提供的观察者实现 (16:56)

Java实现与自己实现的对比

> 1. 不需要再定义观察者和目标的接口，JDK已定义
> 2. 具体的目标实现里面不需要再维护观察者的注册信息，JDK在Observable类里面已实现
> 3. 触发通知的方式有一点变化，要先调用setChanged方法，这是为了实现更精确的触发控制
> 4. 具体观察者的实现里面，update方法能**同时支持**推模型和拉模型



代码编写

> 使用JDK实现观察者模式

1. 天气目标具体实现类ConcreteWeatherSubject.java

   ```java
   @Getter
   public class ConcreteWeatherSubject extends Observable {
       // 天气情况的内容
       private String content;
   
       public void setContent(String content) {
           this.content = content;
           // 天气情况有了，就要通知所有的观察者
           // 在Java中使用Observer模式时，需要先调用setChanged方法
           this.setChanged();
   
           // 调用通知方法-推模型
           this.notifyObservers(content);
           // 调用通知方法-拉模型
           /*this.notifyObservers();*/
       }
   }
   ```

2. 具体的观察者对象ConcreteObserver.java

   ```java
   @Data
   public class ConcreteObserver implements Observer {
       // 观察者的名称，是谁收到了这个信息
       private String observerName;
   
       @Override
       public void update(Observable o, Object arg) {
           // 推模型
           System.out.println(observerName + "收到了消息，目标对象推送过来的是：" + arg);
           // 拉模型
           ConcreteWeatherSubject weatherSubject = (ConcreteWeatherSubject) o;
           System.out.println(observerName + "收到了消息，主动到目标对象中去拉：" + weatherSubject.getContent());
       }
   }
   ```

3. 订阅天气-测试类Client.java

   ```java
   public class Client {
       public static void main(String[] args) {
           // 1. 创建目标
           ConcreteWeatherSubject weather = new ConcreteWeatherSubject();
           // 2. 创建观察者
           ConcreteObserver girlObserver = new ConcreteObserver();
           girlObserver.setObserverName("女朋友");
   
           ConcreteObserver mumObserver = new ConcreteObserver();
           mumObserver.setObserverName("老妈");
           // 3. 注册观察者
           weather.addObserver(girlObserver);
           weather.addObserver(mumObserver);
           // 4. 目标发布天气
           weather.setContent("明天天气晴朗");
       }
   }
   ```

### 3-4 观察者优缺点 (03:46)

优点

> 观察者模式实现了观察者和目标之间的抽象耦合
>
> 观察者模式实现了动态联动（所谓联动是指做一个操作会引起其他相关的操作）
>
> 观察者模式支持广播通信

缺点

> 可能会引起无谓的操作

### 3-5 何时使用观察者模式 (03:33)

观察者模式的本质：**触发联动**

建议在以下情况中选用观察者模式

> 1. 当一个抽象模型有两个方面，其中一个方面的操作依赖于另一个方面的状态变化
> 2. 如果在更改一个对象的时候，需要同时连带改变其他的对象，而且不知道究竟应该有多少对象需要被连带改变
> 3. 当一个对象必须通知其他对象，但是又希望这个对象和被它通知的对象是松散耦合的

## 第4章 观察者模式衍生
> 本章主要介绍如何区别对待观察者场景问题以及代码实现。

### 4-1 区别对待观察者场景问题 (03:41)

需求总结

> 区别对待观察者
> 根据需要，不同的天气情况来通知不同的观察者
>
> 女朋友只想接收「下雨」的天气预报
> 老妈想要接收「下雨」或「下雪」的天气预报

解决思路

> 当天气更新时，在目标天气中进行判断，如果不符合观察者的条件，则不进行通知

实现步骤

> 1. 定义目标的抽象类和观察者的接口
> 2. 实现目标的类和观察者接口
> 3. 编写测试类进行测试

### 4-2 代码示例解决场景问题 (07:52)

1. 天气目标抽象类AbstractWeatherSubject.java

   ```java
   public abstract class AbstractWeatherSubject {
       // 用来保存注册的观察者对象
       protected List<Observer> observers = new ArrayList<>();
   
       // 增加观察者添加到订阅列表中
       public void attach(Observer observer) {
           observers.add(observer);
       }
       // 删除集合中指定的观察者
       public void detach(Observer observer) {
           observers.remove(observer);
       }
   
       // 区别通知观察者-由子类实现
       protected abstract void notifyObservers();
   }
   ```


### 4-3 观察者接口 (01:38)

2. 观察者接口

   > 定义一个更新的接口方法，给那些在目标对象发生改变的时候被通知的观察者对象调用

   ```java
   public interface Observer {
       /**
        * 更新的接口
        *
        * @param weatherSubject 传入的目标对象，方便获取相应的目标对象的状态
        */
       void update(AbstractWeatherSubject weatherSubject);
   
       // 获取观察者名称
       String getObserverName();
       // 设置观察者名称
       void setObserverName(String observerName);
   }
   ```

### 4-4 目标实现类 (07:24)

3. 天气目标的实现类ConcreteWeatherSubject.java

   ```java
   @Getter
   public class ConcreteWeatherSubject extends AbstractWeatherSubject {
       // 目标对象的状态
       // 天气情况：晴天、下雨、下雪
       private String weatherContent;
       public void setWeatherContent(String weatherContent) {
           this.weatherContent = weatherContent;
           this.notifyObservers();
       }
       
       @Override
       protected void notifyObservers() {
           // 遍历所有注册的观察者
           this.observers.forEach(observer -> {
               /*
               规则是：
                   1. 「女朋友」需要「下雨」的条件通知，其他条件不通知
                   2. 「老妈」需要「下雨」或「下雪」的条件通知，其他条件不通知
                */
   
               // 1. 如果天气是晴天
               // do nothing...
   
               // 2. 如果天气是下雨
               if (Objects.equals("下雨", this.getWeatherContent())) {
                   if (Objects.equals("女朋友", observer.getObserverName())) {
                       observer.update(this);
                   }
                   if (Objects.equals("老妈", observer.getObserverName())) {
                       observer.update(this);
                   }
               }
   
               // 3. 如果天气是下雪
               if (Objects.equals("下雪", this.getWeatherContent())) {
                   if (Objects.equals("老妈", observer.getObserverName())) {
                       observer.update(this);
                   }
               }
           });
       }
   }
   ```

### 4-5 观察者接口类的实现 (05:01)

4. 观察者的实现类ConcreteObserver.java

   ```java
   @Data
   public class ConcreteObserver implements Observer {
       // 观察者的名称，是谁收到了这个消息
       private String observerName;
       // 天气的内容信息，这个消息从目标处获取
       private String weatherContent;
       // 提醒的内容，不同的观察者提醒不同的内容
       private String remindThing;
   
       /**
        * 获取目标类的状态，同步到观察者的状态中
        *
        * @param weatherSubject 传入的目标对象，方便获取相应的目标对象的状态
        */
       @Override
       public void update(AbstractWeatherSubject weatherSubject) {
           this.weatherContent = ((ConcreteWeatherSubject) weatherSubject).getWeatherContent();
           System.out.println(observerName + "收到了天气信息" + weatherContent + "，准备去做" + remindThing);
       }
   }
   ```

### 4-6 测试 (06:24)

5. 区别对待观察者-测试类Client.java

   ```java
   public class Client {
       @Test
       public void test1() {
           // 1. 创建目标
           ConcreteWeatherSubject weatherSubject = new ConcreteWeatherSubject();
           // 2. 创建观察者
           ConcreteObserver girlObserver = new ConcreteObserver();
           girlObserver.setObserverName("女朋友");
           girlObserver.setRemindThing("宅在家里");
           ConcreteObserver mumObserver = new ConcreteObserver();
           mumObserver.setObserverName("老妈");
           mumObserver.setRemindThing("收衣服啦");
           // 3. 注册观察者
           weatherSubject.attach(girlObserver);
           weatherSubject.attach(mumObserver);
           // 4. 目标发布天气
           weatherSubject.setWeatherContent("下雪");
           weatherSubject.setWeatherContent("下雨");
           weatherSubject.setWeatherContent("下冰雹");
       }
   }
   ```

## 第5章 课程总结
> 本章对本课程学习的内容进行总结，帮助小伙伴们巩固所学知识。

### 5-1 课程总结 (02:16)

> 观察者模式简介：场景描述
> 观察者模式实战：模式原理
> 观察者模式详解：推拉模型、JDK实现、优缺点、何时使用
> 观察者模式衍生：区别对待观察者