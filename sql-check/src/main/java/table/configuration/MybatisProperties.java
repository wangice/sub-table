package table.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author:ice
 * @Date: 2018/6/15 17:11
 */
@Configuration
@ConfigurationProperties(prefix = MybatisProperties.PREFIX)
public class MybatisProperties {

  public static final String PREFIX = "bos.datasource";

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
