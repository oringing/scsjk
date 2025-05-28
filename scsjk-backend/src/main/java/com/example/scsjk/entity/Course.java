package com.example.scsjk.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("course")
@AllArgsConstructor

public class Course {
    @TableId
    private String cno;
    private String cname;
    private String cpno;
    private Integer ccredit;
}
