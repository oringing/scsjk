package com.example.scsjk;

import org.mybatis.spring.annotation.MapperScan;//MyBatis 自动扫描 Mapper 接口
import org.springframework.boot.SpringApplication; // Spring Boot 启动类
import org.springframework.boot.autoconfigure.SpringBootApplication; // Spring Boot 自动配置


@SpringBootApplication
@MapperScan("com.example.scsjk.mapper")

public class ScsjkBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScsjkBackendApplication.class, args);
    }

}
