package com.exception;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {    // 自定义异常, 继承RuntimeException,
    private String code; // 状态码

    public ServiceException(String code, String msg) {
        super(msg); // 在构造函数中调用父类的构造函数 必须是构造函数主体的第一条语句
        this.code=code; // 返回给前台错误编码
    }

}
