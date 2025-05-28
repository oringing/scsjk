package com.example.scsjk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Sc;
import com.example.scsjk.mapper.ScMapper;
import com.example.scsjk.service.ScService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScServiceImpl implements ScService {

    @Resource
    private ScMapper scMapper;

    @Override
    public IPage<Sc> selectPage(Page<Sc> page) {
        return scMapper.selectPage(page, null);
    }

    @Override
    public List<Sc> selectAll() {
        return scMapper.selectList(null);
    }

    @Override
    public Sc selectByIds(String sno, String cno) {
        return scMapper.selectOne(new QueryWrapper<Sc>()
                .eq("sno", sno)
                .eq("cno", cno));
    }

    @Override
    public boolean insert(Sc sc) {
        return scMapper.insert(sc) > 0;
    }

    @Override
    public boolean update(Sc sc) {
        return scMapper.updateById(sc) > 0;
    }

    @Override
    public boolean deleteByIds(String sno, String cno) {
        return scMapper.delete(new QueryWrapper<Sc>()
                .eq("sno", sno)
                .eq("cno", cno)) > 0;
    }

    @Override
    public List<Sc> searchBySno(String sno) {
        return scMapper.selectList(new QueryWrapper<Sc>().eq("sno", sno));
    }

    @Override
    public List<Sc> searchByCno(String cno) {
        return scMapper.selectList(new QueryWrapper<Sc>().eq("cno", cno));
    }
}