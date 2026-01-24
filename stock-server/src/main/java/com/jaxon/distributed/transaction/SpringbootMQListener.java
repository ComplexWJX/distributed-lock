package com.jaxon.distributed.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * TODO： 该方式订阅事务消息，如何进行消费重试?
 *
 * @author koala
 * @version 1.0
 * @date 2025/11/29/11:02
 * @description
 */
@RocketMQMessageListener(topic = "common-order",consumerGroup = "trans-consumer-group", maxReconsumeTimes = 5)
@Slf4j
@Component
public class SpringbootMQListener implements RocketMQListener<String> {
    int failCount = 0;
    @Override
    public void onMessage(String message) {
        // 重试触发间隔为指数退避方式递增
        log.info("receive message:{}",message);
        failCount++;
        if (failCount <= 4) {
            throw new RuntimeException("模拟消费失败");
        }
    }
}
