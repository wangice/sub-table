package table;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author:ice
 * @Date: 2018/9/10 18:44
 */
@SpringBootApplication(exclude = {MybatisAutoConfiguration.class,
    DataSourceAutoConfiguration.class})
public class LibraryTableWebApplication {

  /**
   * 启动程序.
   */
  public static void main(String[] args) {
    SpringApplication.run(LibraryTableWebApplication.class, args);
  }
}
