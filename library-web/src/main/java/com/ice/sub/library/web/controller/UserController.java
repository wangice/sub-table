package com.ice.sub.library.web.controller;

import com.ice.brother.house.ODateu;
import com.ice.sub.library.web.core.Rsp;
import com.ice.sub.library.web.core.Rsp.RspEnum;
import com.ice.sub.library.web.entities.UserInfo;
import com.ice.sub.library.web.rsp.RspUserInfo;
import com.ice.sub.library.web.service.UserInfoService;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ice
 * @Date: 2018/9/10 19:57
 */
@RestController
public class UserController {

  @Resource
  private UserInfoService userInfoService;


  /**
   * 新增用户数据
   */
  @PostMapping(value = "/user")
  public Rsp addUserInfo(@RequestBody UserInfo userInfo, HttpServletRequest request) {
    if (userInfo.getUserId() != null) {
      return Rsp.transEnd(RspEnum.ERR_MISSING_PARAM_ERROR);
    }
    UserInfo user = userInfoService.selectUser(userInfo);
    if (user != null) {
      return Rsp.transEnd(RspEnum.ERR_ACCOUNT_EXSITS_ERROR);
    }
    Date now = new Date();
    String time = ODateu.parseDateyyyyMMddHHmmssms2(now);
    userInfo.setUserId(Long.parseLong(time));
    userInfo.setCreateTime(now);
    userInfo.setStatus((byte) 0);
    int success = userInfoService.saveUserInfo(userInfo);
    if (success < 1) {
      return Rsp.transEnd(RspEnum.ERR_OPERATE_FAIL);
    }
    return Rsp.transEnd(RspEnum.ERR_OPERATE_SUCCESS);
  }

  /**
   * 查询用户信息.
   */
  @GetMapping(value = "/user/{userId}")
  public Rsp queryUserInfo(@PathVariable("userId") Long userId) {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(userId);
    UserInfo user = userInfoService.selectUser(userInfo);
    if (user == null) {
      return Rsp.transEnd(RspEnum.ERR_NOT_FOUND_USER_ERROR);
    }
    return Rsp.transEnd(RspEnum.ERR_SUCCESS, new RspUserInfo(user));
  }

  /**
   * 修改用户信息.
   */
  @PostMapping("/user/{userId}")
  public Rsp updateUserInfo(@PathVariable("userId") Long userId,
      @RequestBody UserInfo userInfo) {
    userInfo.setUserId(userId);
    UserInfo user = userInfoService.selectUser(userInfo);
    if (user == null) {
      return Rsp.transEnd(RspEnum.ERR_NOT_FOUND_USER_ERROR);
    }
    int succ = userInfoService.updateUserInfo(userInfo);
    if (succ < 1) {
      return Rsp.transEnd(RspEnum.ERR_OPERATE_FAIL);
    }
    return Rsp.transEnd(RspEnum.ERR_OPERATE_SUCCESS);
  }


  /**
   * 修改状态.
   */
  @PostMapping("/user/{userId}/{status}")
  public Rsp updateUserStatus(@PathVariable("userId") String account,
      @PathVariable("status") int status) {
    UserInfo userInfo = new UserInfo();
    userInfo.setAccount(account);
    UserInfo user = userInfoService.selectUser(userInfo);
    if (user == null) {
      return Rsp.transEnd(RspEnum.ERR_NOT_FOUND_USER_ERROR);
    }
    userInfo.setStatus((byte) status);
    int success = userInfoService.updateUserInfoStatus(userInfo);
    if (success < 1) {
      return Rsp.transEnd(RspEnum.ERR_OPERATE_FAIL);
    }
    return Rsp.transEnd(RspEnum.ERR_OPERATE_SUCCESS);
  }

  /**
   * 删除用户
   */
  @DeleteMapping("/user/{account}")
  public Rsp deleteUser(@PathVariable("account") String account) {
    UserInfo userInfo = new UserInfo();
    userInfo.setAccount(account);
    int success = userInfoService.deleteUser(userInfo);
    if (success < 1) {
      return Rsp.transEnd(RspEnum.ERR_OPERATE_SUCCESS);
    }
    return Rsp.transEnd(RspEnum.ERR_OPERATE_SUCCESS);
  }
}
