package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ActivityMapper;
import com.itheima.pojo.Activity;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Staffs;
import com.itheima.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public PageResult<Activity> page(Integer page, Integer pageSize, String content, LocalDate createTime, LocalDate updateTime) {
       PageHelper.startPage(page,pageSize);

        List<Activity> activityList = activityMapper.list(content,  createTime, updateTime);

        Page<Activity> p = (Page<Activity>) activityList;

        return new PageResult<Activity>(p.getTotal(), p.getResult());
    }


    @Override
    public List<Staffs> getStaffsById(Integer id) {
        return activityMapper.getStaffsById(id);
    }

    @Override
    public Activity getActivityById(Integer id) {

        return activityMapper.getActivityById(id);
    }

    @Override
    public void update(Activity activity) {
        activity.setUpdateTime(LocalDate.now());
        activityMapper.update(activity);
    }

    @Override
    public void deleteById(List<Integer> ids) {
         activityMapper.deleteById(ids);
        log.info("删除成功");
    }

    @Transactional
    @Override
    public void add(Activity activity) {
         activity.setCreateTime(LocalDate.now());
        activityMapper.insert(activity);
    }
}
