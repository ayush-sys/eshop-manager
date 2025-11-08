package com.example.eshop.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    // Pointcut for all methods inside the controller package
    @Pointcut("within(com.example.SpringMongo.controller..*)")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logControllerCall(ProceedingJoinPoint joinPoint) throws Throwable {

        // Get HTTP request details
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs != null ? attrs.getRequest() : null;

        String url = request != null ? request.getRequestURI() : "N/A";
        String httpMethod = request != null ? request.getMethod() : "N/A";

        // Get method details & parameters
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName(); // Only method name
        Object[] args = joinPoint.getArgs();

        long startTime = System.currentTimeMillis();
        Object result;

        try {
            result = joinPoint.proceed(); // Execute the controller method
        } finally {
            long timeTaken = System.currentTimeMillis() - startTime;

            log.info("""
                Controller call:
                URL        : {}
                HTTP Method: {}
                Method     : {}
                Params     : {}
                Result     : {}
                Time Taken : {} ms
                """,
                    url, httpMethod, methodName, Arrays.toString(args), joinPoint.proceed(), timeTaken
            );
        }

        return result;
    }
}