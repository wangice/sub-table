package com.ice.sub.library.check.dao.proxy;

import com.ice.sub.library.check.entities.ExtraUserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author ice
 * @Date 2018/9/28 19:40
 */
public interface ExtraUserInfoDao {

  /**
   * 查询用户的扩展数据
   */
  ExtraUserInfo selectExtraUserInfo(@Param("userId") Long userId);
}
