package com.baizhi.mall.commons.api.vo;

public class AllVO<T> {

    private Integer code;

    private T data;

    private String message;


    public AllVO<T> ok(T data){
        this.code = 200;
        this.message = "成功";
        this.data = data;
        return this;

    }
    public AllVO() {
    }

    public AllVO(Integer code) {
        this.code = code;
    }

    public AllVO(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public AllVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public AllVO(Integer code, String message, T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
