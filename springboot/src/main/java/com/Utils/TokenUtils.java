package com.Utils;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.entity.User;
import com.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component  // 首先将TokenUtils注册为springboot的一个bean, 再通过@Resource引入IUserService
public class TokenUtils {

    private static IUserService StaticIUserService; // 静态对象对应静态方法
    @Resource
    private IUserService iUserService;

    @PostConstruct  // 在后台项目启动的时候就把IUserService对象赋给静态对象StaticIUserService
    public void setiUserService(){
        StaticIUserService = iUserService;
    }

    public static String getToken(String userId,String sign){ // 生成token
        return JWT.create().withAudience(userId) // userId作为载荷保存到token里   withAudience携带载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))  // 有效期从当前到两个小时以后
                .sign(Algorithm.HMAC256(sign));  //以password作为token秘钥
    }

    // 静态方法不能调用非静态对象
    public static User getCurrentUser(){ // 获取当前登录的用户信息
            try {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                // 通过RequestContextHolder 获取当前的 请求 request
                String token = request.getHeader("token"); // 获取request 的 token 还是 header
                if (StrUtil.isNotBlank(token)) {  // token存在不为空
                    String userId = JWT.decode(token).getAudience().get(0); // 解析token
                    return StaticIUserService.getById(Integer.valueOf(userId));
                }
            }catch (Exception exception){
                return null;
            }
        return null;
    }
}
