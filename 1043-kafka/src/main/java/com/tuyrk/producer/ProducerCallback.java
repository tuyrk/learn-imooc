package com.tuyrk.producer;

import com.tuyrk.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author tuyrk
 */
@Slf4j
public class ProducerCallback implements ListenableFutureCallback<SendResult<String, String>> {
    private final long startTime;
    private final String key;
    private final String message;


    public ProducerCallback(long startTime, String key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(@Nullable SendResult<String, String> result) {
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
