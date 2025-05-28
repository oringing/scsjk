package com.example.scsjk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.scsjk.entity.Student;
import com.example.scsjk.mapper.StudentMapper;
import com.example.scsjk.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public IPage<Student> selectPage(Page<Student> page) {
        return studentMapper.selectPage(page, null);
    }

    @Override
    public List<Student> selectAll() {
        return studentMapper.selectList(null);
    }

    @Override
    public Student selectById(String sno) {
        return studentMapper.selectById(sno);
    }

    @Override
    public boolean insert(Student student) {
        return studentMapper.insert(student) > 0;
    }

    @Override
    public boolean update(Student student) {
        return studentMapper.updateById(student) > 0;
    }

    @Override
    public boolean deleteById(String sno) {
        return studentMapper.deleteById(sno) > 0;
    }

    @Override
    public List<Student> searchByName(String name) {
        // 使用简化的 QueryWrapper 导入
        return studentMapper.selectList(new QueryWrapper<Student>().eq("sname", name));
    }
}
