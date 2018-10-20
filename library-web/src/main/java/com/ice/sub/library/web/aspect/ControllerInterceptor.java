package com.ice.sub.library.web.aspect;

import com.ice.brother.house.Misc;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 切面,请求过程的拦截输出日志和异常的捕获
 *
 * @author:ice
 * @Date: 2018/7/10 20:07
 */
@Aspect
@Component
public class ControllerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

  @Pointcut("execution(* com.ice.sub.library.web.controller.*..*(..))")
  public void methodPointcut() {

  }

  @AfterThrowing(pointcut = "methodPointcut()", throwing = "e")
  public void doThrowingAfterMethodPointcut(JoinPoint jp, Throwable e) {
    logger.error("exception: {}", Misc.trace(e));
  }

  @Around("methodPointcut()")
  public Object aroundMethodPointcut(ProceedingJoinPoint pjp) {
    Object result = null;
    long startTime = System.currentTimeMillis();
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String method = request.getMethod();
    String uri = request.getRequestURI();
    Map<String, String[]> parameterMap = request.getParameterMap();
    logger.info("request start,uri: {},method: {},req: {}", uri, method, parameterMap);
    long endTime;
    //

    //获取参数
    Map<String, String> paramMap = new HashMap<>();
    Enumeration<String> paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String paramName = paramNames.nextElement();
      String valueStr = request.getParameter(paramName);
      paramMap.put(paramName, valueStr);
    }

    //
    try {
      result = pjp.proceed();
      endTime = System.currentTimeMillis();
      logger.info("request end,elap: {}ms,uri: {},method: {},rsp: {}", (endTime - startTime), uri,
          method, Misc.obj2json(result));

    } catch (Throwable throwable) {
      endTime = System.currentTimeMillis();
      logger.error("request end,elap: {}ms,uri: {},method: {},err:{}", (endTime - startTime), uri,
          method, Misc.trace(throwable));
    }
    return result;
  }
}
