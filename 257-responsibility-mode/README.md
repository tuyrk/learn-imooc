# 模式的秘密---责任链模式

> http://www.imooc.com/learn/257
>
> 简介：责任链模式在我们日常开发工作中随处可见，但却并不一定唯你所熟知。通过本视频，你可以了解什么是责任链模式，如何用 Java 语言实现策略模式。 作者将用简单生动的例子带你认识责任链模式的，通过学习，理解模式学习的要义。

## 第1章 什么是责任链模式
> 本章通过分析击鼓传花和购房折扣申请场景，引入责任链模式的概念。针对概念设计了一个虚拟的应用，分析应用需求。

### 1-1 什么是责任链模式 (10:12)

课程大纲

> 什么是责任链模式
> 如何实现责任链模式
> 责任链模式如何解耦
> 责任链模式的应用

案例：售楼案例

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gacg5tmqv7j310m0ew43e.jpg" alt="案例：售楼案例" style="zoom:35%;" />

责任链模式定义

> 责任链模式将接收者对象连成一条链，并在该链上传递请求，直到有一个接收者对象处理它。通过让更多对象有机会处理请求，避免请求发送者和接收者之间的耦合。

责任链模式类图

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gacg7av0mhj30uc0fg0vb.jpg" alt="责任链模式类图" style="zoom:45%;" />

> 在责任链模式中，作为请求接收者的多个对象通过对其后继的引用而连接起来形成一条链。请求在这条链上传递，直到链上某一个接收者处理这个请求。每个接收者都可以选择自行处理请求或是向后继传递请求。

## 第2章 有求必应的销售队伍：怎样实现责任链模式
> 通过代码的编写责任链模式的实现

### 2-1 有求必应的销售团队 (08:33)

不同的角色拥有不同的折扣权限

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gacgbaggkqj30wu0e6agx.jpg" alt="不同的角色拥有不同的折扣权限" style="zoom:40%;" />

代码编写

1. 价格处理人，负责处理客户折扣申请

   ```java
   @Data
   public abstract class PriceHandler {
       // 责任链的直接后继，用于传递请求
       protected PriceHandler successor;
   
       // 处理折扣申请
       public abstract void processDiscount(float discount);
   
       // 创建PriceHandler的工厂方法
       public static PriceHandler createPriceHandler() {
           // 创建对象
           PriceHandler sales = new Sales();
           PriceHandler manager = new Manager();
           PriceHandler director = new Director();
           PriceHandler vp = new VicePresident();
           PriceHandler ceo = new CEO();
           // 指定直接后继
           sales.setSuccessor(manager);
           manager.setSuccessor(director);
           director.setSuccessor(vp);
           vp.setSuccessor(ceo);
           // 返回销售人员
           return sales;
       }
   }
   ```

2. 销售，可以批准5%以内的折扣

   ```java
   public class Sales extends PriceHandler {
       @Override
       public void processDiscount(float discount) {
           if (discount <= 0.05) {
               System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
           } else {
               successor.processDiscount(discount);
           }
       }
   }
   ```

3. 销售经理，可以批准30%以内的折扣

   ```java
   public class Manager extends PriceHandler {
       @Override
       public void processDiscount(float discount) {
           if (discount <= 0.3) {
               System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
           } else {
               successor.processDiscount(discount);
           }
       }
   }
   ```

4. 销售总监，可以批准40%以内的折扣

   ```java
   public class Director extends PriceHandler {
       @Override
       public void processDiscount(float discount) {
           if (discount <= 0.4) {
               System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
           } else {
               successor.processDiscount(discount);
           }
       }
   }
   ```

5. 销售副总裁，可以批准50%以内的折扣

   ```java
   public class VicePresident extends PriceHandler {
       @Override
       public void processDiscount(float discount) {
           if (discount <= 0.5) {
               System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
           } else {
               successor.processDiscount(discount);
           }
       }
   }
   ```

6. CEO，可以批准55%以内的折扣。折扣超过55%就拒绝申请

   ```java
   public class CEO extends PriceHandler {
       @Override
       public void processDiscount(float discount) {
           if (discount <= 0.55) {
               System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
           } else {
               System.out.format("%s拒绝了折扣：%.2f%n", this.getClass().getName(), discount);
           }
       }
   }
   ```

### 2-2 千姿百态的客户请求 (10:43)

1. 客户，请求折扣

   ```java
   @Data
   public class Customer {
       private PriceHandler priceHandler;
   
       public void requestDiscount(float discount) {
           priceHandler.processDiscount(discount);
       }
   }
   ```
   
