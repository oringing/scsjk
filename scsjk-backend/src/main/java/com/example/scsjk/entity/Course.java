package com.example.scsjk.entity;


import com.baomidou.mybatisplus.annotation.TableId; // mybatisplus注解，用于指定主键
import com.baomidou.mybatisplus.annotation.TableName; // mybatisplus注解，用于指定表名
import lombok.AllArgsConstructor; // lombok注解，用于生成构造函数
import lombok.Data; // lombok注解，用于生成getter/setter/tostring方法

@Data // lombok注解，用于生成getter/setter/tostring方法
@TableName("course")
@AllArgsConstructor

public class Course {   // 实体类，对应数据库中的course表
    @TableId
    private String cno;
    private String cname;
    private String cpno;
    private Integer ccredit;
}
