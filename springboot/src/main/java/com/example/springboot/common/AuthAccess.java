package com.example.springboot.common;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 添加此注解的controller不会被拦截，无需token验证
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
}