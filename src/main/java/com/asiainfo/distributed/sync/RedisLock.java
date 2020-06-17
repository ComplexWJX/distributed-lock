package com.asiainfo.distributed.sync;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
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
    private RedisAsyncCommands<String,String> asyncCommands;
    private RedisCommands<String,String> syncCommands;
    RedisLock(){
        asyncCommands = connection.async();
        syncCommands = connection.sync();
    }

    /***
     * TODO 1.获取锁的注意点：客户端解锁要解自己加的锁，唯一标识？
     *      2.设置key失效时间时down机，设置失败，key永不过期，解决方法：
     *        （1）使用原子操作 set(k,v,nx|xx,ex|px) nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
     *           if it already exist. expx EX|PX, expire time units: EX = seconds; PX = milliseconds
     *         （2）Lua脚本实现 redis对lua脚本执行具有原子性
     *
     * @throws Exception
     */
    public void lock() throws Exception{
        RedisFuture<Boolean> booleanRedisFuturefuture;
        RedisFuture<String> stringRedisFuture;
        Boolean isLock = false;
        /*do{
            // 异步操作setnx命令实现锁，通过阻塞获取future结果
            booleanRedisFuturefuture = asyncCommands.setnx("lock","lock");
            log.info("lockValue:{}",isLock);
            Thread.sleep(1000);
        }while (!(isLock =booleanRedisFuturefuture.get()));*/

        do{
            log.info("lockValue:{}",isLock);
            SetArgs setArgs = new SetArgs().nx().ex(3);
            stringRedisFuture = asyncCommands.set("lock", "lock", setArgs);
            Thread.sleep(1000);
        }while (!(isLock = ("OK".equals(stringRedisFuture.get()))));


        log.info("========================"+Thread.currentThread().getName()+ "  get lock=====================");
        //            asyncCommands.setex("lock",3000,"lock");
        asyncCommands.expire("lock",3000); //TODO 如果执行到此处，服务器宕机，设置key失效时间失败，key永不过期，会造成死锁
    }

    public void unlock(){
        asyncCommands.del("lock");
    }

    public static void main(String[] args) throws Exception{
        RedisLock redisLock = new RedisLock();
        new Thread(()->{
            try {
                redisLock.lock();
                Thread.sleep(3000);
                redisLock.unlock();
                log.info("t1 release lock {}","lock");
            }catch (Exception ex){
                log.error(ex.getMessage());
            }
        },"t1").start();

        new Thread(()->{
            try {
                redisLock.lock();
                Thread.sleep(3000);
                redisLock.unlock();
                log.info("t2 release lock {}","lock");
            }catch (Exception ex){
                log.error(ex.getMessage());
            }
        },"t2").start();

    }
}
