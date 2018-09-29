package com.ice.sub.library.migrate.mq.configuration;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/28 15:27
 */
@Component
public class JdbcConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource(DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
        .build();
  }

  @Bean
  public JdbcTemplate smsJdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
