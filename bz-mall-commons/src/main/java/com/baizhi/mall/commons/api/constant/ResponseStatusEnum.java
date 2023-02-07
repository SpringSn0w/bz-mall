package com.baizhi.mall.commons.api.constant;

import com.baizhi.mall.commons.api.ResponseStatus;

/**
 * @author 15241
 */

public enum ResponseStatusEnum implements ResponseStatus {
    SUCCESS(200, "成功"),
    FAILED(500,"失败"),
    BAD_REQUEST(400, "请求参数有误"),
    UNAUTHORIZED(401, "认证失败"),
    FORBIDDEN(403,"权限不足"),
    NOT_FOUND(404, "接口不存在"),
    INTERNAL_SERVER_ERROR(500, "系统繁忙"),
    METHOD_NOT_ALLOWED(405,"方法不被允许");

    private int code;
    private String message;

    ResponseStatusEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
