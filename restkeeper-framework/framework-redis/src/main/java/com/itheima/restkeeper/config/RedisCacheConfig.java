package com.itheima.restkeeper.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * @ClassName RedisCacheConfig.java
 * @Description redis配置
 */
@Configuration
//开启caching的支持
@EnableCaching
public class RedisCacheConfig {


    /**
     * 申明缓存管理器，会创建一个切面（aspect）并触发Spring缓存注解的切点（pointcut）
     * 根据类或者方法所使用的注解以及缓存的状态，这个切面会从缓存中获取数据，
     * 将数据添加到缓存之中或者从缓存中移除某个值
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 配置序列化（解决乱码的问题）
        //对key的序列化操作：String
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //对value的序列化操作：json
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();
        //配置config,指定超时时间记得key val 序列化处理
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //指定全局超时时间【60S】
                .entryTtl(Duration.ofSeconds(60))
                //配置key的序列化方式
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(redisSerializer))
                //配置value的序列化方式
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(genericJackson2JsonRedisSerializer))
                //关闭空值的存储
                .disableCachingNullValues();

        //使用建造者进行初始化
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
