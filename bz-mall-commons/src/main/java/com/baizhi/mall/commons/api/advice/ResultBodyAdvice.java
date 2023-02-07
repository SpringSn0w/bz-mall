package com.baizhi.mall.commons.api.advice;


import com.baizhi.mall.commons.api.annotation.IgnoreResult;
import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.commons.api.vo.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResultBodyAdvice<T> implements ResponseBodyAdvice {

    private final static ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    @Override
    public boolean supports(MethodParameter returnType, Class aClass) {
        // 判断是否需要自动包装
        // 判断返回值类型是否是Result,不需要包装
        if (returnType.getParameterType().isAssignableFrom(Result.class)) return false;
        // 判断controller类或者服务方法上有没有IgnoreResult注解，不需要包装
        if (returnType.hasMethodAnnotation(IgnoreResult.class) || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), IgnoreResult.class)) return false;
        // 判断controller类或者服务方法上有没有ResultBody注解，需要包装
        return returnType.hasMethodAnnotation(ResultBody.class) || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(),ResultBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 核心包装流程
        // 特殊情况，如果是String类型，包装后还得是String
        if (body instanceof String){
            Result<Object> result = Result.ok(body);
            try {
                return objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return Result.ok(body);
    }
}
