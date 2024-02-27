package com.exception;
import com.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {  // 全局异常处理器
    @ExceptionHandler(ServiceException.class)   // 如果抛出的是ServiceException,则调用该方法
    @ResponseBody //

    public Result handle(ServiceException serviceException){ // 捕获异常 并包装成result对象
        return Result.error(serviceException.getCode(),serviceException.getMessage());
    }
}
