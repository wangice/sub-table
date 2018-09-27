package com.ice.sub.library.table.service.impl;

import com.ice.sub.library.table.dao.UserInfoDao;
import com.ice.sub.library.table.entities.UserInfo;
import com.ice.sub.library.table.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:ice
 * @Date: 2018/9/10 20:11
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Autowired
  private UserInfoDao userInfoDao;

  @Override
  public int saveUserInfo(UserInfo userInfo) {
    return userInfoDao.insertUserInfo(userInfo);
  }

  @Override
  public UserInfo selectUser(UserInfo userInfo) {
    UserInfo user = userInfoDao.selectUserInfo(userInfo);
    return user;
  }

  @Override
  public int deleteUser(UserInfo userInfo) {
    return userInfoDao.delete(userInfo);
  }

  @Override
  public int updateUserInfo(UserInfo userInfo) {
    return userInfoDao.update(userInfo);
  }

  @Override
  public int updateUserInfoStatus(UserInfo userInfo) {
    return userInfoDao.updateStatus(userInfo);
  }
}
