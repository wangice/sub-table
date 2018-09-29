package com.ice.sub.library.migrate.migrate;

import com.ice.sub.library.migrate.dao.mybatis.UserInfoDao;
import com.ice.sub.library.migrate.dao.proxy.BaseUserInfoDao;
import com.ice.sub.library.migrate.dao.proxy.ExtraUserInfoDao;
import com.ice.sub.library.migrate.entities.BaseUserInfo;
import com.ice.sub.library.migrate.entities.ExtraUserInfo;
import com.ice.sub.library.migrate.entities.UserInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/28 19:35
 */
@Component
public class UserMigrate {

  @Value("${user.id}")
  private Long userId;

  @Autowired
  private UserInfoDao userInfoDao;

  @Autowired
  private ExtraUserInfoDao extraUserInfoDao;

  @Autowired
  private BaseUserInfoDao baseUserInfoDao;

  /**
   * 数据迁移
   */

  public void migrate() {
    Long index = null;
    while (true) {
      List<UserInfo> userInfos = userInfoDao.selectUserInfo(userId, index);
      if (userInfos.isEmpty()) {
        break;
      }
      index = userInfos.get(userInfos.size() - 1).getUserId();
      for (UserInfo userInfo : userInfos) {
        BaseUserInfo baseUserInfo = new BaseUserInfo(userInfo);
        ExtraUserInfo extraUserInfo = new ExtraUserInfo(userInfo);
        baseUserInfoDao.insertBaseUserInfo(baseUserInfo);
        extraUserInfoDao.insertExtraUserInfo(extraUserInfo);
      }
    }
  }

}
