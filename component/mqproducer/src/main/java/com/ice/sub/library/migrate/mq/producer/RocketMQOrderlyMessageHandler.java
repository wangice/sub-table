package com.ice.sub.library.migrate.mq.producer;

import com.ice.brother.house.common.event.BaseEvent;
import com.ice.brother.house.common.mq.MessageHandler;
import com.ice.brother.house.common.mq.RocketMQMessage;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 发送顺序消息
 *
 * @author:ice
 * @Date: 2018/9/18 0018
 */
@Component
public class RocketMQOrderlyMessageHandler implements MessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(RocketMQOrderlyMessageHandler.class);
  @Autowired
  private DefaultMQProducer defaultMQProducer;

  @Override
  public boolean sendMessage(BaseEvent baseEvent) {
    Message message = buildMessage(baseEvent);
    boolean flag = false;
    if (message == null) {
      return flag;
    }
    try {
      SendResult sendResult = defaultMQProducer.send(message,
          (MessageQueueSelector) (mqs, msg, arg) -> {
            Long id = (Long) arg;
            Long index = id % mqs.size();
            return mqs.get(index.intValue());
          }, 0L);//所有的都将落在一个队列中，可以根据某种规则进行放置消息
      if (sendResult != null && SendStatus.SEND_OK == sendResult.getSendStatus()) {
        flag = true;
      } else {
        logger.warn("sendMessage Failed");
      }
    } catch (MQClientException e) {
      e.printStackTrace();
      logger.warn("sendMessage({},{},{}) MQClientException {}", message.getTopic(),
          message.getTags(), baseEvent, e);
    } catch (RemotingException e) {
      e.printStackTrace();
      logger.warn("sendMessage({},{},{}) RemotingException {}", message.getTopic(),
          message.getTags(), baseEvent, e);
    } catch (MQBrokerException e) {
      e.printStackTrace();
      logger.warn("sendMessage({},{},{}) MQBrokerException {}", message.getTopic(),
          message.getTags(), baseEvent, e);
    } catch (InterruptedException e) {
      e.printStackTrace();
      logger.warn("sendMessage({},{},{}) InterruptedException {}", message.getTopic(),
          message.getTags(), baseEvent, e);
    }
    return flag;
  }

  /**
   * 构建RocketMQ API发送消息 消息体
   *
   * @param rocketMQMessage 消息对象
   * @return RocketMQ API发送消息 消息体
   */
  private Message buildMessage(RocketMQMessage rocketMQMessage) {
    if (rocketMQMessage != null && !StringUtils.isEmpty(rocketMQMessage.getTopic()) && !StringUtils
        .isEmpty(rocketMQMessage.getTag())) {
      return new Message(rocketMQMessage.getTopic(), rocketMQMessage.getTag(),
          rocketMQMessage.toByteArray());
    }
    return null;
  }
}
