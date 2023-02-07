package com.baizhi.mall.commons.api.exception;

import com.baizhi.mall.commons.api.ResponseStatus;

/**
 * 这是一个适配ResponseStatus为Assert的类，
 * 继承此类的子类，只需要重写getCode、getMessage()、exceptionType()和condition()即可
 * 建议子类使用enum，这样每定义一个枚举值就是一种类型的异常
 * @author 15241
 * @param <T> 异常类型
 */
public interface ResponseStatusAssertAdapter<T extends BaseException> extends Assert<T>,ResponseStatus{
    @Override
    default T newException(String message){
        try {
            // 反射获取创建异常对象
            return exceptionType().getDeclaredConstructor(ResponseStatus.class,String.class)
                    .newInstance(this,message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 异常对象
     * @return
     */
    Class<T> exceptionType();

    @Override
    default void check(Object[] obj,String message){
        if (condition(obj)){
            throw newException(message);
        }
    }

    boolean condition(Object[] os);
}
