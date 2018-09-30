package com.ice.sub.library.web.service.redis;

import com.ice.sub.library.web.constants.RedisConstant;
import com.ice.sub.library.web.core.redis.IRedisService;
import com.ice.sub.library.web.entities.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author ice
 * @Date 2018/9/30 12:24
 */
@Service
public class UserRedisService extends IRedisService<UserInfo> {

  @Override
  protected String getRedisKey() {
    return RedisConstant.USER_INFO;
  }
}
