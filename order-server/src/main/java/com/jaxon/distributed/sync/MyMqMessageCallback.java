package com.jaxon.distributed.sync;

import org.apache.rocketmq.client.producer.RequestCallback;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * Created with IntelliJ IDEA.
 *
 * @author koala
 * @version 1.0
 * @date 2025/11/30/15:23
 * @description
 */
public class MyMqMessageCallback implements SendCallback {
    @Override
    public void onSuccess(SendResult sendResult) {

    }

    @Override
    public void onException(Throwable e) {

    }
}
