package com.example.scsjk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper; // 引入Mybatis-Plus的QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage; // 引入Mybatis-Plus的Page
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 引入Mybatis-Plus的Page
    //来自Mybatis-Plus库，用于构建查询条件和分页查询
import com.example.scsjk.entity.Student; // 引入实体类
import com.example.scsjk.mapper.StudentMapper; // 引入Mapper接口,Mapper主要用于数据库操作
import com.example.scsjk.service.StudentService; // 引入Service接口,Service用于处理业务逻辑
import lombok.Getter; // 引入lombok的Getter注解
import org.springframework.beans.factory.annotation.Autowired; // 引入Spring的Autowired注解
import org.springframework.web.bind.annotation.*; // 引入Spring的注解
import org.springframework.web.bind.annotation.GetMapping; // 引入Spring的GetMapping注解
import org.springframework.web.bind.annotation.RequestMapping; // 引入Spring的RequestMapping注解
import org.springframework.web.bind.annotation.RestController; // 引入Spring的RestController注解
import java.util.HashMap; // 引入HashMap
import java.util.List; // 引入List
import java.util.Map; // 引入Map

@RestController // 标注为RestController，使得该类中的方法可以直接返回JSON数据
@RequestMapping("/student") // 标注为RequestMapping，并指定请求路径为/student
@CrossOrigin(origins = "http://localhost:5173/")  // 允许其它域访问
public class StudentController {
    // 注入Mapper和Service
    @Getter // 引入lombok的Getter注解，自动生成Getter方法，用于获取属性值
    private final StudentMapper studentMapper;
    private final StudentService studentService;

    @Autowired // 自动装配
    public StudentController(StudentMapper studentMapper, StudentService studentService) {
        this.studentMapper = studentMapper; // 注入Mapper，用于数据库操作
        this.studentService = studentService; // 注入Service，用于处理业务逻辑
    }

    // 异常处理
    @ExceptionHandler(Exception.class) // 捕获所有异常
    @ResponseBody // 将异常信息以JSON格式返回给前端
    public Map<String, Object> handleException(Exception e) { // 自定义异常处理方法
        Map<String, Object> result = new HashMap<>(); // 创建一个Map对象，用于存放结果
        result.put("code", 500); // 设置状态码为500，表示服务器内部错误
        result.put("message", "服务器内部错误：" + e.getMessage());// 设置错误信息
        e.printStackTrace(); // 打印异常堆栈信息
        return result;
    }

    // 分页查询
    @GetMapping("/page")
    public Map<String, Object> getPage(@RequestParam(defaultValue = "1") Integer current,
                                       @RequestParam(defaultValue = "10") Integer size) { // 定义查询方法，接收页码和每页记录数作为参数
        Page<Student> page = new Page<>(current, size); // 创建Page对象，用于分页查询
        IPage<Student> studentPage = studentService.selectPage(page); // 调用Service的分页查询方法

        Map<String, Object> result = new HashMap<>(); // 创建一个Map对象，用于存放结果
        result.put("records", studentPage.getRecords()); // 设置结果集
        result.put("total", studentPage.getTotal()); // 设置总记录数

        return result;
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

    public List<Student> searchByName(String name) {
        return studentMapper.selectList(new QueryWrapper<Student>().like("sname", name));
    }

}
