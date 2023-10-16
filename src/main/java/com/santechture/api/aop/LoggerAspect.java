package com.santechture.api.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ahmed Mubarak
 */
//@Aspect
//@Configuration
public class LoggerAspect {
//
//    static final Logger LOGGER = Logger.getLogger(LoggerAspect.class);
//    private ObjectMapper mapper = new ObjectMapper();
//
//
//    @Before("execution( * com.santechture.api.*..*.*(..))")
//    public void before(JoinPoint joinPoint) throws JsonProcessingException {
//
//        LOGGER.info("Enter : " + joinPoint.getSignature());
//        try {
//            LOGGER.info("Parameters data : " + mapper.writeValueAsString(joinPoint.getArgs()));
//
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage());
//        }
//    }
//
//
//    @After("execution( * com.santechture.api.*..*.*(..))")
//    public void after(JoinPoint joinPoint) throws JsonProcessingException {
//
//        LOGGER.info("Exit from: " + joinPoint.getSignature());
//
//
//    }
//
//    @AfterReturning(pointcut = "execution( * com.santechture.api.*..*.*(..))", returning = "returnedData")
//    public void afterReturning(Object returnedData) throws JsonProcessingException {
//
//        LOGGER.info("Returned data : " + mapper.writeValueAsString(returnedData));
//
//    }
//

}
