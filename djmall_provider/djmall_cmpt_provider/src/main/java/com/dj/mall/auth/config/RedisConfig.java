/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_cmpt_provider
 * 类名：RedisConfig
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author chengf
 * @date 2020/6/22 10:34
 * Redis 配置器
 */
@Configuration
public class RedisConfig {

    /**
     * 设置RedisTemplate
     *
     * @param redisConnectionFactory 连接池工厂
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate redisTemplate = new RedisTemplate();
        // 开启自动类型支持
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // FastJSON序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 序列化时写入类的类型
        fastJsonRedisSerializer.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteClassName);

        // key的序列化采用StringRedisSerializer
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        // 值序列化JSON
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
