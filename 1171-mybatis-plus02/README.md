# MyBatis-Plus进阶

> https://www.imooc.com/learn/1171
>
> 简介：本课程是《MyBatis-Plus入门》的延续，在之前的基础上，继续介绍MyBatis-Plus的高级功能，包括逻辑删除、自动填充、乐观锁、性能分析、多租户实现、动态表和SQL注入器等内容。理论加实践相结合的方式进行讲解，定能让你的MyBatis-Plus技能进一步提升。

## 第1章 概述

> 本章介绍课程的主要内容，课程涉及的表结构，以及项目的基本情况。

### 1-1 高级功能课程简介

逻辑删除、自动填充、乐观锁插件

性能分析插件、多租户SQL解析器

动态表名SQL解析器、SQL注入器

### 1-2 基础数据表和基础项目介绍

创建用户表

```mysql
CREATE TABLE user (
  id          BIGINT(20) PRIMARY KEY NOT NULL COMMENT '主键',
  name        VARCHAR(30) DEFAULT NULL COMMENT '姓名',
  age         INT(11)     DEFAULT NULL COMMENT '年龄',
  email       VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
  manager_id  BIGINT(20)  DEFAULT NULL COMMENT '直属上级id',
  create_time DATETIME    DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME    DEFAULT NULL COMMENT '修改时间',
  version     INT(11)     DEFAULT '1' COMMENT '版本',
  deleted     INT(1)      DEFAULT '0' COMMENT '逻辑删除标识(0.未删除,1.已删除)',
  CONSTRAINT manager_fk FOREIGN KEY (manager_id) REFERENCES user (id)
) ENGINE = INNODB CHARSET = UTF8 COMMENT '用户表';
```

初始化数据

```mysql
INSERT INTO user (id, name, age, email, manager_id, create_time)
VALUES (1087982257332887553, '大boss', 40, 'boss@baomidou.com', NULL, '2019-01-11 14:20:20'),
       (1088248166370832385, '王天风', 25, 'wtf@baomidou.com', 1087982257332887553, '2019-02-05 11:12:22'),
       (1088250446457389058, '李艺伟', 28, 'lyw@baomidou.com', 1088248166370832385, '2019-02-14 08:31:16'),
       (1094590409767661570, '张雨琪', 31, 'zjq@baomidou.com', 1088248166370832385, '2019-01-14 09:15:15'),
       (1094592041087729666, '刘红雨', 32, 'lhm@baomidou.com', 1088248166370832385, '2019-01-14 09:48:16');
```

实体类

```java
@Data
public class User extends Model<User> {
  private static final long serialVersionUID = 2973225901816678814L;
  @TableId
  private Long id; // 主键
  @TableField(condition = SqlCondition.LIKE)
  private String name; // 姓名
  @TableField(condition = "%s&lt;#{%s}") // <
  private Integer age; // 年龄
  @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
  private String email; // 邮箱
  private Long managerId; // 直属上级
  private LocalDateTime createTime; // 创建时间
  private LocalDateTime updateTime; // 修改时间
  private Integer version; // 版本
  private Integer deleted; // 逻辑删除标识（0未删除，1已删除）
}
```


## 第2章 逻辑删除

> 本章主要介绍MyBatis-Plus中逻辑删除的内容。

### 2-1 逻辑删除简介

> 逻辑删除：通过标识字段是否是删除状态确定数据是否删除

### 2-2 逻辑删除实现

#### 逻辑删除标识（默认0未删除，1已删除）

```yaml
mybatis-plus:
  global-config:
    db-config:
      logic-not-delete-value: 0 # 未删除
      logic-delete-value: 1 # 已删除
```

#### 配置（高版本可不配置）

```java
@Configuration
public class MybatisPlusConfig {
  @Bean public ISqlInjector sqlInjector() {
    return new DefaultSqlInjector();
  }
}
```

#### 实体类添加逻辑删除标识注解

```java
@TableLogic
private Integer deleted; // 逻辑删除标识（0未删除，1已删除）
```

#### 测试

1. 删除

   ```java
   userMapper.deleteById(1094592041087729666L);
   ```

   ```mysql
   UPDATE user SET deleted=1 WHERE id=? AND deleted=0
   ```

2. 查询

   ```java
   userMapper.selectList(null);
   ```

   ```mysql
   SELECT *,deleted FROM user WHERE deleted=0
   ```

3. 更新

   ```java
   User user = new User();
   user.setAge(26);
   user.setId(1088248166370832385L);
   int rows = userMapper.updateById(user);
   ```

   ```mysql
   UPDATE user SET age=? WHERE id=? AND deleted=0
   ```

