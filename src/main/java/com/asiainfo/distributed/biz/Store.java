package com.asiainfo.distributed.biz;

import lombok.Data;

/**
 * @author WJX
 */
@Data
public class Store {
    private volatile int total;
    private volatile int left;

    public Store(int total) {
        this.total = total;
        this.left = total;
    }
}
