package ps.exalt.service.resource.management.aop.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ps.exalt.service.resource.management.service.SemaphoreService;

@Aspect
@Component
@Log4j2
public class LockingAspect {

    @Autowired
    SemaphoreService semaphoreService;

    @Around("@annotation(ps.exalt.service.resource.management.aop.annotation.LockThread)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("ask for r/w in the database");
        this.semaphoreService.acquire();
        Object proceed = joinPoint.proceed();
        this.semaphoreService.release();
        log.info("r/w in the database are done");
        return proceed;
    }


}
