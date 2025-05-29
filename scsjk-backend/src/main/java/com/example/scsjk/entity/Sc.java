package com.example.scsjk.entity;

import com.baomidou.mybatisplus.annotation.TableName; // 引入表名注解，用于指定表名
import com.baomidou.mybatisplus.annotation.TableId; // 引入主键注解，用于指定主键
import lombok.Data; // 引入lombok注解，用于简化代码
@TableName("sc")
@Data
public class Sc {
    @TableId
    private String sno;
    private String cno;
    private Integer grade;
}