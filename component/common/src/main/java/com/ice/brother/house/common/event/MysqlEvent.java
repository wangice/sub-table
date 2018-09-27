package com.ice.brother.house.common.event;

import com.ice.brother.house.Misc;
import com.ice.brother.house.common.mq.RocketMQContents;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ice
 * @Date 2018/9/26 15:22
 */
public class MysqlEvent extends BaseEvent {

  private String host;//主机名称
  private String sql; //操作语句
  private long timestamp;//时间戳


  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String getTopic() {
    return RocketMQContents.MYSQL_DDL_ORDERLY_TOPIC;
  }

  @Override
  public String getTag() {
    return RocketMQContents.MYSQL_DDL_ORDERLY_TAG;
  }

  @Override
  public byte[] toByteArray() {
    return Misc.object2Bytes(this);
  }

  public static MysqlEvent fromBytes(byte[] bytes) throws Exception {
    String str = new String(bytes);
    MysqlEvent event = Misc.json2Obj(str, MysqlEvent.class);
    boolean empty = StringUtils.isEmpty(str);
    return event;
  }
}
