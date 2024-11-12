package dev.urmat.taskmega;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public org.springframework.data.redis.cache.RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        org.springframework.data.redis.cache.RedisCacheConfiguration     config = org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues();
        return org.springframework.data.redis.cache.RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
