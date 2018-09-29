package com.ice.sub.library.migrate.service;

import com.ice.sub.library.migrate.entities.UserInfo;

/**
 * @author:ice
 * @Date: 2018/9/10 20:10
 */

public interface UserInfoService {

  /**
   * 保存用户信息.
   */
  int saveUserInfo(UserInfo userInfo);

  /**
   * 根据userId查询用户
   */
  UserInfo selectUser(UserInfo userInfo);

  /**
   * 根据userId删除用户
   */
  int deleteUser(UserInfo userInfo);

  /**
   * 更新用户数据
   */
  int updateUserInfo(UserInfo userInfo);

  /**
   * 更新用户状态
   */
  int updateUserInfoStatus(UserInfo userInfo);

}
