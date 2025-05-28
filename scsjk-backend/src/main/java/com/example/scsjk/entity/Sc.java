package com.example.scsjk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@TableName("sc")
@Data
public class Sc {
    @TableId
    private String sno;
    private String cno;
    private Integer grade;
}