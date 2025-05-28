package com.example.scsjk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Course;
import com.example.scsjk.mapper.CourseMapper;
import com.example.scsjk.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseMapper courseMapper, CourseService courseService) {
        this.courseMapper = courseMapper;
        this.courseService = courseService;
    }

    // 分页查询
    @GetMapping("/page")
    public IPage<Course> getPage(@RequestParam(defaultValue = "1") Integer current,
                                 @RequestParam(defaultValue = "10") Integer size) {
        Page<Course> page = new Page<>(current, size);
        return courseService.selectPage(page);
    }

    // 查询所有
    @GetMapping("/all")
    public List<Course> getAll() {
        return courseService.selectAll();
    }

    // 根据ID查询
    @GetMapping("/{cno}")
    public Course getById(@PathVariable String cno) {
        return courseService.selectById(cno);
    }

    // 添加
    @PostMapping
    public boolean add(@RequestBody Course course) {
        return courseService.insert(course);
    }

    // 修改
    @PutMapping
    public boolean update(@RequestBody Course course) {
        return courseService.update(course);
    }

    // 删除
    @DeleteMapping("/{cno}")
    public boolean delete(@PathVariable String cno) {
        return courseService.deleteById(cno);
    }

    // 按课程名查询
    @GetMapping("/search")
    public List<Course> searchByName(@RequestParam String name) {
        return courseService.searchByName(name);
    }
}