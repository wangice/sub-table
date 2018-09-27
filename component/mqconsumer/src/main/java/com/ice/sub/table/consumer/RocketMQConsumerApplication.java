package com.ice.sub.table.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RocketMQ消息消费
 *
 * @author Charles
 * @create 2017-06-20 19:20
 **/
@SpringBootApplication
public class RocketMQConsumerApplication {

  public static void main(String[] args) {
    System.setProperty("rocketmq.client.log.loadconfig", "false");

    SpringApplication.run(RocketMQConsumerApplication.class, args);
  }
}
