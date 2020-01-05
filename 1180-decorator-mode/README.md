# 装饰者模式

> https://www.imooc.com/learn/1180
>
> 简介：装饰者模式是java23种设计模式之一， 英文叫Decorator Pattern，又叫装饰者模式。 装饰模式是在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。 它是通过创建一个包装对象，也就是装饰来包裹真实的对象 改变了传统继承增强对象功能所带来的弊端

## 第1章 课程介绍
> 本章对本课程所学内容进行介绍。

### 1-1 装饰者模式的简介 (03:02)

课程大纲

> 为什么要使用装饰者模式
> 装饰者模式的概念及适应环境
> 装饰者模式的结构
> 装饰者模式的实例演示
> 装饰者模式的优缺点
> 总结

## 第2章 装饰者模式概念
> 本章介绍如何使用装饰者模式，以及装饰者模式的概念和适应环境。

### 2-1 为什么要使用装饰者模式 (06:11)

老王豆浆案例：

> 老王豆浆配方：纯豆浆、红糖、红豆、牛奶、鸡蛋
> 豆浆由纯豆浆和其他辅料混合而成，新增辅料则可以多出很多种组合（一种组合一个子类），此时则会出现类爆炸的情况，导致程序非常臃肿。
> 解决方案：一种辅料一个子类，子类之间可以相互组合搭配。

### 2-2 装饰者模式的概念及适应环境 (03:52)

装饰者模式的概念

> 装饰者模式又名包装模式。动态的给一个对象添加一些额外的职责。就扩展功能而言，它比生成子类的方式更为灵活。

装饰者模式的适应场景

> - 以动态的方式给对象添加职责
> - 处理那些可以撤销的职责
> - 当采用生成子类的方法进行扩充时，可能有大量独立的扩展，为支持每一种组合将产生大量的子类，使得子类数量爆炸性增长（老王豆浆案例）



问题：

1. 继承与装饰者模式有什么区别？

2. 在哪种场景使用继承，在哪种场景使用装饰者模式

## 第3章 装饰者模式结构
> 本章介绍装饰者模式的结构。

### 3-1 装饰者模式的组成部分 (07:35)

装饰者模式的结构

1. 抽象组件（Component）：给出一个抽象接口，以规范准备接收附加责任的对象
2. 被装饰者（ConcreteComponent）：Component的具体实现，也就是我们要装饰的具体对象
3. 装饰者组件（Decorator）：持有组件（Component）对象的实例引用，该类的职责就是为了装饰具体组件对象，定义的基类。
4. 具体装饰（ConcreteDecorator）：负责给构件对象装饰附加的功能。

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gakmgrtweuj30n80hpjs8.jpg" alt="装饰者模式类图" style="zoom:40%;" />

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gakmip2qglj30nc0hqdg7.jpg" alt="装饰者模式实例" style="zoom:35%;" />

## 第4章 装饰者模式的简单入门
> 本章介绍装饰者模式入门。

### 4-1 装饰者模式简单入门 (23:09)

1. 抽象组件-饮品

   ```java
   public interface Drink {
       // 饮品价格
       double money();
   
       // 饮品品种描述
       String desc();
   }
   ```

2. 被装饰者-豆浆

   ```java
   public class Soya implements Drink {
       @Override
       public double money() {
           return 5D;
       }
   
       @Override
       public String desc() {
           return "纯豆浆";
       }
   }
   ```

3. 装饰者组件-装饰器

   > 1. 抽象类
   > 2. 实现抽象组件接口
   > 3. 持有抽象接口的引用

   ```java
   public abstract class Decorator implements Drink {
       // 定义私有的饮品接口引用
       private Drink drink;
       public Decorator(Drink drink) {
           this.drink = drink;
       }
   
       @Override
       public double money() {
           return drink.money();
       }
   
       @Override
       public String desc() {
           return drink.desc();
       }
   }
   ```

4. 具体装饰-红豆

   ```java
   public class RedBean extends Decorator {
       public RedBean(Drink drink) {
           super(drink);
       }
   
       @Override
       public double money() {
           return super.money() + 3.2;
       }
   
       @Override
       public String desc() {
           return super.desc() + "+红豆";
       }
   }
   ```

5. 具体装饰-鸡蛋

   ```java
   public class Egg extends Decorator {
       public Egg(Drink drink) {
           super(drink);
       }
   
       @Override
       public double money() {
           return super.money() + 3.9;
       }
   
       @Override
       public String desc() {
           return super.desc() + "+鸡蛋";
       }
   }
   ```

6. 具体装饰-糖

   ```java
   public class Sugar extends Decorator {
       public Sugar(Drink drink) {
           super(drink);
       }
   
       @Override
       public double money() {
           return super.money() + 2.1;
       }
   
       @Override
       public String desc() {
           return super.desc() + "+糖";
       }
   }
   ```

