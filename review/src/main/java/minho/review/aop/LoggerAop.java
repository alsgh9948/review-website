package minho.review.aop;

import minho.review.user.controller.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAop {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Before("execution(* minho.review..*Controller.*(..))")
    public void logBefore(JoinPoint joinPoint) throws Throwable{
        logger.info("info");
    }
}
