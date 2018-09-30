package com.ice.sub.library.web.rsp;

import com.ice.sub.library.web.entities.UserInfo;

/**
 * @author:ice
 * @Date: 2018/9/11 19:35
 */
public class RspUserInfo {

  private String account;
  private String userName;
  private String identity;
  private String realName;

  public RspUserInfo(UserInfo userInfo) {
    this.account = userInfo.getAccount();
    this.userName = userInfo.getUserName();
    this.identity = userInfo.getIdentity();
    this.realName = userInfo.getRealName();
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getIdentity() {
    return identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }
}
