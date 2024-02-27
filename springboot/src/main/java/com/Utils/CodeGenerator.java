package com.Utils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import java.util.Collections;

// mp代码生成器  在使用前一定要将代码备份
public class CodeGenerator {
    public static void main(String args[]){
        generator();
    }
    private static void generator(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/wms?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8", "root", "qweasd123")
                .globalConfig(builder -> {
                    builder.author("root") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\Users\\lenovo\\Desktop\\project\\src\\main\\java"); // 指定输出目录 绝对路径 输出到每一个包里
                })
                .packageConfig(builder -> {
                    builder.parent("com.springboot") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "D:\\Users\\lenovo\\Desktop\\project\\springboot\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("user") // 设置需要生成的表名 数据库表
                            .addTablePrefix("t_","sys_"); // 设置过滤表前缀 需要被忽略的属性前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
