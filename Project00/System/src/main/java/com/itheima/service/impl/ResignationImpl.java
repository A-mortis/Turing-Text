package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ResignationMapper;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Resignation;
import com.itheima.service.ResignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResignationImpl implements ResignationService {
    @Autowired
    private ResignationMapper resignationMapper;

    @Override
    public void reject(Integer index) {
        resignationMapper.reject(index);
    }

    @Override
    public void pass(Integer index) {

        resignationMapper.pass(index);
    }

    @Override
    public void deleteByIndex(List<Integer> indexes) {
        resignationMapper.deleteByIndex(indexes);
    }

    @Override
    public void update(Resignation resignation) {
        resignationMapper.update(resignation);
    }

    @Override
    public void add(Resignation resignation) {
        resignationMapper.add(resignation);
    }

    @Override
    public List<Resignation> getResignationById(Integer id) {
        return resignationMapper.getResignationById(id);
    }

    @Override
    public String getNameById(Integer id) {
        return resignationMapper.getNameById(id);
    }

    @Override
    public Resignation getResignationByIndex(Integer index) {
      return resignationMapper.getResignationByIndex(index);
    }

    @Override
    public PageResult<Resignation> page(Integer page, Integer pageSize) {
         PageHelper.startPage(page,pageSize);

        List<Resignation> resignationList = resignationMapper.page();

        Page<Resignation> p = (Page<Resignation>) resignationList;

        return new PageResult<Resignation>(p.getTotal(), p.getResult());
    }
}
