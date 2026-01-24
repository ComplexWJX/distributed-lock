package com.jaxon.distributed.sync;

import com.jaxon.distributed.biz.LocalTransMessage;
import com.jaxon.distributed.service.OrderService;
import com.jaxon.distributed.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * <p>RocketMQ 的事务消息只保证 “本地事务 + 消息发送” 的原子性</p>
 *
 * @author koala
 * @version 1.0
 * @date 2025/11/28/17:28
 * @description
 */
@Slf4j
public class MyTransactionListener implements TransactionListener {
    // RocketMQ 的事务消息只保证 “本地事务 + 消息发送” 的原子性
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object args) {
        log.info("arg: {}", args);
        try {
            boolean success = doLocalTransaction(message);
            if (success) {
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            log.error("error:{}", e.getMessage(), e);
            return LocalTransactionState.UNKNOW;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        LocalTransMessage localTransMessage = queryTransactionStatusFromDB(messageExt.getMsgId());
        boolean isCommitted = localTransMessage.getTransactionState().equals("committed");
        if (isCommitted) {
            return LocalTransactionState.COMMIT_MESSAGE;
        } else {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    private boolean doLocalTransaction(Message message) {
        OrderService orderService = SpringUtil.getBean(OrderService.class);
        String orderID = orderService.createOrder();
        return StringUtils.isNotBlank(orderID);
    }

    private LocalTransMessage queryTransactionStatusFromDB(String messageId) {
        log.info("queryTransactionStatusFromDB:{}", messageId);
        //TODO 先mock本地消息
        LocalTransMessage localTransMessage = new LocalTransMessage();
        localTransMessage.setMessageId(messageId);
        localTransMessage.setTransactionState("rollback");
        return localTransMessage;
    }
}
