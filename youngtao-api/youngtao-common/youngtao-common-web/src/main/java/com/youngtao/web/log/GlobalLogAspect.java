package com.youngtao.web.log;

import com.google.common.base.Stopwatch;
import com.youngtao.core.util.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ankoye@qq.com
 * @date 2020/12/09
 */
@Aspect
@Component
public class GlobalLogAspect {
    private final Logger log = LoggerFactory.getLogger(GlobalLogAspect.class);

    @Pointcut("execution(* com.youngtao..controller.*.*(..)) " +
            "|| execution(* com.youngtao..service.*.*(..)) " +
            "|| execution(* com.youngtao..mapper.*.*(..))" +
            "|| execution(* com.youngtao..listener.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String className = point.getSignature().getDeclaringType().getSimpleName();
        String methodName = point.getSignature().getName();
        try {
            Object result = point.proceed();
            if (stopwatch.isRunning()) {
                stopwatch.stop();
            }
            log.info("executed ms: {}, method: {}#{}, args: {}, result: {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), className, methodName, printObjs(point.getArgs()), printObjs(new Object[] {result}));
            return result;
        } catch (Throwable t) {
            if (stopwatch.isRunning()) {
                stopwatch.stop();
            }
            log.warn("executed ms: {}, method: {}#{}, args: {}, exception is {}: {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), className, methodName, printObjs(point.getArgs()), t.getClass().getName(), t.getMessage());
            throw t;
        }
    }

    private static String printObjs(Object[] objs) {
        List<Object> objects = new ArrayList<>();
        for (Object obj : objs) {
            if (obj instanceof byte[]) {
                objects.add("-byte[]-");
            } else {
                objects.add(obj);
            }
        }
        return JsonUtils.toJson(objects);
    }

}
