package com.ice.sub.library.check.check;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/29 16:22
 */
@Component
public class StartUpCenter {

  @Autowired
  private ThreadPoolTaskExecutor threadExecutor;

  @Autowired
  private CheckSqlService checkSqlService;

  @PostConstruct
  public void execute() {
    //线程中启动迁移程序
    threadExecutor.execute(() -> {
      checkSqlService.checkSql();
    });
  }

}
