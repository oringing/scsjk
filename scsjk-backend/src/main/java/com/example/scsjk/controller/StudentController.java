package com.example.scsjk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Student;
import com.example.scsjk.mapper.StudentMapper;
import com.example.scsjk.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")  // 允许所有来源访问，生产环境建议替换为具体域名
public class StudentController {
    private final StudentMapper studentMapper;
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentMapper studentMapper, StudentService studentService) {
        this.studentMapper = studentMapper;
        this.studentService = studentService;
    }

    // 分页查询
    @GetMapping("/page")
    public IPage<Student> getPage(@RequestParam(defaultValue = "1") Integer current,
                                  @RequestParam(defaultValue = "10") Integer size) {
        Page<Student> page = new Page<>(current, size);
        return studentService.selectPage(page);
    }

    // 查询所有
    @GetMapping("/all")
    public List<Student> getAll() {
        return studentService.selectAll();
    }

    // 根据ID查询
    @GetMapping("/{sno}")
    public Student getById(@PathVariable String sno) {
        // 添加日志确认参数
        System.out.println("查询学号: " + sno);

        Student student = studentService.selectById(sno);

        if (student == null) {
            System.out.println("未找到学号为 " + sno + " 的学生");
        }

        return student;
    }

    // 添加
    @PostMapping
    public boolean add(@RequestBody Student student) {
        return studentService.insert(student);
    }

    // 修改
    @PutMapping
    public boolean update(@RequestBody Student student) {
        return studentService.update(student);
    }

    // 删除
    @DeleteMapping("/{sno}")
    public boolean delete(@PathVariable String sno) {
        return studentService.deleteById(sno);
    }

    // 按姓名查询
    @GetMapping("/search")
    public List<Student> searchByName(@RequestParam String name) {
        return studentService.searchByName(name);
    }

    public StudentMapper getStudentMapper() {
        return studentMapper;
    }
}
