package com.leo.blog.common.cache;

import java.lang.annotation.*;

/* 缓存切点 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;  //过期时间

    String name() default "";  //缓存标识 key : value
}
