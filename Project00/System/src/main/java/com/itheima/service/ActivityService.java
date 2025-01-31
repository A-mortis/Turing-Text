package com.itheima.service;

import com.itheima.pojo.Activity;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Staffs;

import java.time.LocalDate;
import java.util.List;

public interface ActivityService {
    PageResult<Activity> page(Integer page, Integer pageSize, String content, LocalDate createTime, LocalDate updateTime);

    void add(Activity activity);

    void deleteById(List<Integer> ids);

    void update(Activity activity);

    Activity getActivityById(Integer id);


    List<Staffs> getStaffsById(Integer id);
}
