package com.example.scsjk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scsjk.entity.Sc;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScService {
    IPage<Sc> selectPage(Page<Sc> page);
    List<Sc> selectAll();
    Sc selectByIds(String sno, String cno);
    boolean insert(Sc sc);
    boolean update(Sc sc);
    boolean deleteByIds(String sno, String cno);
    List<Sc> searchBySno(String sno);
    List<Sc> searchByCno(String cno);
}