package com.ice.sub.library.web.dao.proxy;

import com.ice.sub.library.web.entities.BaseUserInfo;

/**
 * @author ice
 * @Date 2018/9/27 19:33
 */
public interface BaseUserInfoProxyDao {

  int insertBaseUserInfo(BaseUserInfo baseUserInfo);

  int insertBaseUserInfoSelective(BaseUserInfo baseUserInfo);

  int updateBaseUserInfoSelective(BaseUserInfo baseUserInfo);

  int updateBaseUserInfo(BaseUserInfo baseUserInfo);
}
