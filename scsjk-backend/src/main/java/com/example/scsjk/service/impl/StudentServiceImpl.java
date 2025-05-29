package com.example.scsjk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage; // 引入分页插件
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 引入分页插件
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper; // 引入 QueryWrapper
import com.example.scsjk.entity.Student; // 引入 Student 实体类
import com.example.scsjk.mapper.StudentMapper; // 引入 StudentMapper 接口
import com.example.scsjk.service.StudentService; // 引入 StudentService 接口
import org.springframework.stereotype.Service; // 引入 Spring 注解

import javax.annotation.Resource; // 引入 Spring 注解
import java.util.List; // 引入 List 接口

@Service // 声明 StudentServiceImpl 为 Spring Bean
public class StudentServiceImpl implements StudentService { // 实现 StudentService 接口,提供接口中定义的所有业务逻辑服务

    @Resource
    private StudentMapper studentMapper;
    // 注入 StudentMapper 接口,用于操作 Student 实体类

    @Override
    public IPage<Student> selectPage(Page<Student> page) {
        return studentMapper.selectPage(page, null);
        // 调用 StudentMapper 接口的 selectPage 方法,传入分页参数和查询条件,返回分页结果
    }

    @Override
    public List<Student> selectAll() {
        return studentMapper.selectList(null);
        // 调用 StudentMapper 接口的 selectList 方法,传入查询条件,返回所有学生信息
    }

    @Override
    public Student selectById(String sno) {
        return studentMapper.selectById(sno);
        // 调用 StudentMapper 接口的 selectById 方法,传入学生编号,返回学生信息
    }

    @Override
    public boolean insert(Student student) {
        return studentMapper.insert(student) > 0;
        // 调用 StudentMapper 接口的 insert 方法,传入学生实体类,返回插入结果
    }

    @Override
    public boolean update(Student student) {
        return studentMapper.updateById(student) > 0;
        // 调用 StudentMapper 接口的 updateById 方法,传入学生实体类,返回更新结果
    }

    @Override
    public boolean deleteById(String sno) {
        return studentMapper.deleteById(sno) > 0;
        // 调用 StudentMapper 接口的 deleteById 方法,传入学生编号,返回删除结果
    }

    @Override
    public List<Student> searchByName(String name) {
        // 使用简化的 QueryWrapper 导入
        return studentMapper.selectList(new QueryWrapper<Student>().eq("sname", name));
        // 调用 StudentMapper 接口的 selectList 方法,传入查询条件,返回所有学生信息
    }
}
