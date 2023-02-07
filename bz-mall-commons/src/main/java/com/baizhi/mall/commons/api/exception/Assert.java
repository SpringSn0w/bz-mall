package com.baizhi.mall.commons.api.exception;

public interface Assert<T extends BaseException>{
    /**
     * 用于创建异常
     * @param message 异常信息
     * @return
     */
    T newException(String message);

    void check(Object[] obj,String message);
    default void check(Object...obj){
        check(obj,null);
    }
}
