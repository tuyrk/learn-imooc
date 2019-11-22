package com.tuyrk.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.tuyrk.common.ErrorCode;
import com.tuyrk.common.MessageEntity;
import com.tuyrk.common.Response;
import com.tuyrk.producer.SimpleProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author tuyrk
 */
@Slf4j
@RestController
@RequestMapping("kafka")
public class ProducerController {
    private final SimpleProducer simpleProducer;

    @Value("${kafka.topic.default}")
    private String topic;

    public ProducerController(SimpleProducer simpleProducer) {
        this.simpleProducer = simpleProducer;
    }

    @GetMapping(value = "hello", produces = {"application/json"})
    public Response sendKafka() {
        return new Response(ErrorCode.SUCCESS, "OK");
    }

    @PostMapping(value = "send", produces = {"application/json"})
    public Response sendKafka(@RequestBody MessageEntity message) {
        try {
            log.info("kafka的消息={}", message);
            simpleProducer.send(topic, "key", message);
            log.info("发送Kafka成功。");
            return new Response(ErrorCode.SUCCESS, "发送Kafka成功");
        } catch (Exception e) {
            log.error("发送Kafka失败。", e);
            return new Response(ErrorCode.EXCEPTION, "发送Kafka失败");
        }
    }
}
