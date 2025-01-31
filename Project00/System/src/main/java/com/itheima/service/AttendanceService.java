package com.itheima.service;

import com.itheima.pojo.Attendance;
import com.itheima.pojo.Leave;
import com.itheima.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    void add(Attendance attendance);

    PageResult<Attendance> page(Integer page, Integer pageSize, String username, LocalDate createTime, LocalDate updateTime);

    List<Attendance> getAttendanceByUsername(String username);
}
