package com.example.scsjk.service;

import com.baomidou.mybatisplus.core.metadata.IPage; // 导入IPage接口
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 导入Page类
import com.example.scsjk.entity.Sc; // 导入Sc实体类
import org.springframework.stereotype.Service; // 导入Service注解,用于注入到spring容器中

import java.util.List;

@Service
public interface ScService {
    IPage<Sc> selectPage(Page<Sc> page); // 自定义分页查询
    List<Sc> selectAll(); // 查询所有
    Sc selectByIds(String sno, String cno); // 根据学号和课程号查询
    boolean insert(Sc sc); // 插入
    boolean update(Sc sc);  // 更新
    boolean deleteByIds(String sno, String cno); // 根据学号和课程号删除
    List<Sc> searchBySno(String sno); // 根据学号查询
    List<Sc> searchByCno(String cno); // 根据课程号查询
}