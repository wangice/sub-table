package com.ice.sub.library.check.dao.mybatis;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ice.sub.library.check.entities.UserInfo;

/**
 * @author ice
 * @Date 2018/9/28 19:36
 */
public interface UserInfoDao {

  /**
   * 分页查询用户数据
   */
  List<UserInfo> selectUserInfo(@Param("userId") Long userId);
}
