package com.example.scsjk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.scsjk.entity.Student;
import com.example.scsjk.mapper.StudentMapper;
import com.example.scsjk.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public IPage<Student> selectPage(Page<Student> page) {
        return baseMapper.selectPage(page, null);
    }

    @Override
    public List<Student> selectAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public Student selectById(String sno) {
        return baseMapper.selectById(sno);
    }

    @Override
    public boolean insert(Student student) {
        return baseMapper.insert(student) > 0;
    }

    @Override
    public boolean update(Student student) {
        return baseMapper.updateById(student) > 0;
    }

    @Override
    public boolean deleteById(String sno) {
        return baseMapper.deleteById(sno) > 0;
    }

    @Override
    public List<Student> searchByName(String name) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.like("sname", name);
        return baseMapper.selectList(wrapper);
    }
}