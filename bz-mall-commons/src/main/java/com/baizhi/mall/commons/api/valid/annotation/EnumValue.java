package com.baizhi.mall.commons.api.valid.annotation;

import com.baizhi.mall.commons.api.valid.validator.EnumValueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
   Student{
     @EnumValue(value={"0","2","4"},message="异常信息")
     Integer status;
   }
*/

/**
 * @author 15241
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EnumValueConstraintValidator.class)
public @interface EnumValue{
    // 定义一个value属性保存 允许值的列表
    String[] value() default{};

    String message() default "value is not found";

    Class<?>[] group() default {};

    Class<? extends Payload>[] payload() default {};
}
