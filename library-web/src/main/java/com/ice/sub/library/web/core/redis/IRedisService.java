package com.ice.sub.library.web.core.redis;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author:ice
 * @Date: 2018/9/2 0002
 */
public abstract class IRedisService<T> {

  @Autowired
  protected RedisTemplate<String, Object> redisTemplate;
  @Resource
  protected ValueOperations<String, T> valueOperations;

  /**
   * 存入redis中的key
   */
  protected abstract String getRedisKey();

  /**
   * 添加
   *
   * @param key key
   * @param doamin 对象
   * @param expire 过期时间(单位:秒),传入 -1 时表示不设置过期时间
   */
  public void put(String key, T doamin, long expire) {
    valueOperations.set(getRedisKey() + key, doamin);
    if (expire != -1) {
      redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
    }
  }


  /**
   * 删除
   *
   * @param key 传入key的名称
   */
  public void remove(String key) {
    redisTemplate.delete(getRedisKey() + key);
  }

  /**
   * 查询
   *
   * @param key 查询的key
   */
  public T get(String key) {
    return valueOperations.get(getRedisKey() + key);
  }

  /**
   * 判断key是否存在redis中
   *
   * @param key 传入key的名称
   */
  public boolean isKeyExists(String key) {
    return redisTemplate.hasKey(getRedisKey() + key);
  }

}
