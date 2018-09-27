package com.ice.sub.library.table.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author:ice
 * @Date: 2018/6/8 15:04
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.ice.sub.library.table.dao.mybatis", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfig {

  private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);


  @Autowired
  private MybatisProperties bosMybatisProperties;

  @Autowired
  private DataSourceProperties properties;

  @Bean("dataSource")
  public DataSource bosDataSource() {
    DruidDataSource ds = new DruidDataSource();
    logger.debug("DruidDataSource开始连接数据源...");
    ds.setDriverClassName(this.properties.getDriverClassName());
    ds.setUrl(bosMybatisProperties.getUrl());
    ds.setUsername(bosMybatisProperties.getUsername());
    ds.setPassword(bosMybatisProperties.getPassword());
    ds.setMaxActive(this.properties.getMaxActive());
    ds.setMaxWait(this.properties.getMaxWait());
    ds.setInitialSize(this.properties.getInitialSize());
    ds.setValidationQuery(this.properties.getValidationQuery());
    ds.setPoolPreparedStatements(this.properties.isPoolPreparedStatements());
    ds.setMaxPoolPreparedStatementPerConnectionSize(
        this.properties.getMaxPoolPreparedStatementPerConnectionSize());
    ds.setTestWhileIdle(true);
    ds.setTestOnBorrow(false);
    ds.setTestOnReturn(false);
    try {
      ds.setFilters(this.properties.getFilters());
    } catch (SQLException e) {
      logger.error("发生异常：", e);
    }
    ds.setConnectionProperties(this.properties.getConnectionProperties());
    return ds;
  }


  @Bean(name = "sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource bosDataSource)
      throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(bosDataSource);
    sqlSessionFactoryBean.setTypeAliasesPackage("com.ice.sub.library.table.entities");
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    try {
      sqlSessionFactoryBean
          .setMapperLocations(resolver.getResources("classpath*:mapper/*Mapper.xml"));
      SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
      if (sqlSessionFactory != null) {
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
      }
      return sqlSessionFactory;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Bean(name = "sqlSessionTemplate")
  public SqlSessionTemplate sqlSessionTemplate(
      @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Bean(name = "annotationDrivenTransactionManager")
  public PlatformTransactionManager annotationDrivenTransactionManager(
      @Qualifier("dataSource") DataSource bosDataSource) {
    return new DataSourceTransactionManager(bosDataSource);
  }
}
