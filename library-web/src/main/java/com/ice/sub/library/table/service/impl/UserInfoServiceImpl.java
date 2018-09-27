package com.ice.sub.library.table.service.impl;

import com.ice.sub.library.table.dao.mybatis.UserInfoDao;
import com.ice.sub.library.table.dao.proxy.BaseUserInfoProxyDao;
import com.ice.sub.library.table.dao.proxy.ExtraUserInfoProxyDao;
import com.ice.sub.library.table.entities.BaseUserInfo;
import com.ice.sub.library.table.entities.ExtraUserInfo;
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

  @Autowired
  private BaseUserInfoProxyDao baseUserInfoProxyDao;

  @Autowired
  private ExtraUserInfoProxyDao extraUserInfoProxyDao;

  @Override
  public int saveUserInfo(UserInfo userInfo) {
    userInfoDao.insertUserInfo(userInfo);
    BaseUserInfo baseUserInfo = new BaseUserInfo(userInfo);
    ExtraUserInfo extraUserInfo = new ExtraUserInfo(userInfo);
    baseUserInfoProxyDao.insertBaseUserInfo(baseUserInfo);
    extraUserInfoProxyDao.insertExtraUserInfo(extraUserInfo);
    return 1;
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