7. 装饰者模式测试类

   ```java
   public class DecoratorTest {
       public static void main(String[] args) {
           // 开始搭配
           // 创建豆浆对象
           Drink soya = new Soya();
           System.out.println(soya.money());
           System.out.println(soya.desc());
           // 向纯豆浆中加入红豆
           Drink redBeanSoya = new RedBean(soya);
           System.out.println(redBeanSoya.money());
           System.out.println(redBeanSoya.desc());
           // 向红豆豆浆中加入鸡蛋
           Drink redBeanSoyaEgg = new Egg(redBeanSoya);
           System.out.println(redBeanSoyaEgg.money());
           System.out.println(redBeanSoyaEgg.desc());
   
           // 需要糖豆浆
           Drink sugarSoya = new Sugar(soya);
           System.out.println(sugarSoya.money());
           System.out.println(sugarSoya.desc());
       }
   }
   ```



当有新的辅料品种时（新增具体装饰）：

1. 具体装饰-西瓜

   ```java
   public class Watermelon extends Decorator {
       public Watermelon(Drink drink) {
           super(drink);
       }
   
       @Override
       public double money() {
           return super.money() + 4.0;
       }
   
       @Override
       public String desc() {
           return super.desc() + "+西瓜";
       }
   }
   ```

2. 新增具体装饰-创建西瓜对象

   ```java
   Watermelon watermelonSugarSoya = new Watermelon(sugarSoya);
   System.out.println(watermelonSugarSoya.money());
   System.out.println(watermelonSugarSoya.desc());
   ```

当有新的产品时（新增被装饰者）：

1. 被装饰者-果汁

   ```java
   public class Fruit implements Drink {
       @Override
       public double money() {
           return 6D;
       }
   
       @Override
       public String desc() {
           return "果汁";
       }
   }
   ```

2. 新增被装饰者-搭配果汁

   ```java
   Drink fruit = new Fruit();
   System.out.println(fruit.money());
   System.out.println(fruit.desc());
   Drink eggFruit = new Egg(fruit);
   System.out.println(eggFruit.money());
   System.out.println(eggFruit.desc());
   ```

## 第5章 装饰者模式的真实应用
> 本章介绍装饰者模式的真实应用，包括场景分析和如何解决乱码问题。

### 5-1 装饰者模式真实场景案例分析 (04:44)

真实场景案例分析：解决乱码问题

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gakpwx8z5bj30yk0ih0u1.jpg" alt="客户端服务器乱码问题" style="zoom:35%;" />

### 5-2 乱码产生原因以及普通解决方式 (20:02)

