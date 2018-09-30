package com.ice.sub.library.web.dao.proxy;

import com.ice.sub.library.web.entities.ExtraUserInfo;

/**
 * @author ice
 * @Date 2018/9/27 19:58
 */
public interface ExtraUserInfoProxyDao {

  int insertExtraUserInfo(ExtraUserInfo extraUserInfo);

  int insertExtraUserInfoSelective(ExtraUserInfo extraUserInfo);

  int updateExtraUserInfoSelective(ExtraUserInfo extraUserInfo);

  int updateExtraUserInfo(ExtraUserInfo extraUserInfo);

}
