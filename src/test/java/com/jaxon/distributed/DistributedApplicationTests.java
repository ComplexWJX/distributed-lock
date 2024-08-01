package com.jaxon.distributed;

import com.jaxon.distributed.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DistributedApplicationTests {

    @Autowired
    private RedisConfig redisConfig;

    @Test
    void contextLoads() {
        System.out.println(redisConfig.getHost());
        System.out.println(redisConfig.getPassword());
    }

}
