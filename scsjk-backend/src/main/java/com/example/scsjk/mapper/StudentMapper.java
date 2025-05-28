package com.example.scsjk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.scsjk.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}