package com.ice.sub.library.table.mq.dao;

import com.ice.brother.house.Misc;
import com.ice.sub.library.table.mq.entities.Columns;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/29 11:23
 */
@Component
public class UserInfoDao {

  private String TABLE_NAME = "tb_account";

  private String TABLE_SCHEMA = "bos";

  @Autowired
  private JdbcTemplate jdbcTemplate;


  public List<Columns> queryColumns() {
    String sql = Misc.printf2Str(
        "SELECT * FROM information_schema.COLUMNS WHERE table_name = '%s' AND table_schema = '%s' ",
        TABLE_NAME, TABLE_SCHEMA);
    RowMapper<Columns> rowMapper = new BeanPropertyRowMapper<>(Columns.class);
    return jdbcTemplate.query(sql, rowMapper);
  }
}
