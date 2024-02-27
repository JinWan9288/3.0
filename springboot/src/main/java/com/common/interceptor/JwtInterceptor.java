package com.common.interceptor;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.common.Constants;
import com.entity.User;
import com.exception.ServiceException;
import com.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// 不需要@Component new的拦截器 无法使用spring功能
public class JwtInterceptor implements HandlerInterceptor{ // 拦截器
    @Resource
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader("token"); // 从http请求中获取到token头部
        if(!(handler instanceof HandlerMethod)){ // 如果不是映射到方法直接通过  静态资源直接放行
            return true;
        }

        if(StrUtil.isBlank(token)){ // 执行认证 判断是不是空字符串
            throw new ServiceException(Constants.CODE_401, " 无token, 请重新登录 "); // token为空
        }

        String userId;
        try{  // 获取token中的user id
            userId = JWT.decode(token).getAudience().get(0);  //  在tokenUtils有withAudience 携带载荷 取出验证合法性
        }catch (JWTDecodeException jwtDecodeException){
            throw new ServiceException(Constants.CODE_401,"token验证失败, 请重新登录"); // token非法 没有id
        }

        //token合法, 根据token中的userid找数据库中是否存在id 查询数据库操作
        User user = userService.getById(userId);
        if (user == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在, 请重新登录");// token合法 但是id不存在
        }

        //  用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPasswords())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTDecodeException jwtDecodeException){
            throw new ServiceException(Constants.CODE_401,"密码错误, token验证失败, 请重新登录"); // token非法 有id passwords匹配错误
        }
        return true;
    }
}
