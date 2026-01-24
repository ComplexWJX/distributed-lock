package com.jaxon.distributed.sync;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
   // @Bean("whThreadPoolExecutor")
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(10, 20, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(2), r -> {
            Thread thread = new Thread(r);
            thread.setName("store-thread");
            return thread;
        }, (r, executor) -> {
            //executor.execute(r);
            //System.out.println("队列已满，拒绝服务...");
        });
    }
}
