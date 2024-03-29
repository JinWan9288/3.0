package com.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
@Configuration
@EnableOpenApi
public class SwaggerConfig {
//      创建API应用
//      apiInfo() 增加API相关信息
//      通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
//      指定扫描的包(controller)路径来定义指定要建立API的目录。
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("标准接口")
                .apiInfo(apiInfo("SpringBoot中使用Swagger3构建REST ful APIs", "2.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.controller")) // controller层 通过扫描这里来建立api目录
                .paths(PathSelectors.any())
                .build();
    }

//      创建该API的基本信息（这些基本信息会展现在文档页面中）
//      访问地址：http://ip:port/swagger-ui.html
//              http://localhost:9090/swagger-ui.html swagger1.0写法
//              http://localhost:9090/swagger-ui/index.html swagger3.0写法


    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description("更多请关注: https://blog.csdn.net")
//                .termsOfServiceUrl("https://blog.csdn.net/xqnode")
//                .contact(new Contact("xqnode", "https://blog.csdn.net/xqnode", "xiaqingweb@163.com"))
                .version(version)
                .build();
    }
}
