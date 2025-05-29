package com.example.scsjk.entity;


import com.baomidou.mybatisplus.annotation.TableId; // 引入注解，指定主键
import com.baomidou.mybatisplus.annotation.TableName; // 引入注解，指定表名
import lombok.Data; // 引入lombok

@Data // 引入lombok，自动生成getter、setter、toString方法
@TableName("student") // 指定表名
public class Student { // 定义实体类
    @TableId // 指定主键
    private String sno;

    private String sname;
    private String ssex;
    private Integer sage;
    private String sdept;
}