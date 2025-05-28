package com.example.scsjk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Sc {
    @TableId
    private String sno;
    private String cno;
    private Integer grade;
}