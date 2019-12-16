# Kafka流处理平台

> https://www.imooc.com/learn/1043
>
> 简介：Kafka是目前主流的流处理平台，同时作为消息队列家族的一员，其高吞吐性作为很多场景下的主流选择。同时作为流处理平台，在大数据开发中，作为黏合剂串联各个系统。本课分为四大部分：第一部分介绍Kafka相关概念和基本信息；第二部分则从Kafka的结构出发，代领大家了解Kafka的设计与思想；第三部分则从实战出发，结合Kafka的应用场景，一步一步结合代码掌握Kafka的用法。最后，我们学习和探究Kafka的高级特性，融会贯通并争取可以在日常其他开发工作中使用Kafka给我们带来的技术能量。课程配套代码参考：https://github.com/AnAngryMan/KafkaSimpleExample

第1章 课程介绍
---
> 课程介绍

### 1-1 课程介绍

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g92kvgdkiuj30vi0q2795.jpg" alt="Kafka数据流" style="zoom:30%;" />

课程安排：

- Kafka概念解析
- Kafka结构设计
- Kafka场景与应用
- Kafka高级特性

第2章 什么是Kafka
---
> 本章节讲解Kafka的由来，Kafka的定位和基本概念。

### 2-1 什么是kafka

LinkedIn开源

- 分布式数据同步系统 Databus
- 高性能计算引擎 Cubert
- Java异步处理框架 ParSeq
- Kafka流处理平台

Kafka前世今生

- LinkedIn开发
- 2011年初开源，加入Apache基金会
- 2012年从Apache Incubator毕业
- Apache顶级开源项目



streaming platform has three key capabilities：

- Publish and subscribe to streams of records, similar to a message queue or enterprise messaging system.
- Store streams of records in a fault-tolerant durable way.
- Process streams of records as they occur.

Kafka is generally used for two broad classes of applications：

- Building real-time streaming data pipelines that reliably get data between systems or applications.
- Building real-time streaming applications that transform or react to the streams of data.

第3章 Kafka的设计和结构
---
> 本章节讲解Kafka的部署结构、内部设计结构和设计，让你了解Kafka高效而可靠的原因

### 3-1 kafka基本概念

> 物理概念
>
> 逻辑概念

Producer：消息和数据的生产者，向Kafka的一个topic发布消息的进程/代码/服务

Consumer：消息和数据的消费者，订阅数据（Topic）并且处理其发布的消息的进程/代码/服务

Consumer Group：逻辑概念。对于同一个Topic，会广播给不同的group，一个group中，只有一个consumer可以消费该消息。

Broker：物理概念。Kafka集群中的每个Kafka节点

Topic：逻辑概念。Kafka消息的类别，对数据进行区分、隔离

Partition：物理概念。Kafka下数据存储的基本单元。一个Topic数据，会被分散存储到多个Partition，每一个Partition是有序的。

Replication：同一个Partition可能会有多个Replica，多个Replica之间的数据是一样的。

Replication Leader：一个Partition的多个Replica上，需要一个Leader负责该Partition上与Producer和Consumer交互

ReplicaManager：负责管理当前broker所有分区和副本的信息，处理KafkaController发起的一些请求，副本状态的切换、添加/读取消息等

### 3-2 kafka概念延伸

Partition：

- 每一个Topic被切分为多个Partitions
- 消费者数目小于或等于Partition的数目
- Broker Group中的每一个Broker保存Topic的一个或多个Partitions
- Consumer Group中的仅有一个Consumer读取Topic的一个或多个Partitions，并且是唯一的Consumer

Replication：

- 当集群中有Broker挂掉的情况，系统可以主动地使Replicas提供服务
- 系统默认设置每一个Topic的Replication的系数为1，可以在创建Topic时单独设置。

Replication特点：

- Replication的基本单位是Topic的Partition
- 所有的读和写都从Leader进，Followers只是作为备份
- Followers必须能够及时复制Leader的数据
- 增加容错性与可扩展性

### 3-3 kafka基本结构

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g92ml4h2z8j30sm0ni0z0.jpg" alt="Kafka基本结构" style="zoom:35%;" />

- Producer API
- Consumer API
- Streams API
- Connectors API

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g92mn3gn8uj31ka0r44oe.jpg" alt="Kafka基本结构" style="zoom:30%;" />

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g92mo6igjyj31r805gwic.jpg" alt="Kafka消息结构" style="zoom:50%;" />

- Offset：

- Length：

- CRC32：

- Magic：

- attributes：

- Timestamp：

- Key Length：

- Key：

