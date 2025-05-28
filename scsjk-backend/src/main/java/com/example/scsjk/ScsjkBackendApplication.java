package com.example.scsjk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.scsjk.mapper")
//@ComponentScan(basePackages = {
//    "com.example.scsjk.service",
//    "com.example.scsjk.config",
//    "com.example.scsjk.controller"  // 添加controller包
//})

public class ScsjkBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScsjkBackendApplication.class, args);
    }

}
