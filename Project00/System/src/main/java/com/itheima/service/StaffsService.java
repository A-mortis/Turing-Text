package com.itheima.service;


import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Staffs;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface StaffsService {


    LoginInfo login(Staffs staffs);

    void deleteById(List<Integer> ids);

    // 查询所有员工
    List<Staffs> findAll();

    void add(Staffs staffs);

    Staffs getStaffsById(Integer id);

    void update(Staffs staffs);

    PageResult<Staffs> page(Integer page, Integer pageSize , String name, Integer gender, LocalDate createTime, LocalDate updateTime);
}

    