### 2-3 查询中排除删除标识字段及注意事项

```java
@TableLogic
@TableField(select = false)
private Integer deleted; // 逻辑删除标识（0未删除，1已删除）
```

注：自定义SQL不会在生成的SQL语句中添加逻辑位删除标识的查询字段

```java
@Select("select * from user ${ew.customSqlSegment}")
List<User> mySelectList(@Param(Constants.WRAPPER) Wrapper<User> wrapper);
```

1. wrapper手动添加条件

   ```java
   List<User> userList = userMapper.mySelectList(
     Wrappers.<User>lambdaQuery().gt(User::getAge, 25).eq(User::getDeleted, 0)
   );
   ```

2. SQL中添加查询条件


## 第3章 自动填充

> 本章主要介绍MyBatis-Plus自动填充及优化的内容。

### 3-1 自动填充简介

> 自动记录新增或删除数据的操作人员、操作时间等

### 3-2 自动填充实现

#### 实体类添加自动填充标识注解

```java
@TableField(fill = FieldFill.INSERT)
private LocalDateTime createTime; // 创建时间
@TableField(fill = FieldFill.UPDATE)
private LocalDateTime updateTime; // 修改时间
```

#### 自动填充Handler

```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  @Override public void insertFill(MetaObject metaObject) {
    strictInsertFill(metaObject, "createTime", java.time.LocalDateTime.class, LocalDateTime.now());
  }

  @Override public void updateFill(MetaObject metaObject) {
    strictUpdateFill(metaObject, "updateTime", java.time.LocalDateTime.class, LocalDateTime.now());
  }
}
```

#### 测试

1. 新增

   ```java
   User user = new User();
   user.setName("刘明超");
   user.setAge(31);
   user.setEmail("lmc@baomidou.com");
   user.setManagerId(1088248166370832385L);
   int rows = userMapper.insert(user);
   ```

   ```mysql
   INSERT INTO user ( id, name, age, email, manager_id, create_time ) VALUES ( ?, ?, ?, ?, ?, ? )
   ```

2. 更新

   ```java
   User user = new User();
   user.setAge(27);
   user.setId(1088248166370832385L);
   int rows = userMapper.updateById(user);
   ```

   ```mysql
   UPDATE user SET age=?, update_time=? WHERE id=? AND deleted=0
   ```

### 3-3 自动填充优化

> 有时候要填充的值需要进行一系列的调用操作才能获取，这些方法会有一定开销，避免多余的执行操作

```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  @Override public void insertFill(MetaObject metaObject) {
    boolean hasSetter = metaObject.hasSetter("createTime");
    if (hasSetter) {
      strictInsertFill(metaObject, "createTime", java.time.LocalDateTime.class, LocalDateTime.now());
    }
  }

  @Override public void updateFill(MetaObject metaObject) {
    Object val = getFieldValByName("updateTime", metaObject);
    if (Objects.isNull(val)) {
      strictUpdateFill(metaObject, "updateTime", java.time.LocalDateTime.class, LocalDateTime.now());
    }
  }
}
```


## 第4章 乐观锁

> 本章介绍MyBatis-Plus乐观锁的实现。

### 4-1 乐观锁简介

1. 取出记录时，获取当前version
2. 更新时，带上这个version
3. 版本正确更新成功，错误更新失败

读操作多使用乐观锁，写操作多使用悲观锁

```mysql
update table
set version = newVersion, x = a, y = b
where version = oldVersion and z = c;
```

例：

```mysql
update user
set name = '向南天', version = 3
where id = 123123123 and version = 2
```

### 4-2 乐观锁实现及注意事项

> https://baomidou.com/guide/interceptor-optimistic-locker.html

#### 实现步骤

1. 配置

   ```java
   @Bean
   public MybatisPlusInterceptor mybatisPlusInterceptor() {
     MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
     // 乐观锁
     mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
     return mybatisPlusInterceptor;
   }
   ```

2. 实体类添加注解

   ```java
   @Version
   private Integer version;
   ```

3. 测试OptTest

   ```java
   int version = 2;
   User user = new User();
   user.setId(1324399185079832578L);
   user.setVersion(version);
   user.setEmail("lmm2@baomidou.com");
   int rows = userMapper.updateById(user);
   ```

   ```mysql
   UPDATE user 
   SET email='lmm2@baomidou.com',
       update_time=2020-11-11T00:30:12.027,
       version=2
   WHERE id=1324399185079832578 AND version=1 AND deleted=0;
   ```