- Value Length：

- Value：

### 3-4 kafka特点

> 分布式

- 多分区
- 多副本
- 多订阅者
- 基于Zookeeper调度

> 高性能

- 高吞吐量
- 低延迟
- 高并发
- 时间复杂度为O(1)

> 持久性与扩展性

- 数据可持久化
- 容错性
- 支持在线水平扩展
- 消息自动平衡

第4章 Kafka的应用场景和实战
---
> 本章节带领大家了解Kafka的一些应用场景，然后从简单案例和复杂案例两个案例学习掌握Kafka的用法。

### 4-1 kafka应用场景

- 消息队列
- 行为跟踪
- 元信息监控
- 日志收集
- 流处理
- 事件源
- 持久性日志（commit log）

### 4-2 kafka简单案例

- 环境启动
- 简单生产者
- 简单消费者

下载与安装

- Zookeeper下载

  https://zookeeper.apache.org/releases.html#download

- Kafka下载

  http://kafka.apache.org/downloads

- 安装：解压，配置环境变量

- Mac便捷安装：`brew install kafka`

```shell
# bash1 启动zookeeper
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
# bash2 启动Kafka
kafka-server-start /usr/local/etc/kafka/server.properties
```



```shell
# bash3 创建topic
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic tuyrk-kafka-topic
# 查看Kafka topic
kafka-topics --list --zookeeper localhost:2181
# 创建生产者
kafka-console-producer --broker-list localhost:9092 --topic tuyrk-kafkatopic
```

```shell
# bash4 创建消费者
kafka-console-consumer --bootstrap-server localhost:9092 --topic tuyrk-kafka-topic --from-beginnin
```

- `from-beginnin`：从开始重新接收消息

### 4-3 kafka代码案例

- 工程结构
- 基础代码

教师源码：https://github.com/AnAngryMan/KafkaSimpleExample

学习源码（springboot+kafka）：https://github.com/tuyrk/learn-imooc

第5章 Kafka高级特性
---
> 本章节学习Kafka的消息事务和零拷贝，前者学习Kafka如何保证数据一致性，后者学习Kafka消息传递的高效性。

### 5-1 kafka高级特性之消息事务

为什么要支持事务？

- 满足“读取-处理-写入”模式
- 流处理需求的不断增强
- 不准确的数据处理的容忍度降低

数据传输的事务定义

- 最多一次：消息不会被重复发送，最多被传输一次，但也有可能一次不传输
- 最少一次：消息不会被漏发送，最少被传输一次，但也有可能被重复传输
- 精确的一次（Exactly once）：不会漏传输也不会重复传输，每个消息都被传输一次且仅仅传输一次。这是大家所期望的情况。

事务保证

- 内部重试问题：Procedure幂等处理

- 多分区原子写入

  <img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g978niwdtvj31ac0gwgos.jpg" alt="多分区原子写入" style="zoom:30%;" />

事务保证-避免僵尸实例

- 每个事务Produce分配一个transaction.id，在进程重新启动时能够识别相同的Producer实例
- Kafka增加了一个与transaction.id相关的epoch，存储每个transaction.id内部元数据
- 一旦epoch被触发，任何具有相同的transaction.id和更旧的epoch的Producer被视为僵尸，Kafka会拒绝来自这些Producer的后续事务性写入

### 5-2 kafka高级特性之零拷贝

零拷贝简介：

- 网络传输持久性日志块
- Java NIO `channel.transforTo()`方法
- Linux sendfile系统调用



文件传输到网络的公共数据路径

- 操作系统将数据从磁盘读入到内核空间的页缓存
- 应用程序将数据从内核空间读入到用户空间缓存中
- 应用程序将数据写回到内核空间的socket缓存中
- 操作系统将数据从socket缓冲区复制到网卡缓冲区，以便将数据经网络发出

零拷贝过程

- 操作系统将数据从磁盘读入到内核空间的页缓存
- 将数据的位置和长度的信息的描述符增加至内核空间（socket缓冲区）
- 操作系统将数据从内核拷贝到网卡缓冲区，以便将数据经网络发出



文件传输到网络的公共数据路径演变

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g979vkbrulj31u00lutb5.jpg" alt="文件传输到网络的公共数据路径演变" style="zoom:50%;" />

第6章 课程总结
---
> 课程总结

### 6-1 课程总结

<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g979ws24pej31ke0u0nka.jpg" alt="Kafka课程总结" style="zoom:25%;" />

1. 基础概念与结构
2. 三维特点
3. 应用场景
4. 应用案例
5. 高级特性