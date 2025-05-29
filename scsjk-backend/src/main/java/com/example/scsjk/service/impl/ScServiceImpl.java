package com.example.scsjk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;  // 引入QueryWrapper，用于条件查询
import com.baomidou.mybatisplus.core.metadata.IPage;  // 引入IPage，用于分页
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;  // 引入Page，用于分页
import com.example.scsjk.entity.Sc;  // 引入Sc实体类
import com.example.scsjk.mapper.ScMapper;  // 引入ScMapper接口，用于操作数据库
import com.example.scsjk.service.ScService;  // 引入ScService接口，用于业务逻辑处理
import org.springframework.stereotype.Service;  // 引入Service注解，用于注入Spring容器

import javax.annotation.Resource;  // 引入Resource注解，用于注入Spring容器中的Bean
import java.util.List;  // 引入List接口，用于返回列表数据

@Service  // 标注ScServiceImpl为Spring Bean
public class ScServiceImpl implements ScService {  // 实现ScService接口

    @Resource  // 注入ScMapper接口的Bean
    private ScMapper scMapper;  // 声明ScMapper接口的实例变量

    @Override
    public IPage<Sc> selectPage(Page<Sc> page) {
        return scMapper.selectPage(page, null);
    }

    @Override  // 实现selectAll方法
    public List<Sc> selectAll() {  // 返回所有数据
        return scMapper.selectList(null);
    }

    @Override
    public Sc selectByIds(String sno, String cno) {  // 根据学号和课程号查询成绩信息
        return scMapper.selectOne(new QueryWrapper<Sc>()
                .eq("sno", sno)
                .eq("cno", cno));
    }

    @Override
    public boolean insert(Sc sc) {  // 插入一条数据
        return scMapper.insert(sc) > 0;
    }

    @Override
    public boolean update(Sc sc) {  // 更新一条数据
        return scMapper.updateById(sc) > 0;
    }

    @Override
    public boolean deleteByIds(String sno, String cno) {  // 根据学号和课程号删除一条数据
        return scMapper.delete(new QueryWrapper<Sc>()
                .eq("sno", sno)
                .eq("cno", cno)) > 0;
    }

    @Override
    public List<Sc> searchBySno(String sno) {  // 根据学号查询成绩信息
        return scMapper.selectList(new QueryWrapper<Sc>().eq("sno", sno));
    }

    @Override
    public List<Sc> searchByCno(String cno) {  // 根据课程号查询成绩信息
        return scMapper.selectList(new QueryWrapper<Sc>().eq("cno", cno));
    }
}