1. 在pom.xml添加maven依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-freemarker</artifactId>
   </dependency>
   ```

2. 配置项目参数

   ```properties
   # application.properties
   spring.http.encoding.charset=ISO-8859-1
   # freemarker静态资源配置
   spring.freemarker.charset=UTF-8
   spring.freemarker.suffix=.html
   ```

3. 实现form表单

   ```html
   <!--templates/html/form.html-->
   <form action="/code" method="post">
       姓名:<input type="text" name="username"/><br>
       爱好:<input type="checkbox" name="hobby" value="抽烟"/>抽烟
           <input type="checkbox" name="hobby" value="喝酒"/>喝酒
           <input type="checkbox" name="hobby" value="烫头"/>烫头<br>
       <input type="submit" value="提交">
   </form>
   ```

4. 

   ```java
   @Controller
   @RequestMapping("/")
   public class CodeController {
   
       @GetMapping("form")
       public String form() {
           return "html/form";
       }
   
       @PostMapping("code")
       public String code(HttpServletRequest request) {
           // 获取姓名
           String username = request.getParameter("username");
           System.out.println(username);// ç¥ç§çå°å²å²
           // 获取爱好
           String[] hobbies = request.getParameterValues("hobby");
           System.out.println(Arrays.toString(hobbies));// [æ½ç, åé, ç«å¤´]
           return "";
       }
   }
   ```

解决乱码普通方式

1. 通过getBytes获取ISO-8859-1的byte，再进行转码UTF-8

   编码转换工具类

   ```java
   public class CodeUtil {
       /**
        * ISO-8859-1转UTF-8编码
        *
        * @param codename 需要进行转码的字符串
        * @return 转码后的字符串
        */
       public static String newCode(String codename) {
           byte[] bytes = codename.getBytes(StandardCharsets.ISO_8859_1);
           return new String(bytes, StandardCharsets.UTF_8);
       }
   }
   ```

   ```java
   System.out.println(CodeUtil.newCode(username));// 神秘的小岛岛
   for (String hobby : hobbies) {
       System.out.println(CodeUtil.newCode(hobby));// 抽烟, 喝酒, 烫头
   }
   ```

### 5-3 装饰者模式解决乱码问题 (16:05)

1. 抽象组件：ServletRequest

2. 被装饰者：HttpServletRequest

3. 装饰者组件（装饰器）：Decorators

   > 需要继承模板类

   ```java
   public class Decorators extends HttpServletRequestWrapper {
       // 持有接口的引用
       HttpServletRequest request;
       public Decorators(HttpServletRequest request) {
           super(request);
           this.request = request;
       }
   
       @Override
       public String getParameter(String name) {
           return super.getParameter(name);
       }
   
       @Override
       public String[] getParameterValues(String name) {
           return super.getParameterValues(name);
       }
   }
   ```

4. 具体装饰-getParameter

   > 需要继承装饰器的类

   ```java
   public class Parameter extends Decorators {
       public Parameter(HttpServletRequest request) {
           super(request);
       }
   
       // 对getParameter进行增强
       @Override
       public String getParameter(String name) {
           String value = super.getParameter(name);
           // 解决乱码
           byte[] bytes = value.getBytes(StandardCharsets.ISO_8859_1);
           return new String(bytes, StandardCharsets.UTF_8);
       }
   }
   ```

5. 具体装饰-getParameterValues

   ```java
   public class ParameterValues extends Decorators {
       public ParameterValues(HttpServletRequest request) {
           super(request);
       }
   
       // 对getParameterValues进行增强
       @Override
       public String[] getParameterValues(String name) {
           String[] values = super.getParameterValues(name);
           for (int i = 0; i < values.length; i++) {
               byte[] bytes = values[i].getBytes(StandardCharsets.ISO_8859_1);
               values[i] = new String(bytes, StandardCharsets.UTF_8);
           }
           return values;
       }
   }
   ```

6. 编码转换Filter

   ```java
   @WebFilter(filterName = "code", urlPatterns = "/code")
   public class CodeFilter implements Filter {
       @Override
       public void init(FilterConfig filterConfig) throws ServletException {
       }
   
       @Override
       public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
           HttpServletRequest request = (HttpServletRequest) servletRequest;
           HttpServletRequest codeRequest = new Parameter(new ParameterValues(request));
           filterChain.doFilter(codeRequest, servletResponse);
       }
   
       @Override
       public void destroy() {
       }
   }
   ```

7. 在启动类Application.java添加`@ServletComponentScan`注解

### 5-4 装饰者模式的优化写法 (07:53)

由解决编码案例可以得出：装饰者模式功能可拆卸

1. 具体装饰

   ```java
   public class NewDecorator extends HttpServletRequestWrapper {
       // 持有接口的引用
       private HttpServletRequest request;
       public NewDecorator(HttpServletRequest request) {
           super(request);
           this.request = request;
       }
   
       // 对getParameter进行增强
       @Override
       public String getParameter(String name) {
           String value = super.getParameter(name);
           byte[] bytes = value.getBytes(StandardCharsets.ISO_8859_1);
           return new String(bytes, StandardCharsets.UTF_8);
       }
   
       // 对getParameterValues进行增强
       @Override
       public String[] getParameterValues(String name) {
           String[] values = super.getParameterValues(name);
           for (int i = 0; i < values.length; i++) {
               byte[] bytes = values[i].getBytes(StandardCharsets.ISO_8859_1);
               values[i] = new String(bytes, StandardCharsets.UTF_8);
           }
           return values;
       }
   }
   ```

2. 编码转换doFilter()方法

   ```java
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletRequest request = (HttpServletRequest) servletRequest;
       // 简化方式
       HttpServletRequest codeRequest = new NewDecorator(request);
       filterChain.doFilter(codeRequest, servletResponse);
   }
   ```

### 5-5 io流中的装饰者模式的简单介绍 (06:30)

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1galmv0frywj31q20ri109.jpg" alt="Java IO流" style="zoom:30%;" />

Java中IO流的装饰者模式

> BufferedInputStream较FileInputStream读取速度快。

```java
// 构件抽象路径
File file = new File("aaa.txt");
// 公共file实例构件aaa.txt文件
FileInputStream fis = new FileInputStream(file);
// 使用缓冲输入流
BufferedInputStream bis = new BufferedInputStream(fis);
```

## 第6章 装饰者模式优缺点及课程总结
> 本章介绍装饰者模式的优缺点，并对课程内容进行总结。

### 6-1 装饰者模式的优缺点 (03:10)

优点：

- 目的在于扩展对象的功能。装饰者模式提供比继承更好的灵活性
  装饰是动态的，运行时可以修改的；继承是静态的，编译期便已确定好
- 通过使用不同的装饰类及对它们的排列组合，可以创造出许多不同行为的组合

缺点：

- 产生new很多的小对象，大量的小对象会占用内存
- 组合方式很多，很容易出错

### 6-2 装饰者模式的总结 (02:47)

- 熟悉装饰者模式的结构
  抽象组件、被装饰者、装饰器、具体装饰
- 了解装饰者模式在何种场景使用
  对一个对象进行增强、功能自由组合搭配
- 了解装饰者模式的优缺点


