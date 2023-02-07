package com.baizhi.mall.commons.api.exception;

import com.baizhi.mall.commons.api.ResponseStatus;


/**
 * 继承ResponseStatus达到方法复用
 * @author 15241
 */
public class BaseException extends RuntimeException implements ResponseStatus {

    protected ResponseStatus errorStatus;

    public BaseException(ResponseStatus errorStatus){
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public BaseException(ResponseStatus responseStatus,String message){
        super(message == null || message.isEmpty()? responseStatus.getMessage() : message);
    }



    @Override
    public int getCode() {
        return 0;
    }
}
