package ps.exalt.service.resource.management.aop.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import ps.exalt.service.resource.management.service.SemaphoreService;

@Aspect
@Service
public class LockingAspect {

    private final SemaphoreService semaphoreService;

    public LockingAspect(SemaphoreService semaphoreService) {
        this.semaphoreService = semaphoreService;
    }

    @Around("@annotation(ps.exalt.service.resource.management.aop.annotation.Synchronized)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("require to read from the database ");
        this.semaphoreService.acquire();
        Object proceed = joinPoint.proceed();
        this.semaphoreService.release();
        System.out.println("release to read from the database ");
        return proceed;
    }


}
