package com.ice.sub.library.web.entities;

import java.util.Date;

public class ExtraUserInfo {

  private Long userId;
  private String address;
  private Date createTime;
  private String introduction;
  private Short age;
  private Date birthDate;
  private String avatar;
  private Byte sex;
  private String career;
  private Double income;
  private String constellation;
  private Float height;
  private Float bodyWeight;

  public ExtraUserInfo() {

  }

  public ExtraUserInfo(UserInfo userInfo) {
    this.userId = userInfo.getUserId();
    this.address = userInfo.getAddress();
    this.createTime = userInfo.getCreateTime();
    this.introduction = userInfo.getIntroduction();
    this.age = userInfo.getAge();
    this.birthDate = userInfo.getBirthDate();
    this.avatar = userInfo.getAvatar();
    this.sex = userInfo.getSex();
    this.career = userInfo.getCareer();
    this.income = userInfo.getIncome();
    this.constellation = userInfo.getConstellation();
    this.height = userInfo.getHeight();
    this.bodyWeight = userInfo.getBodyWeight();
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
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
    this.avatar = avatar;
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
    this.career = career;
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
    this.constellation = constellation;
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