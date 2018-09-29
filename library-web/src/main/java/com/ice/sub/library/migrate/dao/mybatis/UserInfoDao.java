package com.ice.sub.library.migrate.dao.mybatis;

import com.ice.sub.library.migrate.entities.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoDao {

  UserInfo selectUserInfo(UserInfo pojo);

  int insertUserInfo(@Param("pojo") UserInfo pojo);

  int update(@Param("pojo") UserInfo pojo);

  int delete(@Param("pojo") UserInfo pojo);

  int updateStatus(UserInfo pojo);
}
