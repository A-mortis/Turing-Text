package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Resignation;

import java.util.List;

public interface ResignationService {
    PageResult<Resignation> page(Integer page, Integer pageSize);

    Resignation getResignationByIndex(Integer index);

    String getNameById(Integer id);

    List<Resignation> getResignationById(Integer id);

    void add(Resignation resignation);

    void update(Resignation resignation);

    void deleteByIndex(List<Integer> indexes);

    void pass(Integer index);

    void reject(Integer index);
}
