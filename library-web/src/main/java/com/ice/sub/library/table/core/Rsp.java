package com.ice.sub.library.table.core;

/**
 * @author:ice
 * @Date: 2018/9/10 20:00
 */
public class Rsp {

  public enum RspEnum {
    ERR_SUCCESS(0x00000, "成功"),
    ERR_OPERATE_SUCCESS(0x00002, "操作成功"),
    ERR_OPERATE_FAIL(0x00003, "操作失败"),
    ERR_DELETE_FAIL(0x00005, "删除失败"),

    ERR_MISSING_PARAM_ERROR(0x00010, "缺少必要的参数"),

    ERR_NOT_FOUND_USER_ERROR(0x00101, "没有找到用户"),
    ERR_ACCOUNT_EXSITS_ERROR(0x00102, "用户账号已经存在"),
    ERR_END(0x00000, "结束");

    private int code;
    private String desc;

    RspEnum(int code, String desc) {
      this.code = code;
      this.desc = desc;
    }

    public int getCode() {
      return code;
    }

    public String getDesc() {
      return desc;
    }
  }

  private int code;
  private String desc;
  private Object dat;

  public static Rsp transEnd(RspEnum rspEnum) {
    Rsp rsp = new Rsp();
    rsp.code = rspEnum.code;
    rsp.desc = rspEnum.desc;
    return rsp;
  }

  public static Rsp transEnd(RspEnum rspEnum, Object dat) {
    Rsp rsp = new Rsp();
    rsp.code = rspEnum.code;
    rsp.desc = rspEnum.desc;
    rsp.dat = dat;
    return rsp;
  }


  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Object getDat() {
    return dat;
  }

  public void setDat(Object dat) {
    this.dat = dat;
  }
}
