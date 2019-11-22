package com.tuyrk.consumer;

import com.google.gson.Gson;
import com.tuyrk.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author tuyrk
 */
@Slf4j
@Component
public class SimpleConsumer {
    @KafkaListener(topics = "${kafka.topic.default}")
    public void receive(String message) {
        Gson gson = new Gson();
        MessageEntity messageEntity = gson.fromJson(message, MessageEntity.class);
        log.info("消费者接收消息 = {}", messageEntity);
    }
}
