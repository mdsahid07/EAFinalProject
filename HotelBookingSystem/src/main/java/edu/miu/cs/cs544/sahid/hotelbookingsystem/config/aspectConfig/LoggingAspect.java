package edu.miu.cs.cs544.sahid.hotelbookingsystem.config.aspectConfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Log after reservation creation
    @AfterReturning(pointcut = "execution(* edu.miu.cs.cs544.sahid.hotelbookingsystem.service.ReservationService.createReservationsWithPayment(..))", returning = "result")
    public void logReservationCreation(JoinPoint joinPoint, Object result) {
        logger.info("Reservation created successfully. Details: {}", result);
    }

    // Log around payment processing
    @Around("execution(* edu.miu.cs.cs544.sahid.hotelbookingsystem.service.PaymentService.processPayment(..))")
    public Object logPaymentProcessing(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Starting payment processing...");
        Object result = joinPoint.proceed(); // Proceed with the original method
        logger.info("Payment processed successfully. Result: {}", result);
        return result;
    }

    @Before("execution(* edu.miu.cs.cs544.sahid.hotelbookingsystem.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("Executing method: " + joinPoint.getSignature().getName());
    }

    // Advice to log execution time of methods
    @Around("execution(* edu.miu.cs.cs544.sahid.hotelbookingsystem.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Method " + joinPoint.getSignature().getName() + " executed in " + elapsedTime + " ms");
        return result;
    }

    // Advice to log exceptions
    @AfterThrowing(pointcut = "execution(* edu.miu.cs.cs544.sahid.hotelbookingsystem.service.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        System.out.println("Method " + joinPoint.getSignature().getName() + " threw exception: " + ex.getMessage());
    }
}

