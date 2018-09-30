package com.ice.sub.library.web.entities;

import java.util.Date;

/**
 * @author ice
 * @Date 2018/9/10 18:58
 */
public class UserInfo {

  private Integer id;

  private Long userId;

  private String account;

  private String password;

  private String userName;

  private String identity;

  private String realName;

  private String introduction;

  private String email;

  private String phoneNumber;

  private String address;

  private Date createTime;

  private Date lastLoginTime;

  private Byte status;

  private Date freezingTime;

  private Short age;

  private Date birthDate;

  private String avatar;

  private Byte sex;

  private String career;

  private Double income;

  private String constellation;

  private Float height;

  private Float bodyWeight;

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

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction == null ? null : introduction.trim();
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

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public Byte getStatus() {
    return status;
  }

  public void setStatus(Byte status) {
    this.status = status;
  }

  public Date getFreezingTime() {
    return freezingTime;
  }

  public void setFreezingTime(Date freezingTime) {
    this.freezingTime = freezingTime;
  }

  public Short getAge() {
    return age;
  }

  public void setAge(Short age) {
    this.age = age;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar == null ? null : avatar.trim();
  }

  public Byte getSex() {
    return sex;
  }

  public void setSex(Byte sex) {
    this.sex = sex;
  }

  public String getCareer() {
    return career;
  }

  public void setCareer(String career) {
    this.career = career == null ? null : career.trim();
  }

  public Double getIncome() {
    return income;
  }

  public void setIncome(Double income) {
    this.income = income;
  }

  public String getConstellation() {
    return constellation;
  }

  public void setConstellation(String constellation) {
    this.constellation = constellation == null ? null : constellation.trim();
  }

  public Float getHeight() {
    return height;
  }

  public void setHeight(Float height) {
    this.height = height;
  }

  public Float getBodyWeight() {
    return bodyWeight;
  }

  public void setBodyWeight(Float bodyWeight) {
    this.bodyWeight = bodyWeight;
  }
}
