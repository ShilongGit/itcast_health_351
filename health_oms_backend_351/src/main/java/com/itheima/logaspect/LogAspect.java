package com.itheima.logaspect;

import java.lang.reflect.Method;
import java.util.Date;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.pojo.SystemLog;
import com.itheima.service.MySystemLogService;
import com.itheima.utils.Date2Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {

    @Reference
    MySystemLogService mySystemLogService;

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.itheima.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 前置通知 用于拦截操作，在方法返回后执行
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        SystemLog systemLog = handleLog(joinPoint, null);
        mySystemLogService.insertSystemLog(systemLog);
    }

    /**
     * 拦截异常操作，有异常时执行
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        SystemLog systemLog = handleLog(joinPoint, e);
        mySystemLogService.insertSystemLog(systemLog);
    }

    private SystemLog handleLog(JoinPoint joinPoint, Exception e) {
        SystemLog systemLog = new SystemLog();

        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return systemLog;
            }

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String username = (String) request.getSession().getAttribute("user");
            // 获得方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String operationType = controllerLog.operationType();
            String operationName = controllerLog.operationName();
            String actionTime = Date2Utils.parseDate2String(new Date(), "yyyy-MM-dd HH:mm:ss");
            //打印日志，如有需要还可以存入数据库
            log.info(">>>>>>>>>>>>>具体操作：{}",operationName);
            log.info(">>>>>>>>>>>>>操作类型：{}",operationType);
            log.info(">>>>>>>>>>>>>类名：{}",className);
            log.info(">>>>>>>>>>>>>方法名：{}",methodName);
            log.info(">>>>>>>>>>>>>用户名：{}",username);
            log.info(">>>>>>>>>>>>>操作时间：{}",actionTime);


            systemLog.setActionTime(actionTime);
            systemLog.setClassName(className);
            systemLog.setMethodName(methodName);
            systemLog.setName(username);
            systemLog.setOperationName(operationName);
            systemLog.setOperationType(operationType);

            return systemLog;
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
            systemLog.setStatus("操作失败");
            return systemLog;
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private static Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

}
