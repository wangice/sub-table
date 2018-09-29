package com.ice.sub.library.migrate.mq;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author:ice
 * @Date: 2018/9/16 0016
 */
@SpringBootApplication
@ImportResource(value = {"classpath:mqproducer-config.xml"})
public class OperatorMqApplication {

  private static final Logger logger = LoggerFactory.getLogger(OperatorMqApplication.class);

  public static void main(String[] args) throws IOException {
    //禁用掉rocketmq默认的日志，改用工程项目下的resource/log.xml文件
    System.setProperty("rocketmq.client.log.loadconfig", "false");

    SpringApplication.run(OperatorMqApplication.class, args);
  }
}
