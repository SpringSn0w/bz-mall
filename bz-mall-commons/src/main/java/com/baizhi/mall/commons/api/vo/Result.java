package com.baizhi.mall.commons.api.vo;


import com.baizhi.mall.commons.api.ResponseStatus;
import com.baizhi.mall.commons.api.constant.ResponseStatusEnum;

public class Result<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //第1种：直接暴露builder 典型用法：builder().code(200).message("success").data(dataObj).build();
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    //第2种：通过status接收ResponseStatus后续只需要添加data再build就可以：status(responseStatus).build(data
    public static <T> Builder<T> status(ResponseStatus status) {
        return Result.<T>builder().status(status);
    }

    //第3种：提供开发最常见的2种状态的简化方式ok和error
    public static <T> Result<T> ok() {
        return Result.ok((T) null);
    }

    public static <T> Result<T> ok(T data) {
        return Result.<T>status(ResponseStatusEnum.SUCCESS).data(data).build();
    }

    public static <T> Builder<T> ok(String message) {
        return Result.<T>builder().code(ResponseStatusEnum.SUCCESS.getCode()).message(message);
    }

    public static <T> Result<T> error() {
        return Result.<T>status(ResponseStatusEnum.FAILED).build();
    }

    public static <T> Result<T> error(String message){
        return Result.<T>builder().code(ResponseStatusEnum.FAILED.getCode()).message(message).build();
    }

    /**
     * 构建者模式:要构建的类相同属性
     * */
    public static class Builder<T> {
        private int code;
        private String message;
        private T data;


        public Builder<T> code(int code){
            this.code = code;
            return this;
        }

        public Builder<T> message(String message){
            this.message = message;
            return this;
        }

        public Builder<T> data(T data){
            this.data = data;
            return this;
        }

        public Result<T> build(){
            return new Result<>(code,message,data);
        }

        public Builder<T> status(ResponseStatus responseStatus){
            this.code = responseStatus.getCode();
            this.message = responseStatus.getMessage();
            return this;
        }

        public Result<T>  build(T data){
            this.data = data;
            return build();
        }
    }
}
