package com.ice.sub.library.web.service.impl;

import com.ice.sub.library.web.constants.RedisConstant;
import com.ice.sub.library.web.dao.mybatis.UserInfoDao;
import com.ice.sub.library.web.entities.UserInfo;
import com.ice.sub.library.web.service.UserInfoService;
import com.ice.sub.library.web.service.redis.UserRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 1.缓存穿透：
 *
 * @author:ice
 * @Date: 2018/9/10 20:11
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Autowired
  private UserInfoDao userInfoDao;

  @Autowired
  private UserRedisService userRedisService;

  @Override
  public int saveUserInfo(UserInfo userInfo) {
    userInfoDao.insertUserInfo(userInfo);
    return 1;
  }

  @Override
  public UserInfo selectUser(UserInfo userInfo) {
    UserInfo user = userRedisService.get(userInfo.getUserId() + "");
    if (user == null) {//没有命中，数据库汇总查询
      user = userInfoDao.selectUserInfo(userInfo);
      if (user != null) {//数据库有值的情况下
        userRedisService.put(userInfo.getUserId() + "", user, RedisConstant.OBJECT_EXPIRE_TIME);
      } else {//TODO: 没值的情况下,设置一个空值，并设置五分钟过期时间，防止某一时间大量并发访问，击穿数据库
        userRedisService.put(userInfo.getUserId() + "", userInfo, RedisConstant.NULL_EXPIRE_TIME);
      }
    }
    if (user != null && user.getUserId() != null && user.getAccount() == null) {//表示该值时空值
      user = null;
    }
    return user;
  }

  @Override
  public int deleteUser(UserInfo userInfo) {
    int success = userInfoDao.delete(userInfo);
    if (success > 0) {//删除成功
      userRedisService.remove(userInfo.getUserId() + "");//让缓存失效
    }
    return success;
  }

  @Override
  public int updateUserInfo(UserInfo userInfo) {
    int success = userInfoDao.update(userInfo);//更新数据库
    if (success > 0) {
      userRedisService.remove(userInfo.getUserId() + "");//让缓存失效
    }
    return success;
  }

  @Override
  public int updateUserInfoStatus(UserInfo userInfo) {
    int success = userInfoDao.updateStatus(userInfo);
    if (success > 0) {
      userRedisService.remove(userInfo.getUserId() + "");//让缓存失效
    }
    return success;
  }
}