#### 注意事项

- **支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime**

- 整数类型下 `newVersion = oldVersion + 1`

- `newVersion` 会回写到 `entity` 中

- 仅支持 `updateById(id)` 与 `update(entity, wrapper)` 方法

- **在 `update(entity, wrapper)` 方法下, `wrapper` 不能复用!!!**

  ```java
  int version = 2;
  User user = new User();
  user.setVersion(version);
  user.setEmail("lmm3@baomidou.com");
  LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery()
    .eq(User::getId, 1324399185079832578L);
  userMapper.update(user, queryWrapper);
  
  int version2 = 4;
  user.setVersion(version2);
  user.setEmail("lmm4@baomidou.com");
  userMapper.update(user, queryWrapper);
  ```

  ```mysql
  UPDATE user
  SET email='lmm3@baomidou.com',
      update_time=2020-11-11T00:47:04.215,
      version=3
  WHERE deleted=0 AND (id = 1324399185079832578 AND version = 2);
  ```

  ```mysql
  UPDATE user
  SET email='lmm4@baomidou.com',
      update_time=2020-11-11T00:47:04.418,
      version=4
  WHERE deleted=0 AND (id = 1324399185079832578 AND version = 2 AND version = 3);
  ```


## 第5章 性能分析

> 本章主要介绍MyBatis-Plus的性能分析、参数设置和执行sql分析打印等内容。

### 5-1 性能分析实现及参数设置

该插件 `3.2.0` 以上版本移除推荐使用第三方扩展 `执行 SQL 分析打印` 功能

### 5-2 执行sql分析打印

> https://baomidou.com/guide/p6spy.html

1. 引入依赖

   ```xml
   <dependency>
     <groupId>p6spy</groupId>
     <artifactId>p6spy</artifactId>
     <version>3.9.1</version>
   </dependency>
   ```

2. 修改数据源配置

   ```yaml
   spring:
     datasource:
       driver-class-name: com.p6spy.engine.spy.P6SpyDriver
       url: jdbc:p6spy:mysql://localhost:3306/1171-mybatis-plus02?useSSL=false&serverTimezone=GMT%2B8
   ```

3. spy.properties 配置

   ```properties
   # 3.2.1以上使用
   modulelist=com.baomidou.mybatisplus.extension.p6spy.MybatisPlusLogFactory,com.p6spy.engine.outage.P6OutageFactory
   # 3.2.1以下使用或者不配置
   #modulelist=com.p6spy.engine.logging.P6LogFactory,com.p6spy.engine.outage.P6OutageFactory
   # 自定义日志打印
   logMessageFormat=com.baomidou.mybatisplus.extension.p6spy.P6SpyLogger
   # 日志输出到控制台
   appender=com.baomidou.mybatisplus.extension.p6spy.StdoutLogger
   # 日志输出到文件
   #logfile=log.log
   # 使用日志系统记录 sql
   #appender=com.p6spy.engine.spy.appender.Slf4JLogger
   # 设置 p6spy driver 代理
   deregisterdrivers=true
   # 取消JDBC URL前缀
   useprefix=true
   # 配置记录 Log 例外,可去掉的结果集有error,info,batch,debug,statement,commit,rollback,result,resultset.
   excludecategories=info,debug,result,commit,resultset
   # 日期格式
   dateformat=yyyy-MM-dd HH:mm:ss
   # 实际驱动可多个
   #driverlist=org.h2.Driver
   # 是否开启慢SQL记录
   outagedetection=true
   # 慢SQL记录标准 2 秒
   outagedetectioninterval=2
   ```

4. 执行查询打印日志

   ```
   Consume Time：9 ms 2020-11-11 01:24:00
   Execute SQL：SELECT id,name,age,email,manager_id,create_time,update_time,version FROM user WHERE deleted=0
   ```

   ```
   Consume Time：15 ms 2020-11-11 01:25:29
   Execute SQL：UPDATE user SET age=26, update_time='2020-11-11T01:25:29.194' WHERE id=1088248166370832385 AND deleted=0
   ```


## 第6章 多租户

> 本章介绍MyBatis-Plus多租户的概念及实现。

### 6-1 多租户概念介绍

多租户技术是一种软件架构技术，是实现如何在多用户环境下（指面向企业的用户）公用相同的系统或程序，并且可确保各用户间数据的隔离性。简单来说，多租户是一种架构，目的是为了在多用户环境下使用同一套程序且保证用户间数据隔离。

