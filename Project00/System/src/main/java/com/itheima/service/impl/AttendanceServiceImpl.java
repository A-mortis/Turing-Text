package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.AttendanceMapper;
import com.itheima.pojo.Attendance;
import com.itheima.pojo.Leave;
import com.itheima.pojo.PageResult;
import com.itheima.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public  List<Attendance> getAttendanceByUsername(String username) {
        return attendanceMapper.getAttendanceByUsername(username);
    }

    @Override
    public PageResult<Attendance> page(Integer page, Integer pageSize, String username, LocalDate createTime, LocalDate updateTime) {
         PageHelper.startPage(page,pageSize);

        List<Attendance> leaveList = attendanceMapper.list(username,  createTime, updateTime);

        Page<Attendance> p = (Page<Attendance>) leaveList;

        return new PageResult<Attendance>(p.getTotal(), p.getResult());
    }

    @Transactional
    @Override
    public void add(Attendance attendance) {

        attendance.setCreateTime(LocalDateTime.now());
        attendanceMapper.insert(attendance);

    }
}
