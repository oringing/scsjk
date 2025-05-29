package com.example.scsjk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;// 引入Mybatis-Plus分页插件，用于分页查询
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 引入Mybatis-Plus分页插件，用于分页查询
import com.example.scsjk.entity.Course; // 引入实体类，用于Mybatis-Plus分页插件
import com.example.scsjk.mapper.CourseMapper; // 引入Mapper接口，用于Mybatis-Plus分页插件
import com.example.scsjk.service.CourseService; // 引入服务接口，用于Mybatis-Plus分页插件
import org.springframework.stereotype.Service; // 引入Spring注解，用于注入服务
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper; // 引入Mybatis-Plus查询条件构造器，用于条件查询
import javax.annotation.Resource; // 引入Spring注解，用于注入Mapper接口
import java.util.List; // 引入List接口，用于条件查询

@Service
public class CourseServiceImpl implements CourseService { // 实现服务接口，并注入Spring注解

    @Resource // 注入 CourseMapper
    private CourseMapper courseMapper; // 注入 Mapper接口

    @Override
    public IPage<Course> selectPage(Page<Course> page) { // 实现分页查询
        return courseMapper.selectPage(page, null);
    }

    @Override //查询所有课程数据
    public List<Course> selectAll() {
        return courseMapper.selectList(null);
    }

    @Override //根据课程号查询课程数据
    public Course selectById(String cno) {
        return courseMapper.selectById(cno);
    }

    @Override //新增课程数据
    public boolean insert(Course course) {
        return courseMapper.insert(course) > 0;
    }

    @Override //更新课程数据
    public boolean update(Course course) {
        return courseMapper.updateById(course) > 0;
    }

    @Override //根据课程号删除课程数据
    public boolean deleteById(String cno) {
        return courseMapper.deleteById(cno) > 0;
    }

    @Override // 根据课程名模糊查询课程数据
    public List<Course> searchByName(String name) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("cname", name);
        return courseMapper.selectList(queryWrapper);
    }
}
