package pl.neverendingcode.core.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pl.neverendingcode.core.annotation.LogApiInfo;

import java.io.IOException;
import java.io.StringWriter;

@Slf4j
@Component
@Aspect
public class ApiLoggingAspect {

    @Around(value = "@annotation(logApiInfo)")
    public Object printMethodLogs(ProceedingJoinPoint joinPoint, LogApiInfo logApiInfo) throws Throwable {
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();

        if (!logApiInfo.hasArguments()) {
            log.info("Process: {} started", methodName);
        } else {
            log.info("Process: {} started with arguments: {}", methodName, toJSON(joinPoint.getArgs()));
        }

        Object proceed = joinPoint.proceed();

        if (!logApiInfo.returnsObject()) {
            log.info("Process: {} completed", methodName);
        } else {
            log.info("Process: {} completed with returned object: {}", methodName, toJSON(proceed));
        }

        return proceed;
    }

    private String toJSON(Object o) {
        StringWriter result = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(result, o);
        } catch (IOException e ) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return result.toString();
    }

}