package com.jaxon.distributed.sync;

import org.redisson.Redisson;

/**
 * Created with IntelliJ IDEA.
 *
 * @author WJX
 * @version 1.0
 * @date 2024/04/22/14:53
 * @description
 */
public class RedissonLock {
    private Redisson redisson;

    public RedissonLock(Redisson redisson) {
        this.redisson = redisson;
    }

    public void lock(String lockName) {
        redisson.getLock(lockName).lock();
    }

    public void unlock(String lockName) {
        redisson.getLock(lockName).unlock();
    }
}
