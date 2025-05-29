package com.example.scsjk.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor; // 引入分页插件
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor; // 引入分页拦截器
import org.springframework.context.annotation.Bean; // 引入Bean注解
import org.springframework.context.annotation.Configuration; // 引入Configuration注解, 用于声明配置类

@Configuration // 声明为Spring Bean
public class MyBatisPlusConfig {

    @Bean // 声明Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() { // 自定义mybatis-plus分页插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor(); // 创建MybatisPlusInterceptor对象
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); // 添加分页拦截器
        return interceptor; // 返回MybatisPlusInterceptor对象, 注册到Spring容器中
    }
}