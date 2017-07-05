package com.sd.annotation;

import java.lang.annotation.*;

/**
 * 参数检查注解
 *
 * @author SD
 * @since 2017/7/2
 */
@Documented
@Inherited
@Target(ElementType.PARAMETER) // 只允许在方法参数上使用
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckParam {
    boolean notNull() default true; // 是否不允许为null

    boolean notEmpty() default false; // 是否不允许为empty（如String、Collection）

    String[] notNullFields() default {}; // 不允许为null的属性（主要用于DTO传递参数）

    String[] notEmptyFields() default {}; // 不允许为empty的属性（主要用于DTO传递参数）
}
