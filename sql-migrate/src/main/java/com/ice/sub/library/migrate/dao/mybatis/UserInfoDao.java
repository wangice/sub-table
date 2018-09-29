package com.ice.sub.library.migrate.dao.mybatis;

import com.ice.sub.library.migrate.entities.UserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author ice
 * @Date 2018/9/28 19:36
 */
public interface UserInfoDao {

  /**
   * 查询小于等于userId的用户数量
   */
  long countUserInfo(@Param("userId") Long userId);

  /**
   * 分页查询用户数据
   */
  List<UserInfo> selectUserInfo(@Param("userId") Long userId, @Param("index") Long index);
}
