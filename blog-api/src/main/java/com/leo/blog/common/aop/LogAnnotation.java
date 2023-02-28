package com.leo.blog.common.aop;

import java.lang.annotation.*;

/**
 * 自定义注解  日志注解
 *      加上这三个参数：@Target({ElementType.TYPE,ElementType.METHOD})
 *                  @Retention(RetentionPolicy.RUNTIME)
 *                  @Documented
 */
//Type 代表可以放在类上面 Method代表可以放在方法上
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default  "";

    String operator() default "";
}
