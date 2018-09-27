package com.ice.sub.table.consumer.configuration;

import com.ice.brother.house.common.mq.RocketMQContents;
import com.ice.sub.table.consumer.listener.OrderlyMessageListener;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

/**
 * @author:ice
 * @Date: 2018/8/9 18:12
 */
@Configuration
public class RocketMQConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(RocketMQConfiguration.class);
  @Value("${rocketmq.namesrvAddr}")
  private String nameSrvAddr;
  @Value("${rocketmq.producerGroup}")
  private String producerGroup;
  @Value("${rocketmq.consumerGroup}")
  private String consumerGroup;

  @Autowired
  private OrderlyMessageListener orderlyMessageListener;

  private List<DefaultMQPushConsumer> defaultMQPushConsumers = new ArrayList<>();


  @Bean(name = "operationMqConsumerOrderly")
  public DefaultMQPushConsumer operationMqConsumerOrderly() {
    DefaultMQPushConsumer mysqlMQPushConsumer = new DefaultMQPushConsumer(
        getConsumerGroup(RocketMQContents.MYSQL_DDL_ORDERLY_TOPIC));
    mysqlMQPushConsumer.setNamesrvAddr(nameSrvAddr);
    mysqlMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
    try {
      mysqlMQPushConsumer.subscribe(RocketMQContents.MYSQL_DDL_ORDERLY_TOPIC, "*");
      mysqlMQPushConsumer.registerMessageListener(orderlyMessageListener);
      mysqlMQPushConsumer.start();
    } catch (MQClientException e) {
      logger.error("RocketMQ cdrMQPushConsumer Start  Exception ! e={}", e);
    }
    logger.debug("RocketMQ cdrMQPushConsumer Start Success!");
    this.defaultMQPushConsumers.add(mysqlMQPushConsumer);
    return mysqlMQPushConsumer;
  }


  @PreDestroy
  public void destroy() {
    if (!CollectionUtils.isEmpty(defaultMQPushConsumers)) {
      defaultMQPushConsumers.forEach(defaultMQPushConsumer -> defaultMQPushConsumer.shutdown());
    }
  }

  private String getConsumerGroup(String topic) {
    return consumerGroup + "_" + topic;
  }

}
