package com.itheima.service;

import com.itheima.pojo.Leave;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface LeaveService {
    List<Leave> getleaveById(Integer id);

    void add(Leave leave);

    Leave getLeaveByIndex(Integer index);

    void update(Leave leave);

    void deleteByIndex(List<Integer> indexes);

    String getNameById(Integer id);

    PageResult<Leave> page(Integer page, Integer pageSize);

    void pass(Integer index);

    void reject(Integer index);
}
