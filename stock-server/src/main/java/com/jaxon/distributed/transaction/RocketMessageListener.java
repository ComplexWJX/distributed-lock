package com.jaxon.distributed.transaction;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author koala
 * @version 1.0
 * @date 2025/12/01/14:38
 * @description
 */
public class RocketMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if (msgs != null && msgs.size() > 0) {
            for (MessageExt msg : msgs) {
                // todo 1.处理消息
                // todo 2.保存本地消息表
                // todo 3.发送事务消息
            }
        }
        long ms = System.currentTimeMillis();
        if (ms % 2 == 0) {
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
