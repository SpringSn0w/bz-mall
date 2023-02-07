package com.baizhi.mall.commons.api.valid.validator;

import com.baizhi.mall.commons.api.valid.annotation.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 泛型A 配套的注解类型：EnumValue
 * 泛型T 约束注解作用于那个属性，那个属性的类型  Integer
 *
 * @author 15241
 */
public class EnumValueConstraintValidator implements ConstraintValidator<EnumValue,Integer> {
    private String[] values;
    @Override
    // 初始化方法  可以获取到注解的属性值
    public void initialize(EnumValue constraintAnnotation) {
        //values 获取到注解在使用时，允许的值的列表
        this.values = constraintAnnotation.value();
    }

    @Override
    /** 校验方法   value表示需要校验的值  context 约束校验上下文对象（基本没用）*/
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
//        判断 value  在不在values数组中，如果在则通过校验返回true，如果不再则不通过返回false
        for (String v : values) {
            if (v.equals(String.valueOf(value))) {
                return true;
            }
        }
        return false;
    }
}
