package com.keinye.spring.common.struct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.keinye.spring.common.MyError;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 200)
public class ResultGuardAspect {
    @Around(value = "(@within(com.keinye.spring.common.struct.ResultGuard) || @annotation(com.keinye.spring.common.struct.ResultGuard)) && execution(com.keinye.spring.common.struct.Result *(..))")
    public Object wrapException2Result(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (ResultException ex) {
            return Result.of(ex);
        } catch (Exception ex) {
            return Result.error(MyError.ERROR_UNKNOWN)
                    .description(ex.getMessage())
                    .errorData(ex.getCause());
        }
    }
}
