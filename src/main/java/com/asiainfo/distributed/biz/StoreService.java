package com.asiainfo.distributed.biz;

public class StoreService {
    public void deduct(Store store) {
        if (store.getTotal() > 0) {
            // 扣减
            store.setLeft(store.getLeft() - 1);
        }
        System.out.println("库存：" + store.getLeft());
    }
}
