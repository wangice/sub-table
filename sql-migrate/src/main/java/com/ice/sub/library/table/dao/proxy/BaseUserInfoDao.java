package com.ice.sub.library.table.dao.proxy;

import com.ice.sub.library.table.entities.BaseUserInfo;

/**
 * @author ice
 * @Date 2018/9/28 19:36
 */
public interface BaseUserInfoDao {

  /**
   * 保存用户基础数据
   */
  int insertBaseUserInfo(BaseUserInfo baseUserInfo);
}
