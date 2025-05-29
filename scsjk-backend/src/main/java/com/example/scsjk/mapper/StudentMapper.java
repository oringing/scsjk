package com.example.scsjk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper; // 引入BaseMapper，用于继承
import com.example.scsjk.entity.Student; // 引入Student实体类，用于继承
import org.apache.ibatis.annotations.Mapper; // 引入Mapper注解，用于标识该接口为mybatis接口

@Mapper
public interface StudentMapper extends BaseMapper<Student> { //声明了一个名为StudentMapper的公共接口。
}