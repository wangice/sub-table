package com.ice.sub.table.consumer.listener;

import com.ice.brother.house.Misc;
import com.ice.brother.house.common.event.MysqlEvent;
import com.ice.brother.house.common.mq.RocketMQContents;
import java.util.List;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 需要单独编写，
 *
 * @author:ice
 * @Date: 2018/9/19 0019
 */
@Component
public class OrderlyMessageListener implements MessageListenerOrderly {

  private static final Logger logger = LoggerFactory.getLogger(OrderlyMessageListener.class);

  @Override
  public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list,
      ConsumeOrderlyContext context) {
    //设置自动提交
    context.setAutoCommit(true);
    if (CollectionUtils.isEmpty(list)) {
      return ConsumeOrderlyStatus.SUCCESS;
    }
    list.forEach(msg -> {
      if (!RocketMQContents.MYSQL_DDL_ORDERLY_TOPIC.equals(msg.getTopic())) {
        return;
      }
      String tags = msg.getTags();
      logger.debug("tags:{}", tags);
      try {
        switch (tags) {
          case RocketMQContents.MYSQL_DDL_ORDERLY_TAG:
            handleOrderlyMessage(msg);
            break;
          default:
            logger.debug("message tag is exists! tag:{}", tags);
            break;
        }
      } catch (Exception e) {
        logger.debug("tag is {},Error while decode&save:{}", tags, Misc.trace(e));
      }
    });
    //TODO: 消费消息
    return ConsumeOrderlyStatus.SUCCESS;
  }

  public void handleOrderlyMessage(MessageExt msg) throws Exception {
    MysqlEvent event = MysqlEvent.fromBytes(msg.getBody());
    logger.info("msg:{}", Misc.obj2json(event));
    logger.info("body:{}",event.getSql());
  }
}
