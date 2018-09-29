package com.ice.sub.library.table.dao.proxy;

import com.ice.sub.library.table.entities.ExtraUserInfo;

/**
 * @author ice
 * @Date 2018/9/28 19:40
 */
public interface ExtraUserInfoDao {

  /**
   * 保存用户扩展信息
   */
  int insertExtraUserInfo(ExtraUserInfo extraUserInfo);
}
