package com.sd.configuration;

import com.sd.advice.ParameterCheckingAdvice;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SD
 * @since 2017/7/2
 */
@Configuration
public class AopConfig {

    @Bean
    public Advice parameterCheckingAdvice() {
        return new ParameterCheckingAdvice();
    }

    @Bean
    public Advisor parameterCheckingAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * com.sd.controller.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, parameterCheckingAdvice());
    }
}
