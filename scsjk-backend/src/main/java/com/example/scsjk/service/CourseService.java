package com.example.scsjk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    IPage<Course> selectPage(Page<Course> page);
    List<Course> selectAll();
    Course selectById(String cno);
    boolean insert(Course course);
    boolean update(Course course);
    boolean deleteById(String cno);
    List<Course> searchByName(String name);
}