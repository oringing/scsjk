package com.example.scsjk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Course;
import com.example.scsjk.mapper.CourseMapper;
import com.example.scsjk.service.CourseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource // 注入 CourseMapper
    private CourseMapper courseMapper;

    @Override
    public IPage<Course> selectPage(Page<Course> page) {
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
