package com.ice.sub.table.consumer.listener;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.ice.brother.house.Misc;
import com.ice.brother.house.common.event.MysqlEvent;
import com.ice.brother.house.common.mq.RocketMQContents;
import com.ice.sub.table.consumer.constant.SqlContants;
import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list,
      ConsumeOrderlyContext context) {
    //设置自动提交
//    context.setAutoCommit(true);
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
            handleOrderlyMessage(msg);//消费消息.
            break;
          default:
            logger.debug("message tag is exists! tag:{}", tags);
            break;
        }
      } catch (Exception e) {
        logger.debug("tag is {},Error while decode&save:{}", tags, Misc.trace(e));
      }
    });
    return ConsumeOrderlyStatus.SUCCESS;
  }

  /**
   * 处理拉取的消息
   */
  public void handleOrderlyMessage(MessageExt msg) throws Exception {
    MysqlEvent event = MysqlEvent.fromBytes(msg.getBody());
    logger.info("msg:{}", Misc.obj2json(event));
    logger.info("body:{}", event.getSql());
    if (event.getSql().contains("INSERT")) {//不插入语句
      return;
    }
    if (event.getSql().contains("UPDATE")) {//将字段拆分出来
      String sql = event.getSql().replace('\n', ' ');
      String updateSql = updateSql(sql, SqlContants.BASE_USER_INFO);//更新tb_base_user_info
      logger.info("update: {}", updateSql);
      if (updateSql != null) {
        int update = jdbcTemplate.update(updateSql);
      }
      updateSql = updateSql(sql, SqlContants.EXTRA_USER_INFO);//更新tb_extra_user_info
      if (updateSql != null) {
        int update = jdbcTemplate.update(updateSql);
      }
    }
    if (event.getSql().contains("DELETE")) {//获取其中的user_id字段的值,分别对数据进行删除操作
      String baseDeleteSql = deleteSql(event.getSql(), SqlContants.BASE_USER_INFO);
      String extraDeleteSql = deleteSql(event.getSql(), SqlContants.EXTRA_USER_INFO);
      jdbcTemplate.update(baseDeleteSql);
      jdbcTemplate.update(extraDeleteSql);
    }
  }

  /**
   * 抽取sql语句字段，并转化分库分表sql语句
   */
  public static String updateSql(String sql, String table) {
    int setIndex = sql.indexOf("SET") + 3;
    int whereIndex = sql.indexOf("WHERE");
    String substring = sql.substring(setIndex, whereIndex);
    List<String> list = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(substring);
    List<String> sqlList = new ArrayList<>();
    for (String str : list) {
      if (SqlContants.BASE_USER_INFO.equals(table)) {
        if (baseInfo(str)) {
          sqlList.add(str);
        }
      } else if (SqlContants.EXTRA_USER_INFO.equals(table)) {
        if (extraInfo(str)) {
          sqlList.add(str);
        }
      }
    }
    if (sqlList.isEmpty()) {//参数为空时，直接返回
      return null;
    }
    String where = sql.substring(whereIndex);
    logger.info("where sql:{}", where);
    String splice = splice(sqlList, where, table);
    logger.debug("sql: {}", splice);
    return splice;
  }

  /**
   * 抽取sql语句字段，转化为分库分表的mysql语句
   */
  public static final String deleteSql(String sql, String table) {
    int whereIndex = sql.indexOf("WHERE");
    String where = sql.substring(whereIndex);
    StringBuilder deleteSql = new StringBuilder();
    deleteSql.append("delete ").append(table).append(" ").append(where);
    return deleteSql.toString();
  }

  /**
   * 拼接update语句
   */
  public static final String splice(List<String> params, String where, String table) {
    String join = Joiner.on(",").skipNulls().join(params);
    StringBuilder updateSql = new StringBuilder();
    updateSql.append("update ").append(table).append(" set ").append(join).append(" ").append(where);
    return updateSql.toString();
  }

  /**
   * 判断是否为tb_base_user_info字段
   */
  public static final boolean baseInfo(String str) {
    if (str.contains("account")) {
      return true;
    } else if (str.contains("password")) {
      return true;
    } else if (str.contains("user_name")) {
      return true;
    } else if (str.contains("identity")) {
      return true;
    } else if (str.contains("real_name")) {
      return true;
    } else if (str.contains("email")) {
      return true;
    } else if (str.contains("phone_number")) {
      return true;
    } else if (str.contains("address")) {
      return true;
    } else if (str.contains("status")) {
      return true;
    } else if (str.contains("freezing_time")) {
      return true;
    } else if (str.contains("last_login_time")) {
      return true;
    }
    return false;
  }

  /**
   * 判断是否为tb_extra_user_info字段
   */
  public static final boolean extraInfo(String str) {
    if (str.contains("address")) {
      return true;
    } else if (str.contains("create_time")) {
      return true;
    } else if (str.contains("introduction")) {
      return true;
    } else if (str.contains("age")) {
      return true;
    } else if (str.contains("birth_date")) {
      return true;
    } else if (str.contains("avatar")) {
      return true;
    } else if (str.contains("sex")) {
      return true;
    } else if (str.contains("career")) {
      return true;
    } else if (str.contains("income")) {
      return true;
    } else if (str.contains("constellation")) {
      return true;
    } else if (str.contains("height")) {
      return true;
    } else if (str.contains("body_weight")) {
      return true;
    }
    return false;
  }

}
