package com.jaxon.distributed.access;

import com.jaxon.distributed.sync.MqService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author koala
 * @version 1.0
 * @date 2025/11/30/15:26
 * @description
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private MqService mqService;
    @PostMapping("/create")
    public String createOrder() {
        return mqService.sendTransactionMsg();
    }
}
