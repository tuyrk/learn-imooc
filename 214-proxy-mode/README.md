# 模式的秘密---代理模式

> http://www.imooc.com/learn/214
>
> 简介：代理模式是一种非常重要的设计模式，在 Java 语言中有着广泛的应用，包括Spring AOP 的核心设计思想，都和代理模式有密切关系。什么场景使用代理模式？代理模式实现原理是什么？本节课程将带你领略代理模式的奥妙。

## 第1章 代理模式概念介绍
> 本章讲述了代理模式的分类、应用场景及作用

### 1-1 代理模式概念及分类 (04:57)

学习本课程基础

> 面向对象的设计思维
> 了解多态的概念
> 了解反射机制

课程目标

> 代理模式基本概念及分类
> 了解代理模式开发中应用场景
> 掌握代理模式实现方式
> 理解JDK动态代理实现

代理模式定义

> 为其他对象提供一种代理以控制对这个对象的访问
> 代理对象起到中介作用，可去掉功能服务或添加额外的服务

常见的几种代理模式

> 远程代理（Remote Proxy）：为一个位于不同地理空间的对象提供局域网代表对象。类似于客户端服务器这种模式，是远程通信的缩影
> 虚拟代理（Virtual Proxy）：根据需要将资源消耗很大的对象进行延迟，真正需要的时候进行创建
> 保护代理（Protect Proxy）：控制对象的访问权限
> 智能代理（Smart Reference Proxy）：提供对目标对象额外的服务

## 第2章 常用代理模式原理
> 本章介绍静态代理、动态代理实现原理。并通过案例讲解 JDK 动态代理以及使用 cglib 实现动态代理

智能引用代理

> 静态代理
> 动态代理

### 2-1 静态代理概念及实现 (09:32)

静态代理定义

> 代理和被代理对象在代理之前是确定的。他们都实现相同的接口或者继承相同的抽象类

静态代理图

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1g9yud2gy5yj30qo0jcjxj.jpg" alt="image-20191216203507152" style="zoom:35%;" />

静态代理

> 继承方式
> 聚合方式



代码编写

1. 可行驶的接口Moveable.java

   ```java
   public interface Moveable {
       // 行驶的方法
       void move();
   }
   ```

2. 一辆车实现可行驶的接口Car.java

   ```java
   public class Car implements Moveable {
       @Override
       public void move() {
           // 记录汽车行驶的时间
           /*long startTime = System.currentTimeMillis();
           System.out.println("汽车开始行驶...");*/
   
           // 实现开车
           try {
               System.out.println("汽车行驶中...");
               Thread.sleep(new Random().nextInt(1000));
           } catch (InterruptedException e) { }
   
           /*long endTime = System.currentTimeMillis();
           System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");*/
       }
   }
   ```

3. 使用继承方式实现静态代理Car2.java

   ```java
   public class Car2 extends Car {
       @Override
       public void move() {
           // 记录汽车行驶的时间
           long startTime = System.currentTimeMillis();
           System.out.println("汽车开始行驶...");
   
           super.move();
   
           long endTime = System.currentTimeMillis();
           System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
       }
   }
   ```

4. 使用聚合方式实现静态代理Car3.java

   ```java
   public class Car3 implements Moveable {
       private Car car;
       public Car3(Car car) {
           super();
           this.car = car;
       }
   
       @Override
       public void move() {
           // 记录汽车行驶的时间
           long startTime = System.currentTimeMillis();
           System.out.println("汽车开始行驶...");
   
           car.move();
   
           long endTime = System.currentTimeMillis();
           System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
       }
   }
   ```

5. 静态代理测试类Client.java

   ```java
   public class Client {
       public static void main(String[] args) {
           // 1. 在执行逻辑代码前后添加信息
           /*Moveable car1 = new Car();
           car1.move();*/
   
           // 2. 使用继承方式实现静态代理
           /*Moveable car2 = new Car2();
           car2.move();*/
   
           // 3. 使用聚合方式实现静态代理
           Car car = new Car();
           Moveable car3 = new Car3(car);
           car3.move();
       }
   }
   ```

### 2-2 聚合比继承更适合代理模式 (06:21)

场景分析

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1g9yve0p9zqj31io0u07wh.jpg" alt="实现功能叠加" style="zoom:25%;" />

