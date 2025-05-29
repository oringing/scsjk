package com.example.scsjk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper; // 引入BaseMapper，用于继承
import com.example.scsjk.entity.Course; // 引入实体类
import org.apache.ibatis.annotations.Mapper; // 引入Mapper注解

@Mapper
public interface CourseMapper extends BaseMapper<Course> { // 继承BaseMapper，并使用Mapper注解，用于Mybatis自动生成SQL语句
}