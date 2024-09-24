package com.taskable.backend.exception_handling;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotFoundOnNullAspect {

    @Around("@annotation(notFoundOnNull)")
    public Object throwNotFoundOnNull(ProceedingJoinPoint joinPoint, NotFoundOnNull notFoundOnNull) throws Throwable {
        Object result = joinPoint.proceed();

        if (result == null) {
            String message = notFoundOnNull.message();
            throw new ResourceNotFoundException(message);
        }

        return result;
    }
}