代理类功能叠加

> 1. 记录日志
> 2. 记录时间
> 3. 权限功能



- 使用继承方式

  <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1g9yvfjlv03j30ta0e40zg.jpg" alt="使用继承方式" style="zoom:40%;" />

  使用继承方式来实现代理功能的叠加，代理类会无限的膨胀下去，所以这种方式不推荐使用。

- 使用聚合方式，通过代码演示

代码编写

1. 汽车日志功能的代理CarLogProxy.java

   ```java
   public class CarLogProxy implements Moveable {
       private Moveable m;
       // 因为代理类和被代理类都是实现了相同的接口，所以构造方法传递的对象也是可以是Moveable对象
       public CarLogProxy(Moveable m) {
           super();
           this.m = m;
       }
   
       @Override
       public void move() {
           System.out.println("日志开始");
           m.move();
           System.out.println("日志结束");
       }
   }
   ```

2. 汽车行驶时间的代理CarTimeProxy.java

   ```java
   public class CarTimeProxy implements Moveable {
       private Moveable m;
       // 因为代理类和被代理类都是实现了相同的接口，所以构造方法传递的对象也是可以是Moveable对象
       public CarTimeProxy(Moveable m) {
           super();
           this.m = m;
       }
   
       @Override
       public void move() {
           // 记录汽车行驶的时间
           long startTime = System.currentTimeMillis();
           System.out.println("汽车开始行驶...");
           m.move();
           long endTime = System.currentTimeMillis();
           System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
       }
   }
   ```

3. 静态代理测试类-功能叠加的聚合方式Client.java

   ```java
   public class Client {
       public static void main(String[] args) {
        Car car = new Car();
           CarLogProxy clp = new CarLogProxy(car);
           CarTimeProxy ctp = new CarTimeProxy(clp);
           ctp.move();
       }
   }
   ```
   
   ```
   汽车开始行驶...
   日志开始
   汽车行驶中...
   日志结束
   汽车结束行驶...汽车行驶时间：845毫秒
   ```



思考：CarTimeProxy只是对汽车时间代理，如果此时需要对火车、自行车进行时间代理怎们办？还需要重新写火车时间代理类、汽车时间代理类么？

### 2-3 了解 JDK 动态代理 (08:52)

场景分析

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1g9ywn8g86yj31dm0nids3.jpg" alt="多种类的时间代理" style="zoom:25%;" />

有没有方法动态产生代理，实现对不同类、不同方法的代理呢？



JDK动态代理类图

> Java动态代理机制以巧妙的方式实现了代理模式的设计理念

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1g9ywswu2xgj31ba0a2whb.jpg" alt="JDK动态代理类图" style="zoom:25%;" />

Java动态代理类位于`java.lang.reflect`包下，一般主要涉及到以下两个类：

> - `Interface InvocationHandler`：该接口中仅定义了一个方法
>
>   `public Object invoke(Object obj, Method method, Object[] args)`
>
>   在实际使用时，第一参数`obj`一般是指代理类，`method`是被代理的方法，`args`为该方法的参数数组。
>
>   这个抽象方法在代理类中动态实现。
>
> - Proxy：该类即为动态代理类
>
>   `static Object newProxyInstance(ClassLoader loader, Class[] interfaces, InvocationHandler h)`
>
>   返回代理类的一个实例，返回后的代理类可以当做被代理类使用
>
>   （可使用被代理类的在接口中声明过的方法）



动态代理实现步骤

> 1. 创建一个实现InvocationHandler接口的类，它必须实现invoke方法(实现业务逻辑)
> 2. 创建被代理的类以及接口
> 3. 调用Proxy的静态方法，创建一个代理类
>    Proxy.newProxyInstance(CLassLoader loader, Class[] interfaces, InvocationHandler h)
> 4. 通过代理调用方法



代码编写

1. 使用JDK动态代理-对时间上的处理TimeHandler.java

   ```java
   public class TimeHandler implements InvocationHandler {
       private Object target;
       public TimeHandler(Object target) {
           super();
           this.target = target;
       }
   
       /**
        * JDK动态代理
        * @param proxy  被代理对象
        * @param method 被代理对象方法
        * @param args   方法的参数
        * @return 方法的返回值
        */
       @Override
       public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
           long startTime = System.currentTimeMillis();
           System.out.println("汽车开始行驶...");
   
           method.invoke(target, args);
   
           long endTime = System.currentTimeMillis();
           System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
           return null;
       }
   }
   ```

