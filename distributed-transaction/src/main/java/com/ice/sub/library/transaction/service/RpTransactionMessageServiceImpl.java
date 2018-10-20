package com.ice.sub.library.transaction.service;

import com.ice.sub.library.transaction.entities.RpTransactionMessage;
import com.ice.sub.library.transaction.trsthrow.MessageBizException;

/**
 * @author ice
 * @Date 2018/10/20 16:35
 */
public class RpTransactionMessageServiceImpl implements RpTransactionMessageService{

  @Override
  public int saveMessageWaitingConfirm(RpTransactionMessage rpTransactionMessage)
      throws MessageBizException {
    return 0;
  }

  @Override
  public void confirmAndSendMessage(String messageId) throws MessageBizException {

  }

  @Override
  public int saveAndSendMessage(RpTransactionMessage rpTransactionMessage)
      throws MessageBizException {
    return 0;
  }

  @Override
  public void directSendMessage(RpTransactionMessage rpTransactionMessage)
      throws MessageBizException {

  }

  @Override
  public void reSendMessage(RpTransactionMessage rpTransactionMessage) throws MessageBizException {

  }

  @Override
  public void reSendMessageByMessageId(String messageId) throws MessageBizException {

  }

  @Override
  public void setMessageToAreadlyDead(String messageId) throws MessageBizException {

  }

  @Override
  public RpTransactionMessage getMessageByMessageId(String messageId) throws MessageBizException {
    return null;
  }

  @Override
  public void deleteMessageByMessageId(String messageId) throws MessageBizException {

  }

  @Override
  public void reSendAllDeadMessageByQueueName(String queueName, int batchSize)
      throws MessageBizException {

  }
}
