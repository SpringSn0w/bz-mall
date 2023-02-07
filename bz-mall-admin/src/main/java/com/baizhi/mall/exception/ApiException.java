package com.baizhi.mall.exception;

import com.baizhi.mall.commons.api.ResponseStatus;
import com.baizhi.mall.commons.api.exception.BaseException;

public class ApiException extends BaseException {

    public ApiException(ResponseStatus errorStatus) {
        super(errorStatus);
    }

    public ApiException(ResponseStatus responseStatus, String message) {
        super(responseStatus, message);
    }
}
