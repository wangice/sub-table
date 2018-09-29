package com.ice.sub.library.migrate.mq.entities;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ice
 * @Date 2018/9/29 09:49
 */
public class BinLogEvent {

  private String eventType;//日志类型
  private Long serverId;
  private Long timestamp;//时间戳
  private String database;//数据库名
  private String table;//表名
  private String binLog;//
  private Long position;
  private Long nextPosition;
  private BitSet includedColumnsBeforeUpdate;
  private BitSet includedColumns;
  private Map<String, String> before = new HashMap<>();//更新前字段
  private Map<String, String> after = new HashMap<>();//更新后字段

  public Long getServerId() {
    return serverId;
  }

  public void setServerId(Long serverId) {
    this.serverId = serverId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public Long getPosition() {
    return position;
  }

  public void setPosition(Long position) {
    this.position = position;
  }

  public Long getNextPosition() {
    return nextPosition;
  }

  public void setNextPosition(Long nextPosition) {
    this.nextPosition = nextPosition;
  }

  public String getBinLog() {
    return binLog;
  }

  public void setBinLog(String binLog) {
    this.binLog = binLog;
  }

  public BitSet getIncludedColumnsBeforeUpdate() {
    return includedColumnsBeforeUpdate;
  }

  public void setIncludedColumnsBeforeUpdate(BitSet includedColumnsBeforeUpdate) {
    this.includedColumnsBeforeUpdate = includedColumnsBeforeUpdate;
  }

  public BitSet getIncludedColumns() {
    return includedColumns;
  }

  public void setIncludedColumns(BitSet includedColumns) {
    this.includedColumns = includedColumns;
  }

  public Map<String, String> getBefore() {
    return before;
  }

  public void setBefore(Map<String, String> before) {
    this.before = before;
  }

  public Map<String, String> getAfter() {
    return after;
  }

  public void setAfter(Map<String, String> after) {
    this.after = after;
  }
}
