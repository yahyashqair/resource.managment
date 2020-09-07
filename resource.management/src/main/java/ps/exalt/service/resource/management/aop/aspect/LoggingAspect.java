package ps.exalt.service.resource.management.aop.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ps.exalt.service.resource.management.aop.annotation.TimeTracking;

import java.util.Date;
import java.util.logging.Logger;

@Aspect
@Service
public class LoggingAspect {

    @Around("@annotation(ps.exalt.service.resource.management.aop.annotation.TimeTracking)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(Thread.currentThread().getName() + " executed in " + executionTime + "ms");
        return proceed;
    }

}
