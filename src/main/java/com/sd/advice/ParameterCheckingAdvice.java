package com.sd.advice;

import com.sd.annotation.CheckParam;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 参数检查Advice
 *
 * @author SD
 * @since 2017/7/2
 */
public class ParameterCheckingAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        // 获取每个参数上的所有注解
        Annotation[][] annotationsOfParams = method.getParameterAnnotations();

        // 获取参数类型
        Class<?>[] argTypes = method.getParameterTypes();
        for (int i = 0; i < objects.length; i++) {
            Annotation[] annotations = annotationsOfParams[i]; // 参数上的注解
            for (Annotation annotation : annotations) {
                if (annotation instanceof CheckParam) { // 处理带有CheckParam注解的参数
                    Object paramValue = objects[i];
                    CheckParam cp = (CheckParam) annotation;
                    if (cp.notNull() && paramValue == null) { // 是否要求该参数一定不能为null
                        throw new RuntimeException("Parameter should not be null.");
                    }

                    if (paramValue != null) { // 参数不为null时的后续检查
                        Class<?> paramType = argTypes[i];
                        if (cp.notEmpty()) { // 参数不能为empty的检查，目前主要检查String和Collection类型
                            if (paramType.isAssignableFrom(String.class) && ((String) paramValue).isEmpty()) {
                                // TODO custom your code here.
                                throw new RuntimeException("Parameter should not be empty.");
                            } else if (paramType.isAssignableFrom(Collection.class) && ((Collection) paramValue).isEmpty()) {
                                // TODO custom your code here.
                                throw new RuntimeException("Parameter should not be empty.");
                            }
                        }

                        // 参数的子属性empty检查，目前只能做到一层属性
                        String[] requiredNotEmptyFields = cp.notEmptyFields();
                        Set<String> confirmedNotEmptyFields = new HashSet<>(); // 通过empty检查的属性
                        for (String requiredNotEmptyField : requiredNotEmptyFields) {
                            // 反射获取属性
                            Field f = paramType.getDeclaredField(requiredNotEmptyField);
                            f.setAccessible(true);
                            Class<?> ft = f.getType();
                            Object fv = f.get(paramValue);
                            if (ft.isAssignableFrom(String.class) && (fv == null || ((String) fv).isEmpty())) {
                                // TODO custom your code here.
                                throw new RuntimeException("Parameter field should not be empty.");
                            } else if (ft.isAssignableFrom(Collection.class) && (fv == null || ((Collection) fv).isEmpty())) {
                                // TODO custom your code here.
                                throw new RuntimeException("Parameter field should not be empty.");
                            }
                            confirmedNotEmptyFields.add(requiredNotEmptyField);
                        }
                        // 参数的子属性null检查，目前只能做到一层属性
                        String[] requiredNotNullParams = cp.notNullFields();
                        for (String requiredNotNullParam : requiredNotNullParams) {
                            if (confirmedNotEmptyFields.contains(requiredNotNullParam)) { // 已经通过empty检查检查的属性，无需再进行null检查
                                continue;
                            }
                            Field f = paramType.getDeclaredField(requiredNotNullParam);
                            f.setAccessible(true);
                            if (f.get(paramValue) == null) {
                                // TODO custom your code here.
                                throw new RuntimeException("Parameter field should not be null.");
                            }
                        }
                    }
                    break; // 仅提供CheckParam注解处理
                }
            }
        }
    }
}
