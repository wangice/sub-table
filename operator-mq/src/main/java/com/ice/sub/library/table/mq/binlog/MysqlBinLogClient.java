package com.ice.sub.library.table.mq.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ice
 * @Date 2018/9/26 16:00
 */
@Configuration
public class MysqlBinLogClient {

  @Value("${bin.log.host}")
  private String host;
  @Value("${bin.log.port}")
  private int port;
  @Value("${bin.log.user}")
  private String user;
  @Value("${bin.log.pwd}")
  private String pwd;

  @Autowired
  private BinLogListener binLogListener;

  @Bean("binLogClient")
  public BinaryLogClient binLogClent() throws IOException, TimeoutException {
    BinaryLogClient client = new BinaryLogClient(host, port, user, pwd);
    client.registerEventListener(binLogListener);
    client.connect(10000);//创建新的线程
    return client;
  }

}