2. 测试类，客户发起折扣请求

   ```java
   public class Test {
       public static void main(String[] args) {
           Customer customer = new Customer();
           customer.setPriceHandler(PriceHandler.createPriceHandler());
           Random random = new Random();
           IntStream.rangeClosed(1, 100).forEach(i -> {
               System.out.print(i + ":");
               customer.requestDiscount(random.nextFloat());
           });
       }
   }
   ```

### 2-3 不管怎么变折扣你得批 (08:33)

> 向责任链中添加新的处理对象

代码编写

1. 销售小组长，可以批准15%以内的折扣

   ```java
   public class Lead extends PriceHandler {
       @Override
       public void processDiscount(float discount) {
           if (discount <= 0.15) {
               System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
           } else {
               successor.processDiscount(discount);
           }
       }
   }
   ```

2. 创建PriceHandler的工厂类

   工厂方法的实质不在于它的传入参数，而在于它的返回结果。从行为上返回一个抽象的对象，而非一个具体的对象。

   ```java
   public class PriceHandlerFactory {
       public static PriceHandler createPriceHandler() {
           // 创建对象
           PriceHandler sales = new Sales();
           PriceHandler lead = new Lead();
           PriceHandler manager = new Manager();
           PriceHandler director = new Director();
           PriceHandler vp = new VicePresident();
           PriceHandler ceo = new CEO();
   
           // 指定直接后继
           sales.setSuccessor(lead);
           lead.setSuccessor(manager);
           manager.setSuccessor(director);
           director.setSuccessor(vp);
           vp.setSuccessor(ceo);
   
           // 返回销售人员
           return sales;
       }
   }
   ```

3. 测试类，客户发起折扣请求

   ```java
   public class Test {
       public static void main(String[] args) {
           Customer customer = new Customer();
           customer.setPriceHandler(PriceHandlerFactory.createPriceHandler());
           Random random = new Random();
           IntStream.rangeClosed(1, 100).forEach(i -> {
               System.out.print(i + ":");
               customer.requestDiscount(random.nextFloat());
           });
       }
   }
   ```



扩展：OO设计原则

## 第3章 剖析责任链模式
> 本章在概念引入和代码实战的基础上，剖析责任链的特点，分析责任链如何实现解耦。

### 3-1 剖析责任链模式 (08:08)

利于解耦

> 发出请求的客户端并不知道链上的哪一个接收者会处理这个请求，从而实现了客户端和接收者之间的解耦。

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gachln072nj30js09mabb.jpg" alt="责任链模式" style="zoom:50%;" />

责任链模式缺点

> 1. 时间：在单个Handler对象的时间很短，但是在遍历整条链时会花费较长的时间
> 2. 内存：在创建整条链时，会创建很多类，导致内存增加，成为性能衰减点。

## 第4章 责任链模式的实际应用
> 介绍责任链模式在实际中的使用情况

### 4-1 责任链模式的应用 (06:46)

1. Java的异常机制：Exception Handing
   
   > 异常是请求，调用栈中的每一级是一个Handler，这些栈中Handler共同构建成一个责任链，栈顶元素就是上一级元素的直接后继
   
   <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gachwvq1raj30ou0bwgqc.jpg" alt="Java的异常机制" style="zoom:50%;" />
   
2. JavaScript的事件模型：JavaScript Event Model

   > 每一个dom节点都是一个Handler，当点击`<td>`节点时，它所对应的父节点就是该Handler的直接后继，这个Handler可以选择在自己的层级处理掉点击事件，也可以选择不处理，直接向后继传递

   <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gaci0b82oqj30mk0gedm4.jpg" alt="JavaScript的事件模型" style="zoom:50%;" />

3. JavaWeb开发过滤器链：FilterChain in Web

   如：编码Filter

   <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gaci2t2rvsj30qe0aegn7.jpg" alt="编码Filter" style="zoom:35%;" />

   <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gaci3ny42nj30gy0ckwhc.jpg" alt="FilterChain in Web" style="zoom:50%;" />

   <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gaci6jdsoej30q209g76i.jpg" alt="FilterChain in Web" style="zoom:50%;" />

   在`doFilter()`方法尾部调用`filterChain.doFilter()`，将request对象向下传递，避免链条中断。

4. Spring Security安全框架

   在Spring Security中通过很多Filter类构成了一条链条来处理HTTP请求，从而为应用提供了认证与授权的服务



一点体会

> 将设计模式的思想与OO（Object Oriented）原则相关联
> 在设计模式中发现OO原则可以加深理解和记忆
> 最重要的是要去**理解模式如何使我们去应对变化**
> 如何让我们能够用一种抽象的方式来编程
