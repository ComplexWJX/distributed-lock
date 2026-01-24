package com.jaxon.distributed.sync;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author koala
 * @version 1.0
 * @date 2025/11/28/17:16
 * @description
 */
@Service
@Slf4j
public class MqService {
    private final TransactionMQProducer transactionMQProducer;

    private final DefaultMQProducer defaultMQProducer;

    public MqService() {
        transactionMQProducer = new TransactionMQProducer();
        transactionMQProducer.setNamesrvAddr("localhost:9876");
        transactionMQProducer.setRetryTimesWhenSendFailed(1);
        transactionMQProducer.setProducerGroup("myGroup");
        transactionMQProducer.setSendMsgTimeout(15000);
        transactionMQProducer.setInstanceName("mq-producer1");
        transactionMQProducer.setTransactionListener(new MyTransactionListener());
        try {
            transactionMQProducer.start();
        } catch (MQClientException e) {
            throw new RuntimeException("MQ transactional producer start failed...", e);
        }

        defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr("localhost:9876");
        defaultMQProducer.setRetryTimesWhenSendFailed(1);
        defaultMQProducer.setProducerGroup("myGroup");
        defaultMQProducer.setSendMsgTimeout(15000);
        defaultMQProducer.setInstanceName("mq-producer2");
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            throw new RuntimeException("MQ producer start failed...", e);
        }
    }

    public String sendTransactionMsg() {
        try {
            // 订单
            String orderId = UUID.randomUUID().toString();
            transactionMQProducer.sendMessageInTransaction(new Message("common-order", "tag1", orderId.getBytes()), null);

        } catch (MQClientException e) {
            log.error("error:{}", e.getMessage(), e);
            return "failed";
        }
        return "success";
     }

    public void sendMessage() throws MQClientException, RemotingException, InterruptedException {
        String orderId = UUID.randomUUID().toString();
        defaultMQProducer.send(new Message("common-order", "tag2", orderId.getBytes()), new MyMqMessageCallback());
    }
}
