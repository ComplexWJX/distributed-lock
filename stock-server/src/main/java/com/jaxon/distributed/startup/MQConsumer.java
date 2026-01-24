package com.jaxon.distributed.startup;

import com.jaxon.distributed.transaction.RocketMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author koala
 * @version 1.0
 * @date 2025/12/01/14:40
 * @description
 */
@Service
@Slf4j
public class MQConsumer {
    public MQConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("myGroup");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setInstanceName("mq-consumer");
        try {
            consumer.registerMessageListener(new RocketMessageListener());
            consumer.subscribe("common-order", "tag1");
            consumer.start();
        } catch (MQClientException e) {
            log.error("init mq consumer error:{}", e.getMessage(), e);
        }
    }
}
