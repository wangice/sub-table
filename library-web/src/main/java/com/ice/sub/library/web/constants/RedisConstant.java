package com.ice.sub.library.web.constants;

import com.ice.brother.house.DateUtil;

/**
 * @author ice
 * @Date 2018/9/30 15:36
 */
public class RedisConstant {

  public static final Long OBJECT_EXPIRE_TIME = 2 * DateUtil.DAY; //实体对象的过期时间

  public static final Long NULL_EXPIRE_TIME = 5 * DateUtil.MINUTE;//空值过期时间


  public static final String USER_INFO = "user_info:";

}
