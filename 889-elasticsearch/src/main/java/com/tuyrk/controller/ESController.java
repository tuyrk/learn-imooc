package com.tuyrk.controller;

import com.google.gson.Gson;
import com.tuyrk.entity.Novel;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : TYK tuyk涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/8/31 22:55 星期六
 * @Update : 2019/8/31 22:55 by tuyk涂元坤
 */
@RestController
@RequestMapping("/novel")
public class ESController {

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/init")
    public ResponseEntity init() {
        Gson gson = new Gson();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<Novel> novelList = new ArrayList<Novel>() {{
                add(new Novel("菜谱", "王五", "5000", sdf.parse("2002-10-01")));
                add(new Novel("ElasticSearch入门", "瓦力", "3000", sdf.parse("2017-08-20")));
                add(new Novel("ElasticSearch精通", "很胖的瓦力", "3000", sdf.parse("2017-08-15")));
                add(new Novel("芭蕉扇", "牛魔王", "1000", sdf.parse("2000-10-01")));
                add(new Novel("Java入门", "李三", "2000", sdf.parse("2010-10-01")));
                add(new Novel("ElasticSearch大法好", "李四", "1000", sdf.parse("2017-08-01")));
                add(new Novel("剑谱", "赵六", "10000", sdf.parse("1997-01-01")));
                add(new Novel("移魂大法", "张三", "1000", sdf.parse("2000-10-01")));
                add(new Novel("太极拳", "张三丰", "1000", sdf.parse("1997-01-01")));
                add(new Novel("Python入门", "张四", "2000", sdf.parse("2005-10-01")));
                add(new Novel("七十二变", "孙悟空", "1000", sdf.parse("2000-10-01")));
            }};

            for (int i = 0; i < novelList.size(); i++) {
                Novel novel = novelList.get(i);
                Map novelMap = gson.fromJson(gson.toJson(novel), Map.class);
                IndexRequest novelRequest = new IndexRequest("novel").id(String.valueOf(i)).source(novelMap);
                IndexResponse novelResponse = this.client.index(novelRequest, RequestOptions.DEFAULT);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") String id) {
        DeleteRequest novelRequest = new DeleteRequest("novel", id);
        try {
            DeleteResponse novelResponse = this.client.delete(novelRequest, RequestOptions.DEFAULT);
            System.out.println(novelResponse);
            return new ResponseEntity(novelResponse.getResult().toString(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
}