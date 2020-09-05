package ps.exalt.service.resource.management.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ps.exalt.service.resource.management.service.SemaphoreService;

@Aspect
@Component
public class LockingAspect {

    @Autowired
    SemaphoreService semaphoreService;

    @Around("@annotation(ps.exalt.service.resource.management.aop.annotation.LockThread)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }


}
