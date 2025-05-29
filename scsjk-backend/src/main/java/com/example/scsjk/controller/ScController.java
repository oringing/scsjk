package com.example.scsjk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage; // 引入MybatisPlus分页插件,IPage 是一个分页接口，Page 是其实现类，用于封装分页查询的数据。
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 引入MybatisPlus分页插件
import com.example.scsjk.entity.Sc; // 引入实体类
import com.example.scsjk.mapper.ScMapper; // 引入Mapper接口,定义对数据库的操作。Mapper通常是MyBatis或MyBatis-Plus中用于执行SQL语句的接口，而Service接口则封装了业务逻辑
import com.example.scsjk.service.ScService; // 引入Service接口
import org.springframework.beans.factory.annotation.Autowired; // 引入Autowired注解
import org.springframework.web.bind.annotation.*; // 引入RestController和RequestMapping注解

import java.util.List; // 引入List接口

@RestController  // 定义控制器
@RequestMapping("/sc") // 定义请求路径
@CrossOrigin(origins = "http://localhost:5173/") // 允许跨域请求
public class ScController {

    private final ScMapper scMapper; // 引入Mapper接口
    private final ScService scService; // 引入Service接口

    @Autowired // 自动注入
    public ScController(ScMapper scMapper, ScService scService) { // 构造函数注入
        this.scMapper = scMapper; // 注入Mapper接口
        this.scService = scService; // 注入Service接口
    }

    // 分页查询
    @GetMapping("/page") // 定义请求方法
    public IPage<Sc> getPage(@RequestParam(defaultValue = "1") Integer current, // 当前页
                             @RequestParam(defaultValue = "10") Integer size) { // 每页条数
        Page<Sc> page = new Page<>(current, size); // 创建分页对象
        return scService.selectPage(page); // 调用Service接口查询分页数据
    }

    // 查询所有
    @GetMapping("/all")
    public List<Sc> getAll() {
        return scService.selectAll();
    }

    // 根据学号和课程号查询
    @GetMapping("/{sno}/{cno}")
    public Sc getByIds(@PathVariable String sno, @PathVariable String cno) {
        return scService.selectByIds(sno, cno);
    }

    // 添加
    @PostMapping
    public boolean add(@RequestBody Sc sc) {
        return scService.insert(sc);
    }

    // 修改
    @PutMapping
    public boolean update(@RequestBody Sc sc) {
        return scService.update(sc);
    }

    // 删除
    @DeleteMapping("/{sno}/{cno}")
    public boolean delete(@PathVariable String sno, @PathVariable String cno) {
        return scService.deleteByIds(sno, cno);
    }

    // 按学号查询
    @GetMapping("/search/sno")
    public List<Sc> searchBySno(@RequestParam String sno) {
        return scService.searchBySno(sno);
    }

    // 按课程号查询
    @GetMapping("/search/cno")
    public List<Sc> searchByCno(@RequestParam String cno) {
        return scService.searchByCno(cno);
    }
}