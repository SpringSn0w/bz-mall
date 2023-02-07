package com.baizhi.mall.exception;

import com.baizhi.mall.commons.api.vo.Result;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

/**
 * 全局异常处理
 * @author 15241
 */
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ApiException.class)
    public Result handleApiException(ApiException e){
        e.printStackTrace();
        return Result.status(e).build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException ex){
        String parameterName = ex.getParameterName();
        String msg = "缺失必要的请求参数:"+parameterName;
        return Result.error(msg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        String message = ex.getMessage();
        return Result.error(message);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return handleBindingResult(bindingResult);
    }


    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException ex){
        return handleBindingResult(ex);
    }

    private Result handleBindingResult(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder("校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.deleteCharAt(sb.length()-1).toString();
        return Result.error(msg);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        String name = ex.getName();
        Object value = ex.getValue();
        String msg = name+"值:"+value+"不满足类型要求";
        return Result.error(msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public Result handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder("校验失败:");
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            Path propertyPath = constraintViolation.getPropertyPath();
            String messageTemplate = constraintViolation.getMessageTemplate();
            sb.append(propertyPath).append("：").append(messageTemplate).append(", ");
        }
        String msg = sb.deleteCharAt(sb.length()-1).toString();
        return Result.error(msg);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
