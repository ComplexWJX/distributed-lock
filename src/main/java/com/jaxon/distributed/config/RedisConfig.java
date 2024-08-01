package com.jaxon.distributed.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName RedisConfig
 * @Description TODO
 * @Author QQ
 * @Date Create in 2020/5/18 23:59
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfig {

    private String host;

    private String password;
}