2. JDK动态代理测试类Test.java

   ```java
   public class Test {
       public static void main(String[] args) {
           Car car = new Car();
           InvocationHandler h = new TimeHandler(car);
           Class<? extends Car> cls = car.getClass();
           // 使用Proxy类newProxyInstance方法动态创建代理类
           /*
             loader 类加载器
             interfaces 实现接口
             h InvocationHandler
            */
           Moveable m = (Moveable)Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);
           m.move();
       }
   }
   ```



所谓Dynamic Proxy是这样一种class

> 它是在运行时生成的class
> 该class需要实现一组interface
> 使用动态代理类时，必须实现InvocationHandler接口

### 2-4 使用 cglib 动态产生代理 (07:44)

JDK动态代理与CGLIB动态代理区别

> JDK动态代理
>
> 1. 只能代理实现了接口的类
> 2. 没有实现接口的类不能实现JDK的动态代理
>
> CGLIB动态代理
>
> 1. 针对类来实现代理的
> 2. 对指定目标类产生一个子类，通过方法拦截技术拦截所有父类方法的调用
> 3. 因为是使用继承的方式，所以不能对final修饰的类来进行代理



代码编写

1. 添加相关依赖

   ```java
   <!-- https://mvnrepository.com/artifact/cglib/cglib-nodep -->
   <dependency>
       <groupId>cglib</groupId>
       <artifactId>cglib-nodep</artifactId>
       <version>3.3.0</version>
   </dependency>
   <!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
   <dependency>
       <groupId>commons-io</groupId>
       <artifactId>commons-io</artifactId>
       <version>2.6</version>
   </dependency>
   ```

2. 代理类CglibProxy.java

   ```java
   public class CglibProxy implements MethodInterceptor {
       private Enhancer enhancer = new Enhancer();
       public Object getProxy(Class clazz) {
           // 设置创建子类的类，即为哪个类产生代理类
           enhancer.setSuperclass(clazz);
           enhancer.setCallback(this);
           return enhancer.create();
       }
   
       /**
        * 拦截所有目标类方法的调用
        *
        * @param o 目标类的实例
        * @param method 目标方法的反射对象
        * @param objects 方法的参数
        * @param methodProxy 代理类的实例
        * @return 方法的返回值
        * @throws Throwable
        */
       @Override
       public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
           System.out.println("日志开始...");
   
           // 代理类调用父类的方法
           methodProxy.invokeSuper(o, objects);
   
           System.out.println("日志结束...");
           return null;
       }
   }
   ```

3. 火车Train.java

   ```java
   public class Train implements Moveable {
       @Override
       public void move() {
           System.out.println("火车🚄行驶中");
       }
   }
   ```

   如需再有飞机类则步骤4：

4. 飞机Plain.java

   ```java
   public class Plain implements Moveable {
       @Override
       public void move() {
           System.out.println("飞机✈行驶中");
       }
   }
   ```

5. CGLIB代理的测试类Client.java

   ```java
   public class Client {
       public static void main(String[] args) {
           CglibProxy cglibProxy = new CglibProxy();
           Train t = (Train) cglibProxy.getProxy(Train.class);
           t.move();
           Plain p = (Plain) cglibProxy.getProxy(Plain.class);
           p.move();
       }
   }
   ```

## 第3章 自定义类模拟 JDK 动态代理的实现
> 本章通过编写自定义类，模拟 JDK 动态代理的实现，帮助大家深入理解 JDK 动态代理的实现原理与机制

### 3-1 模拟 JDK 动态代理实现思路分析及简单实现 (15:55)

动态代理实现思路

> 实现功能：通过Proxy的newProxyInstance返回代理对象
>
> 1. 声明一段源码（动态产生代理）
> 2. 编译编码（JDK Compiler API），产生新的类（代理类）
> 3. 将这个类load到内存当中，产生一个新的对象（代理对象）
> 4. return代理对象



注：以下代码为教师源码，在GitHub学习项目代码中已做**改进：方法可传递参数**

代码编写

