package com.ice.sub.library.web.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ice
 * @Date 2018/9/15 16:00
 */
@Configuration
@ConfigurationProperties(prefix = ProxyMybatisProperties.PREFIX)
public class ProxyMybatisProperties {

  public static final String PREFIX = "proxy.datasource";

  private String url;
  private String username;
  private String password;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
