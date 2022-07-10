package com.asiainfo.distributed.biz;

public class StoreService {
    public void deduct(Store store) {
        System.out.println("库存：" + store.getLeft());
        if (store.getLeft() > 0) {
            // 扣减
            store.setLeft(store.getLeft() - 1);
        }
    }
}
