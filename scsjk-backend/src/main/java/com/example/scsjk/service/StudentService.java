package com.example.scsjk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Student;
import org.springframework.stereotype.Service; // 引入Service注解

import java.util.List;

@Service
// @Service注解标记了该接口，表示这是一个服务层的接口，Spring会在自动扫描组件时识别到它，并将其实例化为bean。

public interface StudentService {
    IPage<Student> selectPage(Page<Student> page); // 定义接口方法，返回IPage类型，用于分页查询
    List<Student> selectAll(); // 定义接口方法，返回List类型，用于查询所有学生信息
    Student selectById(String sno); // 定义接口方法，返回Student类型，用于根据学号查询学生信息
    boolean insert(Student student); // 定义接口方法，返回boolean类型，用于新增学生信息
    boolean update(Student student); // 定义接口方法，返回boolean类型，用于更新学生信息
    boolean deleteById(String sno); // 定义接口方法，返回boolean类型，用于删除学生信息
    List<Student> searchByName(String name); // 定义接口方法，返回List类型，用于根据姓名模糊查询学生信息
}