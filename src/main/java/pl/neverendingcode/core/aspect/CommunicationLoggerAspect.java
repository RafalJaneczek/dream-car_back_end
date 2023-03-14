package pl.neverendingcode.core.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pl.neverendingcode.core.annotation.CommunicationLog;
import pl.neverendingcode.core.helper.JsonObjectMapper;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class CommunicationLoggerAspect {

    private final JsonObjectMapper jsonObjectMapper;

    @Around(value = "@annotation(communicationLog)")
    public Object printMethodLogs(ProceedingJoinPoint joinPoint, CommunicationLog communicationLog) throws Throwable {
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();

        if (!communicationLog.hasArguments()) {
            log.info("Process: {} started", methodName);
        } else {
            log.info("Process: {} started with arguments: {}", methodName, jsonObjectMapper.toJSON(joinPoint.getArgs()));
        }

        Object proceed = joinPoint.proceed();

        if (!communicationLog.returnsObject()) {
            log.info("Process: {} completed", methodName);
        } else {
            log.info("Process: {} completed with returned object: {}", methodName, jsonObjectMapper.toJSON(proceed));
        }

        return proceed;
    }

}