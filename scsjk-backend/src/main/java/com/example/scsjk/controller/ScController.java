package com.example.scsjk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Sc;
import com.example.scsjk.mapper.ScMapper;
import com.example.scsjk.service.ScService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sc")
@CrossOrigin(origins = "*")
public class ScController {

    private final ScMapper scMapper;
    private final ScService scService;

    @Autowired
    public ScController(ScMapper scMapper, ScService scService) {
        this.scMapper = scMapper;
        this.scService = scService;
    }

    // 分页查询
    @GetMapping("/page")
    public IPage<Sc> getPage(@RequestParam(defaultValue = "1") Integer current,
                             @RequestParam(defaultValue = "10") Integer size) {
        Page<Sc> page = new Page<>(current, size);
        return scService.selectPage(page);
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