package com.ice.sub.library.web.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author:ice
 * @Date: 2018/9/2 0002
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

  @Bean
  @ConditionalOnMissingBean(name = "redisTemplate")
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    ParserConfig.getGlobalInstance().setAutoTypeSupport(true);//设置自动解析匹配，否则会报错
    //使用fastjson序列化
    template.setConnectionFactory(redisConnectionFactory);
    FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
    // value值的序列化采用fastJsonRedisSerializer
    template.setValueSerializer(fastJsonRedisSerializer);
    template.setHashValueSerializer(fastJsonRedisSerializer);
    // key的序列化采用StringRedisSerializer
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.afterPropertiesSet();
    return template;
  }

  //缓存管理器
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
        .fromConnectionFactory(redisConnectionFactory);
    return builder.build();
  }


  @Bean
  @ConditionalOnMissingBean(StringRedisTemplate.class)
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }

  /**
   * 实例化 HashOperations 对象,可以使用 Hash 类型操作
   */
  @Bean
  public HashOperations<String, String, Object> hashOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForHash();
  }

  /**
   * 实例化 ValueOperations 对象,可以使用 String 操作
   */
  @Bean
  public ValueOperations<String, Object> valueOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForValue();
  }

  /**
   * 实例化 ListOperations 对象,可以使用 List 操作
   */
  @Bean
  public ListOperations<String, Object> listOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForList();
  }

  /**
   * 实例化 SetOperations 对象,可以使用 Set 操作
   */
  @Bean
  public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForSet();
  }

  /**
   * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
   */
  @Bean
  public ZSetOperations<String, Object> zSetOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForZSet();
  }
}
