package com.sparta.trelloproject.common.aop;

import com.sparta.trelloproject.config.SlackAlertUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class AlertAspect {

    private final SlackAlertUtil alertUtil;

    @Around(("@annotation(com.sparta.trelloproject.common.annotation.SendAlert)"))
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            alertUtil.publishMessage("AopTest");
            return joinPoint.proceed();
        } finally {
            System.out.println("작동확인");
        }
    }
}