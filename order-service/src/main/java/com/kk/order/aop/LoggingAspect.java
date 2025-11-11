package com.kk.order.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * @description thực hiện khi xảy ra lỗi ở tất cả method
     * @param joinPoint
     * @param ex
     */
//    @AfterThrowing(pointcut =
//            "execution(* com.kk.order.backend.service..*(..)) " +
//                    "|| execution(* com.bitsco.backend.repository..*(..))"
//            , throwing = "ex")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
//        logger.error("⏳ Phương trình lỗi: " + joinPoint.getSignature());
//        logger.error("❌ Message lỗi " + ex.getMessage());
//    }

    /**
     * @description thực hiện đo lường thời gian api thực hiện
     * @param joinPoint
     * @return Object
     * @throws Throwable
     */
    @Around("execution(* com.kk.order.controller..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("⏳ API thực hiện: {}", joinPoint.getSignature());

        logger.info("📌 Dữ liệu đầu vào: ");
        for (Object arg: joinPoint.getArgs()) {
            logger.info("➡ {}", arg);
        }

        Object result = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        logger.info("⏱ thời gian thực hiện: {}ms", elapsedTime);

        return result;
    }
}
