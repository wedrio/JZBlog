package com.weirdo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: xiaoli
 * @Date: 2022/11/25 --16:31
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.weirdo.mapper")
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class,args);
    }
}