1. 模拟JDK动态代理-业务处理类InvocationHandler.java

   ```java
   
   public interface InvocationHandler {
       /**
        * @param obj    被代理对象
        * @param method 被代理对象方法
        */
       void invoke(Object obj, Method method);
   }
   ```

2. 模拟JDK动态代理-代理类Proxy.java

   ```java
   public class Proxy {
       public static Object newProxyInstance(Class infce, InvocationHandler h)
               throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
           String rt = "\r\n";
           StringBuilder methodStr = new StringBuilder();
           for (Method m : infce.getMethods()) {
               methodStr.
                       append("@Override").append(rt).
                       append("public void ").append(m.getName()).append("() {").append(rt)
                       .append("    try {").append(rt)
                       .append("        Method md = ").append(infce.getName()).append(".class.getMethod(\"").append(m.getName()).append("\");").append(rt)
                       .append("        h.invoke(this, md);").append(rt)
                       .append("    } catch (Exception e) {").append(rt)
                       .append("        e.printStackTrace();").append(rt)
                       .append("    }").append(rt)
                       .append("}").append(rt);
           }
           String str = "package com.tuyrk.analog_jdkproxy;" + rt +
                   "import com.tuyrk.analog_jdkproxy.InvocationHandler;" + rt +
                   "import java.lang.reflect.Method;" + rt +
                   "public class $Proxy0 implements " + infce.getName() + " {" + rt +
                   "    private InvocationHandler h;" + rt +
                   "    public $Proxy0(InvocationHandler h) {" + rt +
                   "        this.h = h;" + rt +
                   "    }" + rt +
                   methodStr + rt +
                   "}" + rt;
   
           // 产生代理类的Java文件
           String filename = System.getProperty("user.dir") + "/214-proxy-mode/target/classes/com/tuyrk/analog_jdkproxy/$Proxy0.java";
           File file = new File(filename);
           FileUtils.writeStringToFile(file, str, StandardCharsets.UTF_8);
   
           // 编译-拿到编译器
           JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
           // 文件管理者
           StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
           // 获取文件
           Iterable<? extends JavaFileObject> units = fileMgr.getJavaFileObjects(filename);
           // 编译任务
           CompilationTask task = compiler.getTask(null, fileMgr, null, null, null, units);
           // 进行编译
           task.call();
           fileMgr.close();
   
           // 获取类加载器
           ClassLoader cl = ClassLoader.getSystemClassLoader();
           // 加载到内存
           Class<?> c = cl.loadClass("com.tuyrk.analog_jdkproxy.$Proxy0");
   
           // 获取构造器
           Constructor<?> ctr = c.getConstructor(InvocationHandler.class);
           return ctr.newInstance(h);
       }
   }
   ```

3. 模拟JDK动态代理-时间业务逻辑处理TimeHandler.java

   ```java
   public class TimeHandler implements InvocationHandler {
       private Object target;
       public TimeHandler(Object target) {
           super();
           this.target = target;
       }
   
       @Override
       public void invoke(Object obj, Method method) {
           long startTime = System.currentTimeMillis();
           System.out.println("汽车开始行驶...");
   
           method.invoke(target);
   
           long endTime = System.currentTimeMillis();
           System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
       }
   }
   ```

4. 模拟JDK动态代理-测试类Client.java

   ```java
   public class Client {
       public static void main(String[] args) throws Exception {
        Car car = new Car();
           InvocationHandler h = new TimeHandler(car);
           Moveable m = (Moveable)Proxy.newProxyInstance(Moveable.class, h);
           m.move();
       }
   }
   ```
   

## 第4章 代理模式总结
> 总结代理模式分类、应用场景、实现原理、实现方式及实现方式优缺点

### 4-1 课程总结 (03:47)

> 代理模式概念、分类及应用场景
> 静态代理（继承、聚合）
> JDK动态代理实现日志处理功能
> 模拟JDK动态代理实现

为什么只讲解了智能引用代理？

- 智能引用代理应用最广，如日志处理、权限处理、事务处理

代理模式-动态代理：

> 不改变原有类的情况下，增加一些额外的业务逻辑。AOP

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1ga0w09naq0j30tk0bywhs.jpg" alt="代理模式-动态代理" style="zoom:50%;" />

代理模式：

> - 代理模式基本概念及分类
> - 静态代理概念及实例
> - 动态代理概念及实例
> - 模拟JDK动态代理实现
> - 课程总结