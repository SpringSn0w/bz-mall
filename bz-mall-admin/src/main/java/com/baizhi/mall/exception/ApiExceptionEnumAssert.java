package com.baizhi.mall.exception;

import com.baizhi.mall.commons.api.exception.ResponseStatusAssertAdapter;

/**
 * new ApiExceptionEnumAssert().check(count,"")
 *
 * @author 15241
 */
public enum ApiExceptionEnumAssert implements ResponseStatusAssertAdapter<ApiException> {
    CREAT(500,"创建失败"){
        @Override
        public boolean condition(Object[] os) {
            return os.length != 1 || Integer.valueOf((Integer) os[0]) <= 0;
        }
    },
    DELETE_BY_ID(500,"删除失败"){
        @Override
        public boolean condition(Object[] os) {
            return os.length != 1 || Integer.valueOf((Integer) os[0]) <= 0;
        }
    },
    DELETE_BY_EXAMPLE(501,"批量删除失败"){
        @Override
        public boolean condition(Object[] os) {
            return os.length != 1 || Integer.valueOf((Integer) os[0]) <= 0;
        }
    },
    UPDATE_BY_ID(500,"更新失败"){
        @Override
        public boolean condition(Object[] os) {
            return os.length != 1 || Integer.valueOf((Integer) os[0]) <= 0;
        }
    },
    UPDATE_BY_EXAMPLE(501,"批量更新失败"){
        @Override
        public boolean condition(Object[] os) {
            return os.length != 1 || Integer.valueOf((Integer) os[0]) <= 0;
        }
    };
    int code;
    String message;

    ApiExceptionEnumAssert(int code,String message){
        this.code = code;
        this.message = message;
    }
    /**
     * 异常对象
     *
     * @return
     */
    @Override
    public Class<ApiException> exceptionType() {
        return ApiException.class;
    }

    @Override
    public boolean condition(Object[] os) {
        return os.length != 1 || Integer.valueOf((Integer) os[0]) <= 0;
    }


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
