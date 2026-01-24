package com.jaxon.distributed.sync;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @ClassName LettuceRedisPool
 * @Description TODO
 * @Author QQ
 * @Date Create in 2020/4/10 22:28
 * @Version 1.0
 */
public class LettuceRedisPool {

    private static LettuceRedisPool redisPool;

    private static StatefulRedisConnection connection;

    public static LettuceRedisPool getInstance(){
        initialize();
        return redisPool;
    }

    private static void initialize(){
        if(redisPool == null){
            synchronized (LettuceRedisPool.class){
                if(redisPool==null){
                    redisPool = new LettuceRedisPool();
                    //init connect
                    init();
                }
            }
        }
    }

    private static void init(){
        RedisURI redisURI = RedisURI.builder()
                .withHost("localhost")
                .withPassword("123456")
                .withDatabase(0)
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS)).build();
        RedisClient redisClient = RedisClient.create(redisURI);
        connection = redisClient.connect();
    }


    public StatefulRedisConnection getConnection(){
        return connection;
    }
}
