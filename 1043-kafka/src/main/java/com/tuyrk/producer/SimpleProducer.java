package com.tuyrk.producer;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author tuyrk
 */
@Slf4j
@Component
public class SimpleProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(Object message) {
        kafkaTemplate.sendDefault(new Gson().toJson(message));
    }

    public void send(String topic, Object message) {
        kafkaTemplate.send(topic, new Gson().toJson(message));
    }

    public void send(String topic, String key, Object message) {

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, new Gson().toJson(message));
        long startTime = System.currentTimeMillis();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                               @Override
                               public void onFailure(Throwable throwable) {
                                   throwable.printStackTrace();
                               }

                               @Override
                               public void onSuccess(SendResult<String, String> result) {
                                   if (ObjectUtils.isEmpty(result)) {
                                       return;
                                   }
                                   RecordMetadata metadata = result.getRecordMetadata();
                                   if (ObjectUtils.isEmpty(metadata)) {
                                       return;
                                   }

                                   long elapsedTime = System.currentTimeMillis() - startTime;
                                   log.info("生产者发送消息 = message(key = {}, message = {}).sent to partition({}) with offset({}) in {} ms",
                                           key, message, metadata.partition(), metadata.offset(), elapsedTime);
                               }
                           }
        );
    }
}
