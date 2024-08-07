package example.news.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j

public class LoggingAspect {

    @Before("@annotation(Loggable)")
    public void logBefore(JoinPoint joinPoint){
        log.info("Before execution of:{}", joinPoint.getSignature().getName());
    }
    @After("@annotation(Loggable)")
    public void logAfter(JoinPoint joinPoint){
        log.info("After execution of:{}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@annotation(Loggable)", returning = "result")
    public void logAfterReturning (JoinPoint joinPoint, Object result){
        log.info("After returning of:{}", joinPoint.getSignature().getName(), result);
    }
    @AfterThrowing(pointcut = "@annotation(Loggable)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception){
        log.info("After throwing from:{}", joinPoint.getSignature().getName(), exception);
    }
    @Around("@annotation(Loggable)")
    public Object logArround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        log.info("Arround method:{} is called", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        log.info("Around method:{} is finished", proceedingJoinPoint.getSignature().getName());
        return result;
    }
}
