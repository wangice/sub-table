package com.ice.sub.library.migrate.mq.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.ice.brother.house.common.event.MysqlEvent;
import com.ice.sub.library.migrate.mq.dao.UserInfoDao;
import com.ice.sub.library.migrate.mq.entities.BinLogEvent;
import com.ice.sub.library.migrate.mq.entities.Columns;
import com.ice.sub.library.migrate.mq.enums.BinlogFormatEnum;
import com.ice.sub.library.migrate.mq.producer.RocketMQOrderlyMessageHandler;
import java.io.Serializable;
import java.util.List;
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

  private static final String TABLE_MAP = "TABLE_MAP";
  private static final String EXT_UPDATE_ROWS = "EXT_UPDATE_ROWS";
  private static final String EXT_DELETE_ROWS = "EXT_DELETE_ROWS";

  @Value("bin.log.host")
  private String host;

  @Value("${binlog.format}")
  private String binlogFormat;   // binlog_format的模式

  private String tableName;
  private String databaseName;

  private BinLogEvent binLogEvent = null;

  @Autowired
  private RocketMQOrderlyMessageHandler orderlyMessageHandler;

  @Autowired
  private UserInfoDao userInfoDao;

  @Override
  public void onEvent(Event event) {
    logger.debug("event:{}", event.toString());
    if (BinlogFormatEnum.ROW.name().equals(binlogFormat)) {//bin_log format ROW模式
      if (TABLE_MAP.equals(event.getHeader().getEventType().name())) {
        binLogEvent = new BinLogEvent();
        binLogEvent.setTable(((TableMapEventData) event.getData()).getTable());
        binLogEvent.setDatabase(((TableMapEventData) event.getData()).getDatabase());
      }
      if (EXT_UPDATE_ROWS.equals(event.getHeader().getEventType().name())) {//更新语句
        if (event.getData() instanceof UpdateRowsEventData) {//update
          UpdateRowsEventData updateRowsEvent = (UpdateRowsEventData) event.getData();
          binLogEvent.setTimestamp(event.getHeader().getTimestamp());
          binLogEvent
              .setIncludedColumnsBeforeUpdate(updateRowsEvent.getIncludedColumnsBeforeUpdate());
          binLogEvent.setIncludedColumns(updateRowsEvent.getIncludedColumns());
          //
          String sql = plice(binLogEvent, updateRowsEvent.getRows().get(0).getValue());
          sendMsg(sql, binLogEvent.getTimestamp());
        }
        if (EXT_DELETE_ROWS.equals(event.getHeader().getEventType().name())) {//删除语句
          if (event.getData() instanceof DeleteRowsEventData) {
            DeleteRowsEventData deleteRowsEventData = (DeleteRowsEventData) event.getData();
            binLogEvent.setTimestamp(event.getHeader().getTimestamp());
            binLogEvent.setIncludedColumns(deleteRowsEventData.getIncludedColumns());
            String sql = spliceDelete(binLogEvent, deleteRowsEventData.getRows().get(0));
            sendMsg(sql, binLogEvent.getTimestamp());
          }
        }
        binLogEvent = null;
        return;
      }
      if (BinlogFormatEnum.STATEMENT.name().equals(binlogFormat)) {
        if (EVENTTYPE.equals(event.getHeader().getEventType().name())) {
          QueryEventData queryEventData = event.getData();
          if (!queryEventData.getSql().contains(BEGIN)) {
            sendMsg(queryEventData.getSql(), event.getHeader().getTimestamp());
          }
        }
        return;
      }
    }
  }

  /**
   * 这是有主键的更新方法，没有主键，则前后进行比较，然后将未更新的字段当做where条件
   */
  private String plice(BinLogEvent binLogEvent, Serializable[] value) {
    List<Columns> columns = userInfoDao.queryColumns();
    StringBuilder whereSql = new StringBuilder(" where ");
    StringBuilder paramSql = new StringBuilder();
    int where = 0;
    for (int i = 0; i < binLogEvent.getIncludedColumns().length(); i++) {
      if (binLogEvent.getIncludedColumns().get(i)) {
        Columns column = columns.get(i);
        if ("PRI".equals(column.getColumnKey())) {//主键
          where++;
          if (where != 1) {
            whereSql.append("and ").append(column.getColumnName()).append("=")
                .append(value[i]);
          } else {
            whereSql.append(column.getColumnName()).append("=")
                .append(value[i]);
          }
        } else {
          paramSql.append(column.getColumnName()).append("=").append(value[i]).append(",");
        }
      }
    }
    String sql = paramSql.deleteCharAt(paramSql.length() - 1).toString();

    return "update " + binLogEvent.getDatabase() + "." + binLogEvent.getTable() + " set " + sql
        + whereSql.toString();
  }

  /**
   * 这是有主键的删除语句，没有主键则将所有字段都当做条件.
   */
  public String spliceDelete(BinLogEvent binLogEvent, Serializable[] value) {
    List<Columns> columns = userInfoDao.queryColumns();
    StringBuilder whereSql = new StringBuilder(" where ");
    int where = 0;
    for (int i = 0; i < binLogEvent.getIncludedColumns().length(); i++) {
      if (binLogEvent.getIncludedColumns().get(i)) {
        Columns column = columns.get(i);
        if ("PRI".equals(column.getColumnKey())) {//主键
          where++;
          if (where != 1) {
            whereSql.append("and ").append(column.getColumnName()).append("=")
                .append(value[i]);
          } else {
            whereSql.append(column.getColumnName()).append("=")
                .append(value[i]);
          }
        }
      }
    }
    return "delete from " + binLogEvent.getDatabase() + "." + binLogEvent.getTable() + whereSql
        .toString();
  }

  /**
   * 消息发送
   */
  public void sendMsg(String sql, Long timestamp) {
    MysqlEvent mysqlEvent = new MysqlEvent();
    mysqlEvent.setHost(host);
    mysqlEvent.setSql(sql);
    mysqlEvent.setTimestamp(timestamp);
    logger.debug("DDL sql:{}", sql);
    orderlyMessageHandler.sendMessage(mysqlEvent);//发送mq消息
  }

}
