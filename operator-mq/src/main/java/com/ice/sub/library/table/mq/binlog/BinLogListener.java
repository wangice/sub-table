package com.ice.sub.library.table.mq.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.ice.brother.house.common.event.MysqlEvent;
import com.ice.sub.library.table.mq.producer.RocketMQOrderlyMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/26 16:03
 */
@Component
public class BinLogListener implements EventListener {

  private static final Logger logger = LoggerFactory.getLogger(BinLogListener.class);

  private static final String EVENTTYPE = "QUERY";
  private static final String BEGIN = "BEGIN";
  @Value("bin.log.host")
  private String host;

  @Autowired
  private RocketMQOrderlyMessageHandler orderlyMessageHandler;

  @Override
  public void onEvent(Event event) {
    logger.debug("event:{}", event.toString());
    if (EVENTTYPE.equals(event.getHeader().getEventType().name())) {
      QueryEventData queryEventData = event.getData();
      if (!queryEventData.getSql().contains(BEGIN)) {
        MysqlEvent mysqlEvent = new MysqlEvent();
        mysqlEvent.setHost(host);
        mysqlEvent.setSql(queryEventData.getSql());
        mysqlEvent.setTimestamp(event.getHeader().getTimestamp());
        logger.debug("DDL sql:{}", queryEventData.getSql());
        orderlyMessageHandler.sendMessage(mysqlEvent);//发送mq消息
      }
    }
  }
}
