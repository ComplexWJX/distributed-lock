package com.asiainfo.distributed.sync;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName RedisLock
 * @Description TODO
 * @Author QQ
 * @Date Create in 2020/4/10 22:27
 * @Version 1.0
 */
@Slf4j
public class RedisLock {

    private final StatefulRedisConnection connection = LettuceRedisPool.getInstance().getConnection();
    RedisAsyncCommands<String,String> asyncCommands;
    RedisLock(){
        asyncCommands = connection.async();
    }

    public void lock(){
        asyncCommands.setex("lock",3000,"abc");
        log.info("set key {}","lock success");
    }

    public void unlock(){
        asyncCommands.del("lock");
    }

    public static void main(String[] args) {
        RedisLock redisLock = new RedisLock();
        redisLock.unlock();
//        new Thread(()->{
//            try {
//                System.out.println("t1 get lock");
//                redisLock.lock();
//                Thread.sleep(3000);
//                redisLock.unlock();
//                log.info("release lock {}","lock");
//            }catch (InterruptedException ex){
//                log.error(ex.getMessage());
//            }
//        }).start();

//        new Thread(()->{
//            try {
//                System.out.println("t2 get lock");
//                redisLock.lock();
//            }finally {
//                redisLock.unlock();
//            }
//        }).start();

    }
}
