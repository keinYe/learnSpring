package com.keinye.spring.common.struct;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
public class ResultAspect {
    @AfterReturning(pointcut = "execution(com.keinye.spring.common.struct.Result *(..)) && (@within(org.springframework.transaction.annotation.Transactional) || @annotation(org.springframework.transaction.annotation.Transactional) || @within(javax.transaction.Transactional) || @annotation(javax.transaction.Transactional))", returning = "result")
    public Object rollbackSpringTransactionalOnError(Object result) {
        if (result instanceof Result
                && !((Result) result).isSuccess()
                && TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }
}
