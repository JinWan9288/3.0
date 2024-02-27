package com.config;
import com.common.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**") // 拦截所有请求, 通过判断token是否合法来决定是否允许登录
                .excludePathPatterns("/user/login","/user/register","/user/export","/user/import","/file/**");
                                    //排除登录注册导入导出 这些接口都放行 登录等不需要token验证
    }

    @Bean // 注入拦截器
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}