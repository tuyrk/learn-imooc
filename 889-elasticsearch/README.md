# ElasticSearch入门

> https://www.imooc.com/learn/889
>
> 简介：ElasticSearch是一个分布式、可扩展、实时的搜索与数据分析引擎，它能从项目一开始就赋予你的数据以搜索、分析和探索的能力。通过本课程的学习，你可以了解到，ElasticSearch在互联网行业里的火热程度，也可以了解到它的实际应用场景。本课程会通过理论与实践相结合的方式，带领你一步一步走进ElasticSearch的世界，使你轻松掌握ElasticSearch的基本概念，学习ElasticSearch的服务搭建，了解ElasticSearch的常用技巧，并通过案例项目让你拥有实际的应用能力。 老师实战课程已经上线：http://coding.imooc.com/class/167.html ElasticSearch+MySQL+Kafka强力组合，更有ES结合百度地图，Nginx等高级应用。

## 第1章 课程介绍

> 介绍Elastic Search的由来，应用场景，课程需要的前置知识，环境要求，介绍课程安排

### [ 1-1 课程介绍](https://www.imooc.com/video/15762)

什么是ElasticSearch？

- 基于Apache Lucene构建的开源搜索引擎

  Lucene相对复杂，需要深厚的搜索理论知识，难以集成

- 采用Java编写，提供简单易用的RESTFul API

- 轻松的横向扩展，可支持PB级的结构化或非结构化 数据处理



应用场景：

- 海量数据分析引擎
- 站内搜索引擎
- 数据仓库

一线公司实际应用场景：

- 英国卫报-实时分析公众对文章的回应
- 维基百科、Github-站内实时搜索
- 百度-实时日志监控平台
- 阿里巴巴、Google、京东等等



前置知识

- 熟悉用Maven构建项目
- 了解Spring Boot的基本使用

环境要求：

- IDE工具：Intellij IDEA、Eclipse等常用IDE即可
- Java：JDK1.8
- 其他依赖：Maven、NodeJS(6.0以上)



课程简介：

- 安装=》基础概念=》基本用法=》高级查询=》实战演练=》课程总结

## 第2章 安装

> 先从单个节点的安装讲起，然后把插件安装流程走一遍，并说一下插件的主要作用，最后把分布式安装简单的介绍一下

### [ 2-1 ES版本历史和选择](https://www.imooc.com/video/15763)

- 版本历史 1.x -> 2.x -> 5.x
- 版本选择 5.x以上



安装

- 单实例安装
- 实用插件Head安装
- 分布式安装

### [ 2-2 单实例安装](https://www.imooc.com/video/15764)

