package table.entities;

import java.util.Date;

public class BaseUserInfo {

  private Integer id;

  private Long userId;

  private String account;

  private String password;

  private String userName;

  private String identity;

  private String realName;

  private String email;

  private String phoneNumber;

  private String address;

  private Byte status;

  private Date freezingTime;

  private Date lastLoginTime;


  public BaseUserInfo() {

  }

  public BaseUserInfo(UserInfo userInfo) {
    this.userId = userInfo.getUserId();
    this.account = userInfo.getAccount();
    this.password = userInfo.getPassword();
    this.userName = userInfo.getUserName();
    this.identity = userInfo.getIdentity();
    this.realName = userInfo.getRealName();
    this.email = userInfo.getEmail();
    this.phoneNumber = userInfo.getPhoneNumber();
    this.address = userInfo.getAddress();
    this.status = userInfo.getStatus();
    this.freezingTime = userInfo.getFreezingTime();
    this.lastLoginTime = userInfo.getLastLoginTime();
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account == null ? null : account.trim();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password == null ? null : password.trim();
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName == null ? null : userName.trim();
  }

  public String getIdentity() {
    return identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity == null ? null : identity.trim();
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName == null ? null : realName.trim();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email == null ? null : email.trim();
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address == null ? null : address.trim();
  }

  public Byte getStatus() {
    return status;
  }

  public void setStatus(Byte status) {
    this.status = status;
  }

  public Date getFreezingTime() {
    return freezingTime == null ? null : (Date) freezingTime.clone();
  }

  public void setFreezingTime(Date freezingTime) {
    this.freezingTime = freezingTime == null ? null : (Date) freezingTime.clone();
  }

  public Date getLastLoginTime() {
    return lastLoginTime == null ? null : (Date) lastLoginTime.clone();
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime == null ? null : (Date) lastLoginTime.clone();
  }
}