多租户三种数据隔离方案：

1. 独立数据库

   一个租户一个数据库，数据隔离级别最高

   优点：为不同的租户提供了不同的数据库，有助于数据模型的扩展设计，满足不用租户的独特需求，如果出现故障恢复数据比较简单

   缺点：增加了数据库安装的数量，带来了维护成本和购置成本的增加

2. 共享数据库独立schema

   多个或者所有租户共享database，但是每个租户都有一个schema或user。比如Oracle

   优点：为安全性要求较高的租户提供了一定程度的逻辑数据隔离

   缺点：如果出现了数据故障，数据恢复比较困难

3. 共享数据库共享schema共享数据表

   租户共享同一个database，同一个schema，但是在表中增加租户ID字段。共享程度最高，隔离级别最低。简单来说就是每插入一条数据时都要有租户的标识

   优点：维护成本、购置成本最低

   缺点：隔离级别最低、安全性最低，数据恢复和备份困难，逐条恢复还原

### 6-2 多租户实现

> 实现「共享数据库共享schema共享数据表」方案的多租户数据隔离

添加配置

```java
@Bean public MybatisPlusInterceptor mybatisPlusInterceptor() {
  MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
  // 多租户
  mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
    @Override public String getTenantIdColumn() {
      return "manager_id";
    }
    @Override public Expression getTenantId() {
      return new LongValue(1088248166370832385L);
    }
    /**
     * 默认返回 false 表示所有表都需要拼多租户条件
     * @param tableName 表名
     * @return 是否过滤租户信息
     */
    @Override public boolean ignoreTable(String tableName) {
      return Objects.equals("role", tableName);
    }
  }));
  return mybatisPlusInterceptor;
}
```

测试

```java
userMapper.selectList(null);

User user = new User();
user.setAge(24);
user.setId(1088248166370832385L);
userMapper.updateById(user);

User user = new User();
user.setName("李国民");
user.setAge(27);
user.setEmail("lgm@baomidou.com");
userMapper.insert(user);
```

查询语句中增加了租户信息manager_id

```mysql
SELECT * FROM user WHERE manager_id = 1088248166370832385

UPDATE user SET age = ?, update_time = ? WHERE manager_id = 1088248166370832385 AND id = ?

INSERT INTO user (id, name, age, email, create_time, manager_id) VALUES (?, ?, ?, ?, ?, 1088248166370832385)
```

### 6-3 特定sql过滤

> 过滤特性的SQL语句，比如某些查询方法不需要租户信息

```java
@InterceptorIgnore(tenantLine = "true")
// @SqlParser(filter = true) // Deprecated
@Select("select * from user ${ew.customSqlSegment}")
List<User> mySelectList(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

@InterceptorIgnore(tenantLine = "false")
@Override
User selectById(Serializable id);
```


## 第7章 动态表

> 本章主要介绍MyBatis-Plus动态表的实现及注意事项。

### 7-1 动态表名的应用场景

多使用于数据字段相同的几张库表的情况，数据量过大进行分表存储：

- 日志表，命名规则为log_202011按月份分表
- 针对不同机构进行存储，一张机构一张表

规则均为前缀相同，在查询的时候才确定完整表名的情况

### 7-2 动态表名实现

添加动态表名的映射规则配置

```java
@Bean
public MybatisPlusInterceptor mybatisPlusInterceptor() {
  MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
  // 动态表名
  mybatisPlusInterceptor.addInnerInterceptor(new DynamicTableNameInnerInterceptor(new HashMap<String, TableNameHandler>(2) {{
    put("user", (sql, tableName) -> tableName + "_" + System.currentTimeMillis());
  }}));

  return mybatisPlusInterceptor;
}
```

测试

```java
userMapper.selectById(1087982257332887553L);
```

动态修改查询SQL的表名

```mysql
SELECT * FROM user_1605358385725 WHERE id=?
```

### 7-3 注意事项

如果[动态表名的映射规则配置]Map的value为null，则直接使用key进行SQL操作，即没有配置则不进行替换

多租户特定方法过滤`@InterceptorIgnore(tenantLine = "true")`也适用于动态表名替换


## 第8章 SQL注入器

> 本章主要介绍SQL注入器，和选装件的内容。

### 8-1 SQL注入器简介及自定义方法实现

> 使用SQL注入器可以自定义通用方法，如BaseMapper的insert、selectById等

实现步骤：

1. 创建定义方法的类
2. 创建注入器
3. 在Mapper中加入自定义方法

#### 一、创建定义方法的类

