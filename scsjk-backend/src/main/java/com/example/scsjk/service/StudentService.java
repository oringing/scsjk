package com.example.scsjk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    IPage<Student> selectPage(Page<Student> page);
    List<Student> selectAll();
    Student selectById(String sno);
    boolean insert(Student student);
    boolean update(Student student);
    boolean deleteById(String sno);
    List<Student> searchByName(String name);
}