package com.example.scsjk.service;

import com.baomidou.mybatisplus.core.metadata.IPage; //
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//表明 CourseService 是一个服务组件，Spring 会自动扫描并将其注册为 Spring 容器中的一个 Bean。
public interface CourseService {
    IPage<Course> selectPage(Page<Course> page);//分页查询
    List<Course> selectAll(); //查询所有
    Course selectById(String cno); //根据课程号查询课程
    boolean insert(Course course); //插入课程
    boolean update(Course course); //更新课程
    boolean deleteById(String cno); //根据课程号删除课程
    List<Course> searchByName(String name); //根据课程名模糊查询课程
}