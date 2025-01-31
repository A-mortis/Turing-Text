package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.LeaveMapper;
import com.itheima.pojo.Leave;
import com.itheima.pojo.PageResult;
import com.itheima.service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public void reject(Integer index) {
        leaveMapper.reject(index);
    }

    @Override
    public void pass(Integer index) {
        leaveMapper.pass(index);
    }

    @Override
    public PageResult<Leave> page(Integer page, Integer pageSize) {
         PageHelper.startPage(page,pageSize);

        List<Leave> leaveList = leaveMapper.page();

        Page<Leave> p = (Page<Leave>) leaveList;

        return new PageResult<Leave>(p.getTotal(), p.getResult());
    }

    @Override
    public String getNameById(Integer id) {
        return leaveMapper.getNameById(id);
    }

    @Override
    public void deleteByIndex(List<Integer> indexes) {
        leaveMapper.deleteByIndex(indexes);
    }

    @Override
    public void update(Leave leave) {
        leaveMapper.update(leave);
    }

    @Override
    public Leave getLeaveByIndex(Integer index) {
        return  leaveMapper.getLeaveByIndex(index);
    }

    @Override
    public void add(Leave leave) {
        leaveMapper.insert(leave);
    }

    @Override
    public List<Leave> getleaveById(Integer id){
        return leaveMapper.getLeaveById(id);
    }
}
