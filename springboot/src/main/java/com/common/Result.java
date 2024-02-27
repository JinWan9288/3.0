package com.common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 有参构造
@NoArgsConstructor // 无参构造
public class Result {// 接口统一的返回包装类
    private String code; // 向前端发送,告诉前端这次请求是成功还是失败, 等于0 表示成功, 不等于0 都是失败
    private String msg; // 封装失败原因
    private Object data; // 后台需要携带的数据

    public static Result success(){ // 无参成功
        return new Result(Constants.CODE_200,"",null);
    }

    public static Result success(Object data) {  // 传入数据成功 有参成功
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result error(String code,String msg){ // 自定义状态码 错误信息
        return new Result(code,msg,null);
    }

    public static Result error(){ // 系统错误
        return new Result(Constants.CODE_500,"系统错误",null);
    }
}