> [ElasticSearch官网首页](https://www.elastic.co/cn/) 

```shell
# 下载
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.3.1-linux-x86_64.tar.gz
# 解压
tar -zvxf elasticsearch-7.3.1-linux-x86_64.tar.gz
# 进入HOME目录
cd elasticsearch-7.3.1-linux-x86_64
# 运行ElasticSearch程序
sh ./bin/elasticsearch
# 获取ElasticSearch服务信息
curl http://localhost:9200/
```

### [ 2-3 插件安装](https://www.imooc.com/video/15765)

> [Github主页](https://github.com/mobz/elasticsearch-head)

```shell
# 下载
wget https://github.com/mobz/elasticsearch-head/archive/master.zip
# 解压
unzip master.zip
# 进入HOME目录
cd elasticsearch-head-master
# 编译源码
npm install
# 运行程序
npm run start
# 访问服务地址
open http://localhost:9100/
```



- 修改ElasticSearch配置

  ```shell
  vim config/elasticsearch.yml
  ```

  ```yaml
  http.cors.enabled: true
  http.cors.allow-origin: "*"
  ```

  重启ElasticSearch、Head

### [ 2-4 分布式安装](https://www.imooc.com/video/15766)

修改ElasticSearch配置

- 主节点：

  ```shell
  vim config/elasticsearch.yml
  # -import ~~master~~
  # 查看进程
  ps -ef | grep `pwd`
  # 后台启动ElasticSearch
  ./bin/elasticsearch -d
  ```

  ```yaml
  # ~~master~~
  cluster.name: wali
  node.name: master
  node.master: true
  
  network.host: 127.0.0.1
  ```

- 从节点

  ```shell
  # 创建子节点目录
  mkdir es_slave
  # 拷贝ElasticSearch
  cp elasticsearch-7.3.1-linux-x86_64.tar.gz es_slave
  # 解压
  tar -zvxf elasticsearch-7.3.1-linux-x86_64.tar.gz
  # 复制两份子节点程序
  cp -r elasticsearch-7.3.1-linux-x86_64 es_slave1
  cp -r elasticsearch-7.3.1-linux-x86_64 es_slave2
  
  cd es_slave1
  vi config/elasticsearch.yml
  # -import ~~es_slave1~~
  # 后台启动ElasticSearch
  ./bin/elasticsearch -d
  
  cd es_slave2
  vi config/elasticsearch.yml
  # -import ~~es_slave2~~
  # 后台启动ElasticSearch
  ./bin/elasticsearch -d
  ```

  ```yaml
  # ~~es_slave1~~
  cluster.name: wali
  node.name: slave1
  
  network.host: 127.0.0.1
  http.port: 8001
  ```

  ```yaml
  # ~~es_slave2~~
  cluster.name: wali
  node.name: slave2
  
  network.host: 127.0.0.1
  http.port: 8002
  ```


## 第3章 基础概念

> 结合Head插件，简单的讲解集群、节点的概念，重点讲解索引及其重要性，分片和备份属于索引的附属概念，一笔带过，类型、文档简单讲解下，并将索引、类型、文档三者的关系讲解清楚

### [ 3-1 基础概念](https://www.imooc.com/video/15767)

集群和节点：

- 节点1+节点2+…+…+节点N ==》集群

基础概念

- 索引：含有相同属性的文档集合
- 类型：索引可以定义一个或多个类型，文档必须属于一个类型
- 文档：文档是可以被索引的基本数据单位

- 分片：每个索引都有多个分片，每个分片是一个Lucene索引
- 备份：拷贝一份分片就可以完成了分片的备份

## 第4章 基本用法

> 本章节主要带领学生一起学习ElasticSearch的基本使用方法，并了解常用的REST API，讲解过程中要将命令行与head插件相结合以演示相应操作。

### [ 4-1 索引创建](https://www.imooc.com/video/15768)

> - 非结构化创建
> - 结构化创建

RESTFul API：

- API基本格式

  ```
  http://<ip>:<port>/<索引>/<类型>/<文档ID>
  ```

- 常用HTTP动词：GET/PUT/POST/DELETE



Head插件创建索引（非结构化）：

1. 访问Head服务地址：http://127.0.0.1:9100

2. 索引-新建索引

3. 概览

   粗线框为主分片，旁边细线框为分片备份

Head插件创建索引（结构化）：

1. 复合查询-查询

2. 参数信息

   ```
   http://127.0.0.1:9200/
   book/novel/_mappings POST
   ```

   ```json
   {
       "novel": {
           "properties": {
               "title":{
                   "type": "text"
               }
           }
       }
   }
   ```

3. 验证JSON-提交请求

4. 概览-刷新-信息-索引信息-mappings



Postman创建索引

1. ```
   PUT http://127.0.0.1:9200/people
   ```

2. ```
   {
   	"settings": {
   		"number_of_shards": 3,
   		"number_of_replicas": 1
   	},
   	"mappings": {
   		"man": {
   			"properties": {
   				"name": {"type": "text"},
   				"country": {"type": "keyword"},
   				"age": {
   					"type": "integer"
   				},
   				"date": {
   					"type": "date",
   					"format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
   				}
   			}
   		},
   		"woman": {
   			
   		}
   	}
   }
   ```

### [ 4-2 插入](https://www.imooc.com/video/15769)

> - 指定文档ID插入
> - 自动产生文档ID插入

文档ID：唯一索引值，指向文档数据



指定文档ID插入：

1. ```
   PUT http://127.0.0.1:9200/people/man/1
   ```

2. ```json
   {
       "name": "瓦力",
    "country": "China",
       "age": 30,
       "date": "1987-03-07"
   }
   ```

3. 概览-docs：索引下所有文档的数量值

4. 数据浏览

自动产生文档ID插入：

1. ```
   POST http://127.0.0.1:9200/people/man
   
   ```

2. ```json
   {
       "name": "瓦力",
       "country": "China",
       "age": 30,
       "date": "1987-03-07"
   }
   
   ```

### [ 4-3 修改](https://www.imooc.com/video/15770)

> - 直接修改文档
> - 脚本修改文档

直接修改文档

1. ```
   POST http://127.0.0.1:9200/people/man/1/_update
   
   ```

2. ```json
   {
       "doc": {
           "name": "谁是瓦力"
       }
   }
   
   ```

脚本修改文档

1. ```
   POST http://127.0.0.1:9200/people/man/1/_update
   
   ```

2. ```json
   {
       "script": {
           "lang": "painless",
           "inline": "ctx._source.age += 10"
       }
   }
   
   ```

   ```json
   {
       "script": {
           "lang": "painless",
           "inline": "ctx._source.age += params.age",
           "params": {
               "age": 100
           }
       }
   }
   
   ```

### [ 4-4 删除](https://www.imooc.com/video/15771)

> - 删除文档
> - 删除索引

删除文档

```
DELETE http://127.0.0.1:9200/people/man/1

```

删除索引

- Head插件删除

  概览-动作-删除-输入删除-确定

- Postman删除

  ```
  DELETE http://127.0.0.1:9200/people
  
  ```

删除索引是一个非常危险的操作。

### [ 4-5 查询](https://www.imooc.com/video/15772)

> - 简单查询
> - 条件查询
> - 聚合查询

事先创建索引book：

```
PUT http://127.0.0.1:9200/book

```

```json
{
	"settings": {
		"number_of_shards": 3,
		"number_of_replicas": 1
	},
	"mappings": {
		"novel": {
    		"properties": {
    			"word_count": {
    				"type": "integer"
				},
				"author": {
                    "type": "keyword"
                },
				"title": {
                    "type": "text"
                },
				"publish_date": {
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis",
                    "type": "date"
                }
			}
		}
	}
}

```

插入数据：

```
PUT http://127.0.0.1:9200/book/novel/1

```

```json
{
    "word_count": "5000",
    "author": "王五",
    "title": "菜谱",
    "publish_date": "2002-10-01"
}

```

book novel 5  1 王五 菜谱                    5000  2002-10-01
book novel 8  1 瓦力 ElasticSearch入门       3000  2017-08-20
book novel 9  1 很胖的瓦力 ElasticSearch精通 3000  2017-08-15
book novel 10 1 牛魔王 芭蕉扇                1000  2000-10-01
book novel 2  1 李三 Java入门                2000  2010-10-01
book novel 4  1 李四 ElasticSearch大法好     1000  2017-08-01
book novel 6  1 赵六 剑谱                    10000 1997-01-01
book novel 1  1 张三 移魂大法                1000  2000-10-01
book novel 7  1 张三丰 太极拳                1000  1997-01-01
book novel 3  1 张四 Python入门              2000  2005-10-01
book novel 11 1 孙悟空 七十二变              1000  2000-10-01



简单查询：

```
GET http://127.0.0.1:9200/book/novel/1

```

条件查询：

- 查询所有

  ```
  POST http://127.0.0.1:9200/book/_search
  
  ```

  ```json
  {
      "query": {
          "match_all": {}
      },
      "form": 1,
      "size": 1
  }
  
  ```

  关键词查询

  ```json
  {
      "query": {
          "match": {
              "title": "ElasticSearch"
          }
      },
      "sort": [
          {"publish_date": {"order": "desc"}}
      ]
  }
  
  ```

- 条件查询

  ```
  POST http://127.0.0.1:9200/book/_search
  
  ```

  ```json
  {
      "aggs": {
          "group_by_word_count": {
              "terms": {
                  "field": "word_count"
              }
          },
          "group_by_publish_date": {
              "terms": {
                  "field": "publish_date"
              }
          }
      }
  }
  
  ```

- 聚合查询

  ```
  POST http://127.0.0.1:9200/book/_search
  
  ```

  ```json
  {
      "aggs": {
          "grades_word_count": {
              "stats": {
                  "field": word_count
              }
          }
      }
  }
  
  ```

  ```json
  {
      "aggs": {
          "grades_word_count": {
              "min": {
                  "field": word_count
              }
          }
      }
  }
  
  ```

  

## 第5章 高级查询

> 将query与filter分开讲解，并把相应的关键词列举一些，结合实例操作，最后将query与filter组合起来，讲解实例

### [ 5-1 query](https://www.imooc.com/video/15759)

高级查询：

- 子条件查询：特定字段查询所指特定值
- 复合条件查询：以一定的逻辑组合子条件查询

子条件查询

- Query context
- Filter context



Query Context

> 在查询过程中，除了判断文档是否满足查询条件外，ES还会计算一个_score来标识匹配的程度，旨在判断目标文档和查询条件匹配的有多好。

常用查询：

- 全文本查询：针对文本类型数据
- 字段级别查询：针对结构化数据，如数字、日期等



```
POST http://127.0.0.1:9200/book/_search

```

- 模糊匹配(匹配：ElasticSearch+入门)

  ```json
  {
      "query": {
          "match": {
              "title": "ElasticSearch入门"
          }
      }
  }
  
  ```

- 习语匹配(匹配：ElasticSearch入门)

  ```json
  {
      "query": {
          "match_phrase": {
              "title": "ElasticSearch入门"
          }
      }
  }
  
  ```

- 多字段模糊匹配

  ```json
  {
      "query": {
          "multi_match": {
              "query": "瓦力",
              "fields": ["author", "title"]
          }
      }
  }
  
  ```

- 语法匹配

  ```json
  {
      "query": {
          "query_string": {
              "query": "ElasticSearch AND 大法"
          }
      }
  }
  
  ```

  ```json
  {
      "query": {
          "query_string": {
              "query": "(ElasticSearch AND 大法) OR Python"
          }
      }
  }
  
  ```

- 多字段语法匹配

  ```json
  {
      "query": {
          "query_string": {
              "query": "瓦力 OR ElasticSearch",
              "fields": ["title", "author"]
          }
      }
  }
  
  ```

- 字段匹配

  ```json
  {
      "query": {
          "term": {
              "word_count": 1000
          }
      }
  }
  
  ```

  ```json
  {
      "query": {
          "term": {
              "author": "瓦力"
          }
      }
  }
  
  ```

- 范围匹配（数字、日期）

  ```json
  {
      "query": {
          "range": {
              "word_count": {
              	"gte": 1000,
                  "lte": 2000
              }
          }
      }
  }
  
  ```

  ```json
  {
      "query": {
          "range": {
              "publish_date": {
              	"gt": "2019-08-29",
                  "lte": "now"
              }
          }
      }
  }
  
  ```

  now：代表当前时间

### [ 5-2 filter](https://www.imooc.com/video/15760)

Filter Context

> 在查询过程中，只判断该文档是否满足条件，只有Yes或者No



```
POST http://127.0.0.1:9200/book/_search

```

```json
{
    "query": {
        "bool": {
            "filter": {
            	"term": {
                	"word_count": 1000
                }
            }
        }
    }
}

```

filter做数据过滤的，并且会对查询结果进行缓存，所以filter比query要快一些。

### [ 5-3 复合查询](https://www.imooc.com/video/15761)

常用查询：

- 固定分数查询
- 布尔查询



```
POST http://127.0.0.1:9200/book/_search

```

```json
{
    "query": {
        "match": {
            "title": "ElasticSearch"
        }
    }
}

```

```json
{
    "query": {
        "constant_score": {
            "filter": {
            	"match": {
                	"title": "ElasticSearch"
                }
            },
            "boost": 2
        }
    }
}

```

```json
{
    "query": {
        "bool": {
            "should": [
                {
                    "match": {
                        "author": "瓦力"
                    }
                },
                {
                    "match": {
                        "title": "ElasticSearch"
                    }
                }
            ]
        }
    }
}

```

```json
{
    "query": {
        "bool": {
            "must": [
                {
                    "match": {
                        "author": "瓦力"
                    }
                },
                {
                    "match": {
                        "title": "ElasticSearch"
                    }
                }
            ],
            "filter": [
                {
                    "term": {
                        "word_count": 1000
                    }
                }
            ]
        }
    }
}

```

```json
{
    "query": {
        "bool": {
            "must_not": {
                "term": {
                    "author": "瓦力"
                }
            }
        }
    }
}

```



固定分数查询不支持match，只支持filter

## 第6章 Spring Boot集成ES

> 直接演示SpringBoot集成ES，做接口开发
>
> 以下使用ElasticSearch7.3.1做演示

### [ 6-1 SpringBoot集成ElasticSearch](https://www.imooc.com/video/15758)

实战演练：

- Spring Boot集成ES
- 图书信息管理接口开发

Pom.xml

```xml
<dependency>
  <groupId>org.elasticsearch</groupId>
  <artifactId>elasticsearch</artifactId>
  <version>${elasticsearch.version}</version>
</dependency>
<dependency>
  <groupId>org.elasticsearch.client</groupId>
  <artifactId>elasticsearch-rest-high-level-client</artifactId>
  <version>${elasticsearch.version}</version>
</dependency>
<dependency>
  <groupId>org.elasticsearch.client</groupId>
  <artifactId>elasticsearch-rest-client</artifactId>
  <version>${elasticsearch.version}</version>
</dependency>
<dependency>
  <groupId>org.elasticsearch.client</groupId>
  <artifactId>elasticsearch-rest-client-sniffer</artifactId>
  <version>${elasticsearch.version}</version>
</dependency>

```

配置Spring Bean

```java
@Configuration
public class ESConfig {
    @Bean
    public RestHighLevelClient client() throws UnknownHostException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        return client;
    }
}

```

ESController

```java
@RestController
@RequestMapping("/novel")
public class ESController {
  @Autowired
  private RestHighLevelClient client;
  // todo 
}

```

运行

```shell
mvn spring-boot:run

```

### [ 6-2 查询接口开发](https://www.imooc.com/video/15773)

接口开发：

- 新增图书信息功能开发
- 修改图书信息功能开发
- 删除功能开发
- 综合查询接口开发

```java
@GetMapping("/{id}")
public ResponseEntity get(@PathVariable(name = "id") String id) {
  if (id.isEmpty()) {
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }
  GetRequest novelRequest = new GetRequest("novel", id);
  try {
    GetResponse novelResponse = this.client.get(novelRequest, RequestOptions.DEFAULT);
    if (novelResponse.isExists()) {
      System.out.println(novelResponse);
      return new ResponseEntity(novelResponse.getSource(), HttpStatus.OK);
    }
  } catch (IOException e) {
    e.printStackTrace();
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
  return new ResponseEntity(HttpStatus.NOT_FOUND);
}

```

运行：

```shell
mvn spring-boot:run
GET http://127.0.0.1:8080/novel/1

```

### [ 6-3 增加接口开发](https://www.imooc.com/video/15775)

```java
@PostMapping("")
public ResponseEntity add(@RequestBody Novel novel) {
  try {
    Gson gson = new Gson();
    Map novelMap = gson.fromJson(gson.toJson(novel), Map.class);
    IndexRequest novelRequest = new IndexRequest("novel").id(UUID.randomUUID().toString()).source(novelMap);
    IndexResponse novelResponse = this.client.index(novelRequest, RequestOptions.DEFAULT);
    System.out.println(novelResponse);
    return new ResponseEntity(novelResponse.getId(), HttpStatus.OK);
  } catch (IOException e) {
    e.printStackTrace();
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

```

```java
@Data
public class Novel {
    private String title;
    private String author;
    @JsonProperty(value = "word_count")
    private String wordCount;
    @JsonProperty(value = "publish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
}

```

运行

```
POST http://127.0.0.1:8080/novel

```

```json
{
	"title": "大话西游",
	"author": "刘镇伟",
	"word_count": "1008",
	"publish_date": "2019-09-08"
}

```

### [ 6-4 删除接口开发](https://www.imooc.com/video/15774)

```java
@DeleteMapping("/{id}")
public ResponseEntity delete(@PathVariable(name = "id") String id) {
  DeleteRequest novelRequest = new DeleteRequest("novel", id);
  try {
    DeleteResponse novelResponse = this.client.delete(novelRequest, RequestOptions.DEFAULT);
    System.out.println(novelResponse);
    return new ResponseEntity(novelResponse.getResult().toString(),HttpStatus.OK);
  } catch (IOException e) {
    e.printStackTrace();
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

```

运行

```
DELETE http://127.0.0.1:8080/novel/1

```

### [ 6-5 更新接口开发](https://www.imooc.com/video/15776)

```java
@PutMapping("/{id}")
public ResponseEntity update(@PathVariable(name = "id") String id, @RequestBody Novel novel) {
  try {
    Gson gson = new Gson();
    Map map = gson.fromJson(gson.toJson(novel), Map.class);
    UpdateRequest novelRequest = new UpdateRequest("novel", id).doc(map);
    UpdateResponse novelResponse = this.client.update(novelRequest, RequestOptions.DEFAULT);
    System.out.println(novelResponse);
    return new ResponseEntity(novelResponse.getResult().toString(), HttpStatus.OK);
  } catch (IOException e) {
    e.printStackTrace();
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

```

运行

```
PUT http://127.0.0.1:8080/novel/1

```

```json
{
	"title": "大话西游",
	"author": "刘镇伟",
	"word_count": "1008",
	"publish_date": "2019-09-08"
}

```

### [ 6-6 复合查询接口开发](https://www.imooc.com/video/15777)

```java
@GetMapping("")
public ResponseEntity query(@RequestParam(value = "author", required = false) String author,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "gt_word_count", defaultValue = "0") Integer gtWordCount,
                            @RequestParam(value = "lt_word_count", required = false) Integer ltWordCount) {
    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    if (author != null) {
        boolQueryBuilder.must(QueryBuilders.matchQuery("author", author));
    }
    if (title != null) {
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", title));
    }
    RangeQueryBuilder wordCountRangeQuery = QueryBuilders.rangeQuery("word_count")
            .from(gtWordCount);
    if (ltWordCount != null && 0 < ltWordCount) {
        wordCountRangeQuery.to(ltWordCount);
    }
    boolQueryBuilder.filter(wordCountRangeQuery);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
            .query(boolQueryBuilder)
            .from(0)
            .size(10);
    SearchRequest novelSearchRequest = new SearchRequest("novel")
            .source(searchSourceBuilder)
            .searchType(SearchType.DFS_QUERY_THEN_FETCH);
    System.out.println(novelSearchRequest.source());
    try {
        SearchResponse novelSearchResponse = this.client.search(novelSearchRequest, RequestOptions.DEFAULT);
        System.out.println(novelSearchResponse);
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        novelSearchResponse.getHits().forEach(e -> {
            result.add(e.getSourceAsMap());
        });
        return new ResponseEntity(result, HttpStatus.OK);
    } catch (IOException e) {
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

```


## 第7章 课程总结

> 课程总结

### [ 7-1 课程总结](https://www.imooc.com/video/15757)

1. ES简介

   ES使用场景例子

   ES的火热程度-重要性

2. 安装

   ES使用插件HEAD

   ES分布式安装，实现了ES的横向扩容

3. 基础概念

4. 基本用法

   增删查改功能

5. 高级查询

6. 实战演练

   将ES集成到SpringBoot