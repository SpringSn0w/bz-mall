package com.baizhi.mall.commons.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 信息保留RUNTIME
@Target({ElementType.TYPE,ElementType.METHOD}) //只定义注解应用所在
public @interface ResultBody {
}
