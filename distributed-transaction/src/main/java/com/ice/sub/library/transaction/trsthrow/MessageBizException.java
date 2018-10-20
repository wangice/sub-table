package com.ice.sub.library.transaction.trsthrow;

/**
 * @author ice
 * @Date 2018/10/20 16:33
 */
public class MessageBizException extends Exception {

  public static final int SAVA_MESSAGE_IS_NULL = 101; //保存的消息为空

  public static final int MESSAGE_CONSUMER_QUEUE_IS_NULL = 102;  //息的消费队列不能为空

  private int code;

  private String msg;

  public MessageBizException(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
