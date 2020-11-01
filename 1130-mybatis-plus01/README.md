# MyBatis-Plus入门

> https://www.imooc.com/learn/1130
>
> 简介：Mybatis-Plus（MP）在 MyBatis 的基础上只做增强不做改变，简化开发、提高效率。本课程从现有持久层框架存在的问题切入，引出MP在项目开发中能为我们解决哪些问题。课程中细致地讲解了MP的核心功能；并且结合实际场景，讲解了某些方法在特定场景下的使用。如果想进一步提升，请观看《MyBatis-Plus进阶》课程，链接在此：https://www.imooc.com/learn/1171

- [第1章 课程简介及快速入门](#第1章-课程简介及快速入门)
  - [1-1 课程介绍及学习前须知](#1-1-课程介绍及学习前须知)
  - [1-2 MyBatis与JPA对比](#1-2-mybatis与jpa对比)
  - [1-3 MP简介](#1-3-mp简介)
  - [1-4 lombok简介及安装](#1-4-lombok简介及安装)
  - [1-5 快速入门小例子](#1-5-快速入门小例子)
- [第2章 基本使用](#第2章-基本使用)
  - [2-1 通用传统模式简介及通用mapper新增方法](#2-1-通用传统模式简介及通用mapper新增方法)
  - [2-2 常用注解](#2-2-常用注解)
  - [2-3 排除非表字段的三种方式](#2-3-排除非表字段的三种方式)
- [第3章 MyBatis-Plus查询方法](#第3章-mybatis-plus查询方法)
  - [3-1 普通查询](#3-1-普通查询)
  - [3-2 条件构造器查询（1）](#3-2-条件构造器查询1)
  - [3-3 条件构造器查询（2）](#3-3-条件构造器查询2)
  - [3-4 条件构造器查询（3）](#3-4-条件构造器查询3)
  - [3-5 条件构造器查询（4）](#3-5-条件构造器查询4)
  - [3-6 select不列出全部字段](#3-6-select不列出全部字段)
  - [3-7 condition作用](#3-7-condition作用)
  - [3-8 实体作为条件构造器构造方法的参数](#3-8-实体作为条件构造器构造方法的参数)
  - [3-9 AllEq用法](#3-9-alleq用法)
  - [3-10 其他使用条件构造器的方法](#3-10-其他使用条件构造器的方法)
  - [3-11 lambda条件构造器](#3-11-lambda条件构造器)
- [第4章 自定义sql及分页查询](#第4章-自定义sql及分页查询)
  - [4-1 自定义sql](#4-1-自定义sql)
    - [一、注解式查询](#一注解式查询)
    - [二、XML式查询](#二xml式查询)
  - [4-2 分页查询](#4-2-分页查询)
    - [selectPage](#selectpage)
    - [selectMapsPage](#selectmapspage)
    - [自定义SQL分页语句](#自定义sql分页语句)
- [第5章 更新及删除](#第5章-更新及删除)
  - [5-1 更新方法](#5-1-更新方法)
  - [5-2 删除方法](#5-2-删除方法)
- [第6章 AR模式、主键策略和基本配置](#第6章-ar模式主键策略和基本配置)
  - [6-1 AR模式](#6-1-ar模式)
  - [6-2 主键策略](#6-2-主键策略)
    - [MP支持的主键策略介绍](#mp支持的主键策略介绍)
    - [局部主键策略实现](#局部主键策略实现)
    - [全局主键策略实现](#全局主键策略实现)
  - [6-3 基本配置](#6-3-基本配置)
    - [基本配置](#基本配置)
    - [进阶配置](#进阶配置)
    - [DB策略配置](#db策略配置)
- [第7章 通用service](#第7章-通用service)
  - [7-1 通用service](#7-1-通用service)
    - [基本方法](#基本方法)
    - [批量操作方法](#批量操作方法)
    - [链式调用方法](#链式调用方法)

## 第1章 课程简介及快速入门

> 本章主要介绍课程内容及快速入门的小案例，带你快速了解MyBatis-Plus

### 1-1 课程介绍及学习前须知

需要掌握的技能：Lambda表达式、SpringBoot，Maven、Mybatis

开发环境：IDEA2020.2、JDK1.8、Maven3.6、MySQL8.0

### 1-2 MyBatis与JPA对比

|      | Mybatis                                                      | JPA                                                          |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 优势 | SQL语句可以自由控制，灵活，性能较高<br />SQL与代码分离，易于阅读和维护<br />提供XML标签，支持编写动态SQL语句 | JPA移植性比较好（JPQL）<br />提供了很多CRUD方法，开发效率高<br />对象化程度更高 |
| 劣势 | 简单CRUD操作还得写SQL语句<br />XML中有大量的SQL要维护<br />Mybatis自身功能有限，但支持插件 |                                                              |

### 1-3 MP简介

> MP是一个Mybatis的增强工具，只做增强不做改变

官网地址：https://mybatis.plus/

GitHub：https://github.com/baomidou/mybatis-plus

Gitee：https://gitee.com/baomidou/mybatis-plus

Mybatis-Plus框架结构

<img src="https://tva1.sinaimg.cn/large/0081Kckwgy1gk35gux3c4j310y0no43b.jpg" alt="Mybatis-Plus框架结构.jpg" width="50%;" />

[Mybatis-Plus特性](https://baomidou.com/guide/#%E7%89%B9%E6%80%A7)

- 无侵入、损耗小、强大的CRUD操作
- 支持Lambda形式调用、支持多种数据库
- 支持主键自动生成、支持ActiveRecord模式
- 支持自定义全局通用操作、支持关键词自动转义
- 内置代码生成器、内置分页插件、内置性能分析插件
- 内置全局拦截插件、内置SQL注入剥离器

### 1-4 lombok简介及安装

### 1-5 快速入门小例子

流程：建库建表、引入依赖、配置、编码、测试

1. 建库建表

   ```mysql
   DROP DATABASE IF EXISTS `1130-mybatis-plus01`;
   CREATE DATABASE `1130-mybatis-plus01`;
   use `1130-mybatis-plus01`;
   
   -- 创建用户表
   DROP TABLE IF EXISTS user;
   CREATE TABLE user
   (
     id          BIGINT(20) PRIMARY KEY NOT NULL COMMENT '主键',
     name        VARCHAR(30) DEFAULT NULL COMMENT '姓名',
     age         INT(11)     DEFAULT NULL COMMENT '年龄',
     email       VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
     manager_id  BIGINT(20)  DEFAULT NULL COMMENT '直属上级id',
     create_time DATETIME    DEFAULT NULL COMMENT '创建时间',
     CONSTRAINT manager_fk FOREIGN KEY (manager_id) REFERENCES user (id)
   ) ENGINE = INNODB CHARSET = UTF8;
   
   -- 初始化数据
   INSERT INTO user (id, name, age, email, manager_id, create_time)
   VALUES (1087982257332887553, '大boss', 40, 'boss@baomidou.com', NULL, '2019-01-11 14:20:20'),
          (1088248166370832385, '王天风', 25, 'wtf@baomidou.com', 1087982257332887553, '2019-02-05 11:12:22'),
          (1088250446457389058, '李艺伟', 28, 'lyw@baomidou.com', 1088248166370832385, '2019-02-14 08:31:16'),
          (1094590409767661570, '张雨琪', 31, 'zjq@baomidou.com', 1088248166370832385, '2019-01-14 09:15:15'),
          (1094592041087729666, '刘红雨', 32, 'lhm@baomidou.com', 1088248166370832385, '2019-01-14 09:48:16');
   
   ```

2. 引入依赖

   ```xml
   <dependency>
     <groupId>org.projectlombok</groupId>
     <artifactId>lombok</artifactId>
     <optional>true</optional>
   </dependency>
   <dependency>
     <groupId>com.baomidou</groupId>
     <artifactId>mybatis-plus-boot-starter</artifactId>
     <version>3.4.0</version>
   </dependency>
   <dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
   </dependency>
   ```

3. 配置

   ```yaml
   spring:
     datasource:
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3306/1130-mybatis-plus01?useSSL=false&serverTimezone=GMT%2B8
       username: root
       password: 123456
   ```

4. 编码

   ```java
   @Data
   @AllArgsConstructor
   public class User {
     private Long id; // 主键
     private String name; // 姓名
     private Integer age; // 年龄
     private String email; // 邮箱
     private Long managerId; // 直属上级
     private LocalDate createTime; // 创建时间
   }
   ```

   ```java
   @Mapper
   public interface UserMapper extends BaseMapper<User> {
   }
   ```

5. 测试

   ```java
   @Autowired
   private UserMapper userMapper;
   ```

   ```java
   // 参数为null表示查询全部数据
   List<User> userList = userMapper.selectList(null);
   Assertions.assertEquals(5, userList.size());
   userList.forEach(System.out::println);
   ```

## 第2章 基本使用

> 本章介绍MyBatis-Plus的通用Mapper方式、常用注解及排除非表字段的三种方式。

### 2-1 通用传统模式简介及通用mapper新增方法

SSM传统编程模式

> 接口中写抽象方法、XML或注解写SQL、Service中调用接口、Controller中调用

```yaml
## 日志配置
logging:
  level:
    root: warn
    com.tuyrk.mybatisplus.crud.dao: trace
  pattern:
    console: '%p%m%n'
```

```java
User user = new User(null, "刘明强", 31, null, 1088248166370832385L, LocalDate.now());
int rows = userMapper.insert(user);
Assertions.assertEquals(1, rows);
```

### 2-2 常用注解

```mysql
## 更改表名
rename table user to mp_user;
## 更改主键名称
alter table mp_user change id user_id bigint not null comment '主键';
```

```java
@Data
@AllArgsConstructor
@TableName("mp_user")
public class User {
  // @TableId("user_id")
  // private Long id;
  @TableId
  private Long userId; // 主键
  @TableField("name")
  private String realName; // 姓名
  private Integer age; // 年龄
  private String email; // 邮箱
  private Long managerId; // 直属上级
  private LocalDate createTime; // 创建时间
}
```

### 2-3 排除非表字段的三种方式

> 插入操作时，忽略实体类中存在但数据库中不存在的字段

```java
private transient String remark; // 备注
```

```java
private static String remark; // 备注
public static String getRemark() { return remark; }
public static void setRemark(String remark) { User.remark = remark; }
```

```java
@TableField(exist = false)
private String remark; // 备注
```

## 第3章 MyBatis-Plus查询方法

> 本章主要介绍MyBatis-Plus查询的主要内容，包括**普通查询**、**条件构造器查询**、**select不列出全部字段查询**等内容。

### 3-1 普通查询

- selectById

  ```java
  User user = userMapper.selectById(1094590409767661570L);
  Assertions.assertEquals(1094590409767661570L, user.getId());
  ```

- selectById

  ```java
  List<Long> ids = Arrays.asList(1094590409767661570L, 1088248166370832385L, 1094592041087729666L);
  List<User> userList = userMapper.selectBatchIds(ids); // 查询
  userList.forEach(System.out::println); // 打印
  ```
  
- selectByMap
  
  ```java
  HashMap<String, Object> columnMap = new HashMap<>();
  columnMap.put("name", "王天风");
  columnMap.put("age", 25);
  // where name='王天风' and age=25
  // key为列名，而不是实体的属性名
  List<User> userList = userMapper.selectByMap(columnMap); // 查询
  userList.forEach(System.out::println);
  ```

### 3-2 条件构造器查询（1）

AbstractWrapper

1. 名字中包含雨并且年龄小于40

   ```mysql
   name like '%雨%' and age<40
   ```
   
   ```java
   QueryWrapper<User> queryWrapper = new QueryWrapper<>();
   // QueryWrapper<Object> queryWrapper = Wrappers.query();
   queryWrapper.like("name", "雨").lt("age", 40);
   List<User> userList = userMapper.selectList(queryWrapper); // 查询
   userList.forEach(System.out::println);
   ```
   
2. 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空

   ```mysql
   name like '%雨%' and age between 20 and 40 and email is not null
   ```

   ```java
   queryWrapper.like("name", "雨").between("age", 20, 40).isNotNull("email");
   ```

3. 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列

   ```mysql
   name like '王%' or age>=25 order by age desc,id asc
   ```

   ```java
   queryWrapper.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
   ```

### 3-3 条件构造器查询（2）

4. 创建日期为2019年2月14日并且直属上级为名字为王姓

   ```mysql
   date_format(create_time,'%Y-%m-%d')='2019-02-14' 
   and manager_id in (select id from user where name like '王%')
   ```

   ```java
   queryWrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", "2020-10-27")
     .inSql("manager_id", "select id from user where name like '王%'");
   ```

   {0}可以防止SQL注入

### 3-4 条件构造器查询（3）

5. 名字为王姓并且（年龄小于40或邮箱不为空）

   ```mysql
   name like '王%' and (age<40 or email is not null)
   ```

   ```java
   queryWrapper.likeRight("name", "王")
     .and(qw -> qw.lt("age", 40).or().isNotNull("email"));
   ```
   
6. 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）

   ```mysql
   name like '王%' or (age<40 and age>20 and email is not null)
   ```

   ```java
   queryWrapper.likeRight("name", "王")
     .or(qw -> qw.lt("age", 40).gt("age", 20).isNotNull("email"));
   ```

### 3-5 条件构造器查询（4）

7. （年龄小于40或邮箱不为空）并且名字为王姓

   ```mysql
   (age<40 or email is not null) and name like '王%'
   ```

   ```java
   queryWrapper.nested(qw -> qw.lt("age", 40).or().isNotNull("email"))
     .likeRight("name", "王");
   ```

8. 年龄为30、31、34、35

   ```mysql
   age in (30、31、34、35)
   ```

   ```java
   queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));
   ```

9. 只返回满足条件的其中一条语句即可

   ```mysql
   limit 1
   ```

   ```java
   queryWrapper.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");
   ```

### 3-6 select不列出全部字段

10. 名字中包含雨并且年龄小于40(需求1加强版)

    - 仅列出某些字段

      ```mysql
      select id, name from user
         where name like '%雨%' and age < 40;
      ```

      ```java
      queryWrapper.select("id", "name")
        .like("name", "雨").lt("age", 40);
      ```

      注：使用Lombok时，添加了@AllArgsConstructor将会没有无参构造函数，需要添加@NoArgsConstructor，否则使用.select()会抛出异常

    - 排除某些字段

      ```mysql
      select id, name, age, email
         from user
         where name like '%雨%' and age < 40;
      ```

      ```java
      queryWrapper.select(User.class, info -> {
        return !Objects.equals("manager_id", info.getColumn())
          && !Objects.equals("create_time", info.getColumn());
      }).like("name", "雨").lt("age", 40);
      ```

### 3-7 condition作用

> 是否将其作为查询条件加入到执行的SQL语句。true是,false否

```java
if (StringUtils.isNotBlank(name)) {
  queryWrapper.like("name", name);
}
if (StringUtils.isNotBlank(email)) {
  queryWrapper.like("email", email);
}
```

```java
queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
  .like(StringUtils.isNotBlank(email), "email", email);
```

### 3-8 实体作为条件构造器构造方法的参数

> 使用实体作为条件构造器构造方法的参数时，将实体中**不为空**的属性值作为条件**默认等值**方式进行查询

可使用如下注解修改默认等值的查询方式

```java
@TableField(condition = SqlCondition.LIKE)
private String name; // 姓名
@TableField(condition = "%s&lt;#{%s}") // <
private Integer age; // 年龄
```

```java
User user = new User();
user.setName("刘红雨");
user.setAge(32);
QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);

queryWrapper.like("name", "雨").lt("age", 40);
```

### 3-9 AllEq用法

[allEq](https://mybatis.plus/guide/wrapper.html#alleq)

> `params` : `key`为数据库字段名,`value`为字段值
>
> `null2IsNull` : 为`true`则在`map`的`value`为`null`时调用 [isNull](https://mybatis.plus/guide/wrapper.html#isnull) 方法,为`false`时则忽略`value`为`null`的
>
> `filter` : 过滤函数,是否允许字段传入比对条件中

```java
allEq(Map<R, V> params)
allEq(Map<R, V> params, boolean null2IsNull)
allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
allEq(BiPredicate<R, V> filter, Map<R, V> params)
```

```java
QueryWrapper<User> queryWrapper = new QueryWrapper<>();
Map<String, Object> params = new HashMap<>();
params.put("name", "王天风");
params.put("age", null);

queryWrapper.allEq(params, false);
List<User> userList1 = userMapper.selectList(queryWrapper);
userList1.forEach(System.out::println);

queryWrapper.clear();

queryWrapper.allEq((k, v) -> !Objects.equals("name", k), params);
List<User> userList2 = userMapper.selectList(queryWrapper);
userList2.forEach(System.out::println);
```

### 3-10 其他使用条件构造器的方法

11. 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组。

    ```mysql
    select avg(age) avg_age, min(age) min_age, max(age) max_age
    from user
    group by manager_id
    having sum(age) < 500;
    
    ```

1. selectMaps

2. selectObjs

   只返回第一个字段的值

3. selectCount

   查询总记录数

4. selectOne

   查询一条记录，可以为null，不能为多条数据

### 3-11 lambda条件构造器

```java
// LambdaQueryWrapper<User> lambdaQuery = new QueryWrapper<User>().lambda();
// LambdaQueryWrapper<User> lambdaQuery = new LambdaQueryWrapper<>();
// LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery(User.class);
LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
```

```java
lambdaQuery.like(User::getName, "雨").lt(User::getAge, 40);
```

```java
lambdaQuery.likeRight(User::getAge, "王")
  .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
```

```java
List<User> userList3 = new LambdaQueryChainWrapper<>(userMapper)
  .like(User::getName, "雨").ge(User::getAge, 20).list();
```

## 第4章 自定义sql及分页查询

> 本章介绍MyBatis-Plus中自定义sql和分页查询的内容。

### 4-1 自定义sql

#### 一、注解式查询

UserMapper.java

```java
@Select("select * from user ${ew.customSqlSegment}")
List<User> selectAll(@Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
                     Wrapper<User> wrapper);
```

Test.java

```java
lambdaQuery.like(User::getName, "雨").lt(User::getAge, 40);
List<User> userList1 = userMapper.selectAllByAnnotation(lambdaQuery);
```

#### 二、XML式查询

UserMapper.java

```java
List<User> selectAllByXml(@Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
                          Wrapper<User> wrapper);
```

application.yml

```java
mybatis-plus:
  mapper-locations:
    - classpath*:/mapper/**/*.xml
```

UserMapper.xml

```xml
<select id="selectAllByXml" resultType="com.tuyrk.mybatisplus.quickstart.entity.User">
  select * from user ${ew.customSqlSegment}
</select>
```

Test.java

```java
lambdaQuery.like(User::getName, "雨").lt(User::getAge, 40);
List<User> userList1 = userMapper.selectAllByXml(lambdaQuery);
```

### 4-2 分页查询

```java
@Configuration
public class MybatisPlusConfig {
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }
}
```

#### selectPage

```java
Page<User> page = new Page<>(1, 2);
Page<User> userIPage = userMapper.selectPage(page, lambdaQuery);

System.out.println("总页数：" + userIPage.getPages());
System.out.println("总记录数：" + userIPage.getTotal());
List<User> userList = userIPage.getRecords();
```

#### selectMapsPage

```java
Page<Map<String, Object>> page = new Page<>(1, 2, false);
Page<Map<String, Object>> mapsPage = userMapper.selectMapsPage(page, lambdaQuery);
```

#### 自定义SQL分页语句

UserMapper.java

```java
IPage<User> selectUserPage(Page<User> page, @Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
                           Wrapper<User> wrapper);
```

UserMapper.xml

```xml
<select id="selectUserPage" resultType="com.tuyrk.mybatisplus.quickstart.entity.User">
  select * from user ${ew.customSqlSegment}
</select>
```

Test.java

```java
Page<User> page = new Page<>(1, 2);
IPage<User> userIPage = userMapper.selectUserPage(page, lambdaQuery);
```

## 第5章 更新及删除

> 介绍MyBatis-Plus中更新和删除功能的使用。

### 5-1 更新方法

- 根据ID更新
- 以条件构造器作为参数的更新方法
- 条件构造器中set方法使用

1. 根据ID更新

   ```java
   User user = new User();
   user.setId(1088248166370832385L); // where 
   user.setAge(26); // set
   user.setEmail("wtf2@baomidou.com"); // set
   
   int rows = userMapper.updateById(user);
   ```

2. 以条件构造器作为参数的更新方法

   ```java
   UpdateWrapper<User> updateWrapper = Wrappers.update(); // where
   updateWrapper.eq("name", "李艺伟").eq("age", 28);
   
   User user = new User(); // set
   user.setEmail("lyw2020@baomidou.com");
   user.setAge(29);
   
   int rows = userMapper.update(user, updateWrapper);
   ```

   ```java
   User whereUser = new User();
   whereUser.setName("李艺伟");
   UpdateWrapper<User> updateWrapper = Wrappers.update(whereUser); // where
   updateWrapper.eq("name", "李艺伟").eq("age", 28);
   
   User user = new User(); // set
   user.setEmail("lyw2020@baomidou.com");
   user.setAge(29);
   
   int rows = userMapper.update(user, updateWrapper);
   ```

3. 条件构造器中set方法使用

   ```java
   UpdateWrapper<User> updateWrapper = Wrappers.update();
   updateWrapper.eq("name", "李艺伟").eq("age", 29) // where
     .set("age", 30); // set
   int rows = userMapper.update(null, updateWrapper);
   ```

4. 条件构造器Lambda条件参数

   ```java
   LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.lambdaUpdate();
   lambdaUpdate.eq(User::getName, "李艺伟").eq(User::getAge, 30)
     .set(User::getAge, 31);
   int rows = userMapper.update(null, lambdaUpdate);
   ```

5. 条件构造器Lambda条件参数链式更新调用

   ```java
   boolean rows = ChainWrappers.lambdaUpdateChain(userMapper)
     .eq(User::getName, "李艺伟").eq(User::getAge, 30)
     .set(User::getAge, 31)
     .update();
   ```

### 5-2 删除方法

- 根据ID删除
- 其他普通删除方法
- 以条件构造器为参数的删除方法

1. 根据ID删除

   ```java
   int rows = userMapper.deleteById(1322819611623723009L);
   ```

2. 根据map参数删除

   ```java
   HashMap<String, Object> columnMap = new HashMap<>();
   columnMap.put("name", "刘明强");
   columnMap.put("age", 25);
   int rows = userMapper.deleteByMap(columnMap);
   ```

3. 根据ID集合删除

   ```java
   int rows = userMapper.deleteBatchIds(Arrays.asList(1321099776439136258L, 1321100685084811265L, 1321100748422971393L));
   ```

4. 以条件构造器为参数的删除方法

   ```java
   LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
   lambdaQuery.eq(User::getAge, 27).or().gt(User::getAge, 41);
   int rows = userMapper.delete(lambdaQuery);
   ```

## 第6章 AR模式、主键策略和基本配置

> 本章介绍MyBatis-Plus中的AR模式、主键策略和基本配置等内容。

### 6-1 AR模式

ActiveRecord模式

- AR模式简介：活动记录，领域模型模式

- MP中AR模式的实现

1. 在实体类继承Model，并添加serialVersionUID

   ```java
   @Data
   @EqualsAndHashCode(callSuper = false)
   public class User extends Model<User> {
     private static final long serialVersionUID = -3116126971888623558L;
     // ... //
   }
   ```

2. insert

   ```java
   User user = new User();
   // user.setXXX();
   boolean insert = user.insert();
   ```

3. selectById

   > user != userSelect

   ```java
   User user = new User();
   User userSelect = user.selectById(1322869223491497986L);
   ```

   ```java
   User user = new User();
   user.setId(1322869223491497986L);
   User userSelect = user.selectById();
   ```

4. updateById

   ```java
   User user = new User();
   user.setId(1322869223491497986L); // where
   user.setName("张草草"); // set
   boolean update = user.updateById();
   ```

5. deleteById

   ```java
   User user = new User();
   boolean update = user.deleteById(1322869223491497986L);
   ```

6. insertOrUpdate

   > user有id则执行更新操作，没有id则执行插入操作

   ```java
   User user = new User();
   user.setId(1322871394721492993L);
   // user.setXXX();
   boolean insert = user.insertOrUpdate();
   ```

### 6-2 主键策略

#### MP支持的主键策略介绍

```java
@Getter
@AllArgsConstructor
public enum IdType {
  AUTO(0), // 数据库ID自增
  NONE(1), // 该类型为未设置主键类型(将跟随全局)
  INPUT(2), // 用户输入ID，该类型可以通过自己注册自动填充插件进行填充
  ASSIGN_ID(3), ASSIGN_UUID(4),
  @Deprecated ID_WORKER(3),
  @Deprecated ID_WORKER_STR(3),
  @Deprecated UUID(4);

  private final int key;
}
```

#### 局部主键策略实现

```java
@TableId(type = IdType.NONE)
private Long id;
```

#### 全局主键策略实现

```yaml
mybatis-plus:
  global-config:
    db-config:
      id-type: none
```

注：局部主键策略优于全局主键策略

### 6-3 基本配置

#### 基本配置

1. [configLocation](https://baomidou.com/config/#configlocation)

   ```yaml
   mybatis-plus:
     config-location: classpath*:/mybatis-config.xml
   ```

   ```xml
   <!-- mybatis-config.xml -->
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
   </configuration>
   ```

2. [mapperLocations](https://baomidou.com/config/#mapperlocations)

   > Maven 多模块项目的扫描路径需以 classpath*: 开头 （即加载多个 jar 包下的 XML 文件）

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="">
   </mapper>
   ```

3. [typeAliasesPackage](https://baomidou.com/config/#typealiasespackage)

   > MyBaits 别名包扫描路径，通过该属性可以给包中的类注册别名，注册后在 Mapper 对应的 XML 文件中可以直接使用类名，而不用使用全限定的类名(即 XML 中调用的时候不用包含包名)

   ```yaml
   mybatis-plus:
     type-aliases-package: com.tuyrk.mybatisplus.quickstart.entity
   ```

#### 进阶配置

1. [mapUnderscoreToCamelCase](https://baomidou.com/config/#configuration)

   > 是否开启自动驼峰命名规则（camel case）映射。默认为true，原生Mybatis默认为false

   ```yaml
   mybatis-plus:
     # config-location: classpath:mybatis-config.xml
     configuration:
       map-underscore-to-camel-case: true
   ```

   注：configuration和configLocation不能同时配置，否则会报错

#### DB策略配置

1. [insertStrategy](https://baomidou.com/config/#insertstrategy)

   ```java
   public enum FieldStrategy {
     IGNORED, // 忽略判断
     NOT_NULL, // 非NULL判断
     NOT_EMPTY, // 非空判断(只对字符串类型字段,其他类型字段依然为非NULL判断)
     DEFAULT, // 默认的,一般只用于注解里。在全局里代表NOT_NULL；在注解里代表跟随全局
     NEVER; // 不加入SQL
   }
   ```

   全局配置

   ```yaml
   mybatis-plus:
     global-config:
       db-config:
         insert-strategy: ignored
   ```

   局部配置

   ```java
   @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
   private String email;
   ```

2. [idType](https://baomidou.com/config/#idtype)

   > 全局默认主键类型

3. [tablePrefix](https://baomidou.com/config/#tableprefix)

   > 表名前缀

   ```yaml
   mybatis-plus:
     global-config:
       db-config:
         table-prefix: mp_
   ```

   ```mysql
   SELECT * FROM mp_user;
   ```

4. [tableUnderline](https://baomidou.com/config/#tableunderline)

   > 表名是否使用驼峰转下划线命名,只对表名生效

## 第7章 通用service

> 本章介绍通用service的内容。

### 7-1 通用service

```java
public interface UserService extends IService<User> {
}
```

```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
```

#### 基本方法

1. getOne

   ```java
   LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
   lambdaQuery.gt(User::getAge, 25);
   User user1 = userService.getOne(lambdaQuery, false); // SUCCESS
   User user2 = userService.getOne(lambdaQuery); // TooManyResultsException
   ```

#### 批量操作方法

1. saveBatch
2. saveOrUpdateBatch

#### 链式调用方法

1. 链式查询

   ```java
   List<User> userList = userService.lambdaQuery()
     .gt(User::getAge, 25).like(User::getName, "雨") // where
     .list();
   ```

2. 链式更新

   ```java
   boolean update = userService.lambdaUpdate()
     .ge(User::getAge, 25) // where
     .set(User::getAge, 26) // set
     .update();
   ```

3. 链式删除

   ```java
   boolean remove = userService.lambdaUpdate()
     .ge(User::getAge, 25)
     .remove();
   ```

   

