package com.itheima.service.impl;

import com.itheima.mapper.EnrollActivityMapper;
import com.itheima.service.EnrollActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollActivityServiceImpl implements EnrollActivityService {

    @Autowired
    private EnrollActivityMapper enrollActivityMapper;

    @Override
    public void getStatus(Integer staffId, Integer activityId) {
        enrollActivityMapper.getStatus(staffId,activityId);
    }

    @Override
    public void cancel(Integer staffId, Integer activityId) {
        enrollActivityMapper.cancel(staffId,activityId);
    }

    @Override
    public void enroll(Integer staffId, Integer activityId) {
        enrollActivityMapper.enroll(staffId,activityId);
    }


}