```java
public class DeleteAllMethod extends AbstractMethod {
  @Override public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    // 执行的SQL
    String sql = "delete from " + tableInfo.getTableName();
    // Mapper接口方法名
    String method = "deleteAll";
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
    return addDeleteMappedStatement(mapperClass, method, sqlSource);
  }
}
```

#### 二、创建注入器

```java
@Component
public class CustomizeSqlInjector extends DefaultSqlInjector {
  @Override public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
    // 必须要先获取默认的公用方法，不然默认的方法无法使用
    List<AbstractMethod> methodList = super.getMethodList(mapperClass);
    methodList.add(new DeleteAllMethod());
    return methodList;
  }
}
```

#### 三、在Mapper中加入自定义方法

##### 1. 创建公用Mapper继承BaseMapper

```java
public interface CommMapper<T> extends BaseMapper<T> {
  /**
   * 删除所有数据
   * @return 影响行数
   */
  int deleteAll();
}
```

##### 2. 实体用例Mapper继承公用Mapper

```java
@Mapper
public interface UserMapper extends CommMapper<User> {
}
```

#### 四、测试

```java
userMapper.deleteAll();
```

```mysql
delete from user
```

### 8-2 选装件InsertBatchSomeColumn

- InsertBatchSomeColumn

  批量新增数据,自选字段 insert(排除逻辑删除字段)

- LogicDeleteByIdWithFill

  根据 id 逻辑删除数据,并带字段填充功能

- AlwaysUpdateSomeColumnById

  根据 ID 更新固定的那几个字段(但是不包含逻辑删除)

一、注入器添加选装件方法

```java
/* com.tuyrk.mybatisplus.injector.CustomizeSqlInjector#getMethodList */
// 批量新增数据,自选字段 insert(排除逻辑删除字段)
methodList.add(new InsertBatchSomeColumn(tableFieldInfo ->
                                         !tableFieldInfo.isLogicDelete()
                                         && !Objects.equals("age", tableFieldInfo.getColumn())
                                        ));
// 根据 id 逻辑删除数据,并带字段填充功能
methodList.add(new LogicDeleteByIdWithFill());
// 根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
methodList.add(new AlwaysUpdateSomeColumnById(tableFieldInfo ->
                                              !Objects.equals("name", tableFieldInfo.getColumn())
                                             ));
```

二、通用Mapper添加接口方法

```java
/* com.tuyrk.mybatisplus.dao.CommMapper */
/**
 * 批量新增数据,自选字段 insert(排除逻辑删除字段)
 * @param entityList 批量插入的数据集合
 * @return 影响行数
 */
int insertBatchSomeColumn(List<T> entityList);
/**
 * 根据 id 逻辑删除数据,并带字段填充功能
 * @param entity 删除实体
 * @return 影响行数
 */
int deleteByIdWithFill(T entity);
/**
 * 根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
 * @param entity 更新实体
 * @return 影响行数
 */
int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
```



三、测试

```java
User user1 = new User();
user1.setName("李兴华");
user1.setAge(34);
user1.setManagerId(1088248166370832385L);
User user2 = new User();
user2.setName("杨红");
user2.setAge(29);
user2.setManagerId(1088248166370832385L);

userMapper.insertBatchSomeColumn(Arrays.asList(user1, user2));
```

```mysql
INSERT INTO user (id,name,age,email,manager_id,create_time,update_time,version)
VALUES (?,?,?,?,?,?,?,?) , (?,?,?,?,?,?,?,?)
```

```mysql
INSERT INTO user (id,name,email,manager_id,create_time,update_time,version)
VALUES (?,?,?,?,?,?,?) , (?,?,?,?,?,?,?)
```

### 8-3 选装件LogicDeleteByIdWithFill

三、实体类添加注解

```java
@TableField(fill = FieldFill.UPDATE)
private Integer age;
```

四、测试

```java
User user = new User();
user.setId(1327906230311628802L);
user.setAge(35);

userMapper.deleteByIdWithFill(user);
```

```mysql
UPDATE user SET age=?,update_time=?,deleted=1 WHERE id=? AND deleted=0
```

### 8-4 选装件alwaysUpdateSomeColumnById

三、测试

```java
User user = new User();
user.setId(1327906230311628802L);
user.setAge(40);
user.setName("李兴国");

userMapper.alwaysUpdateSomeColumnById(user);
```

```mysql
UPDATE user SET age=?, email=?, manager_id=?, create_time=?, update_time=?, version=? 
WHERE id=? AND deleted=0
```

