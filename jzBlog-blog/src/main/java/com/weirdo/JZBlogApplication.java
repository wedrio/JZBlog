package com.weirdo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: xiaoli
 * @Date: 2022/11/5 --15:42
 * @Description:
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("com.weirdo.mapper")
@EnableScheduling
@EnableSwagger2
public class JZBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(JZBlogApplication.class,args);
    }
}
