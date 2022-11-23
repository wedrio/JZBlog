package com.weirdo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author: xiaoli
 * @Date: 2022/11/5 --15:42
 * @Description:
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("com.weirdo.mapper")
public class JZBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(JZBlogApplication.class,args);
    }
}
