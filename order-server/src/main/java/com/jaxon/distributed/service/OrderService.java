package com.jaxon.distributed.service;

import com.jaxon.distributed.biz.LocalTransMessage;
import com.jaxon.distributed.sync.MqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author koala
 * @version 1.0
 * @date 2025/11/30/14:50
 * @description
 */
@Service
@Slf4j
public class OrderService {
    public String createOrder() {
        try {
            log.info("开始创建订单");
            // todo 1.创建订单
            // todo 2.保存本地消息表
//            int i = 1 / 0; // 模拟异常
        } catch (Exception e) {
            log.error("创建订单异常", e);
            throw e;
        }
        return "success";
    }

    private LocalTransMessage buildLocalTransMessage() {
        LocalTransMessage localTransMessage = new LocalTransMessage();
        localTransMessage.setMessageId("123");
        localTransMessage.setTransactionState("committed");
        return localTransMessage;
    }
}
