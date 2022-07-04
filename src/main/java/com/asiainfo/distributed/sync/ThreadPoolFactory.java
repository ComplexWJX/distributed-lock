package com.asiainfo.distributed.sync;

import org.springframework.context.annotation.Bean;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
   // @Bean("whThreadPoolExecutor")
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(1, 2, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> {
            Thread thread = new Thread(r);
            thread.setName("wh-thread");
            return thread;
        });
    }
}
