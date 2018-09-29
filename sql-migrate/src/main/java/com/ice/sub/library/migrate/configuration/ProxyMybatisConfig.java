package com.ice.sub.library.migrate.configuration;

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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ice
 * @Date 2018/9/15 16:22
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.ice.sub.library.migrate.dao.proxy", sqlSessionTemplateRef = "proxySqlSessionTemplate")
public class ProxyMybatisConfig {

  private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
  @Autowired
  private ProxyMybatisProperties proxyMybatisProperties;

  @Autowired
  private DataSourceProperties properties;

  @Primary
  @Bean("proxyDataSource")
  public DataSource proxyDataSource() {
    DruidDataSource ds = new DruidDataSource();
    logger.debug("DruidDataSource开始连接数据源 proxy...");
    ds.setDriverClassName(this.properties.getDriverClassName());
    ds.setUrl(proxyMybatisProperties.getUrl());
    ds.setUsername(proxyMybatisProperties.getUsername());
    ds.setPassword(proxyMybatisProperties.getPassword());
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

  @Primary
  @Bean(name = "proxySqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Qualifier("proxyDataSource") DataSource bosDataSource)
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

  @Primary
  @Bean(name = "proxySqlSessionTemplate")
  public SqlSessionTemplate sqlSessionTemplate(
      @Qualifier("proxySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Primary
  @Bean(name = "annotationDrivenTransactionManager")
  public PlatformTransactionManager annotationDrivenTransactionManager(
      @Qualifier("proxyDataSource") DataSource bosDataSource) {
    return new DataSourceTransactionManager(bosDataSource);
  }
}
