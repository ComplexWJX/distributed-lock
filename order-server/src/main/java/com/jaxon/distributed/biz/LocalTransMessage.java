package com.jaxon.distributed.biz;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author koala
 * @version 1.0
 * @date 2025/11/28/17:37
 * @description
 */
@Data
public class LocalTransMessage {
    private String messageId;
    private String message;
    private String tag;
    private String topic;
    private String key;
    private String group;
    private String transactionState;
}
