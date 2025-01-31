package com.itheima.service;

public interface EnrollActivityService {
    void enroll(Integer staffId, Integer activityId);

    void cancel(Integer staffId, Integer activityId);

    void getStatus(Integer staffId, Integer activityId);
}
