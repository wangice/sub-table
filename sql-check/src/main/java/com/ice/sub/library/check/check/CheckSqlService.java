package com.ice.sub.library.check.check;

import com.ice.sub.library.check.dao.mybatis.UserInfoDao;
import com.ice.sub.library.check.dao.proxy.BaseUserInfoDao;
import com.ice.sub.library.check.dao.proxy.ExtraUserInfoDao;
import com.ice.sub.library.check.entities.BaseUserInfo;
import com.ice.sub.library.check.entities.ExtraUserInfo;
import com.ice.sub.library.check.entities.UserInfo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/29 17:36
 */
@Component
public class CheckSqlService {

  private static final Logger logger = LoggerFactory.getLogger(CheckSqlService.class);

  @Autowired
  private UserInfoDao userInfoDao;

  @Autowired
  private BaseUserInfoDao baseUserInfoDao;

  @Autowired
  private ExtraUserInfoDao extraUserInfoDao;


  public void checkSql() {
    Long userId = null;
    while (true) {
      List<UserInfo> userInfos = userInfoDao.selectUserInfo(userId);
      if (userInfos.isEmpty()) {
        break;
      }
      userId = userInfos.get(userInfos.size() - 1).getUserId();//获取最后UserId
      for (UserInfo userInfo : userInfos) {
        BaseUserInfo baseUserInfo = new BaseUserInfo(userInfo);
        ExtraUserInfo extraUserInfo = new ExtraUserInfo(userInfo);
        //比较对象
        BaseUserInfo copyBaseUserInfo = baseUserInfoDao.selectBaseUserInfo(userInfo.getUserId());
        ExtraUserInfo copyExtraUserInfo = extraUserInfoDao
            .selectExtraUserInfo(userInfo.getUserId());
        if (!baseUserInfo.equals(copyBaseUserInfo)) {
          logger.info("userid:{} tb_user and tb_base_user_info not equal", userInfo.getUserId());
        }
        if (!extraUserInfo.equals(copyExtraUserInfo)) {
          logger.info("userid:{} tb_user and tb_extra_user_info not equal", userInfo.getUserId());
        }
      }
    }
  }
}
