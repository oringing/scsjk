package com.example.scsjk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage; // 引入Mybatis-Plus分页插件
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Course; // 引入实体类
import com.example.scsjk.mapper.CourseMapper;  // 引入Mybatis-Plus接口
import com.example.scsjk.service.CourseService; // 引入业务接口
import org.springframework.beans.factory.annotation.Autowired; // 引入Spring自动注入
import org.springframework.web.bind.annotation.*; // 引入Spring注解
import java.util.List; // 引入List接口

@RestController // 控制器注解
@RequestMapping("/course") // 请求映射
@CrossOrigin(origins = "http://localhost:5173/") // 跨域注解
public class CourseController { // 控制器类

    private final CourseMapper courseMapper; // Mybatis-Plus接口
    private final CourseService courseService; // 业务接口

    @Autowired
    public CourseController(CourseMapper courseMapper, CourseService courseService) { // 构造函数注入，
        this.courseMapper = courseMapper; // 接口注入
        this.courseService = courseService; // 接口注入
    }

    // 分页查询
    @GetMapping("/page")
    public IPage<Course> getPage(@RequestParam(defaultValue = "1") Integer current,  // 接收请求参数
                                 @RequestParam(defaultValue = "10") Integer size) { // 接收请求参数
        Page<Course> page = new Page<>(current, size); // 创建分页对象
        return courseService.selectPage(page); // 调用业务接口
    }

    // 查询所有
    @GetMapping("/all") // 请求方法注解
    public List<Course> getAll() { // 接收请求参数
        return courseService.selectAll(); // 调用业务接口
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