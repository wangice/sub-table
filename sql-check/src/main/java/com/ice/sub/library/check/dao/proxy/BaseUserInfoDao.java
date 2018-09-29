package com.ice.sub.library.check.dao.proxy;

import com.ice.sub.library.check.entities.BaseUserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author ice
 * @Date 2018/9/28 19:36
 */
public interface BaseUserInfoDao {

  /**
   * 查询用户的基础数据
   */
  BaseUserInfo selectBaseUserInfo(@Param("userId") Long userId);
}
