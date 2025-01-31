package com.itheima.controller;

import com.itheima.pojo.Activity;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.Staffs;
import com.itheima.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@CrossOrigin("*")
@RequestMapping("/activity")
@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String content,
                       @DateTimeFormat(pattern = "YYYY-MM-DD")LocalDate createTime,
                       @DateTimeFormat(pattern = "YYYY-MM-DD")LocalDate updateTime){
        log.info("分页查询员工数据：{},{},{},{},{}",page,pageSize,content,createTime,updateTime);
        PageResult<Activity> pageResult = activityService.page(page, pageSize,content,createTime,updateTime);
        return Result.success(pageResult);
    }

     @GetMapping("/{id}")
    public Result getStaffsById(@PathVariable Integer id){
        log.info("根据ID查询员工：{}",id);
        Activity activity = activityService.getActivityById(id);
        return Result.success(activity);
    }
  @PostMapping
    public Result save(@RequestBody Activity activity) {
        //System.out.println("新增员工："+ staffs);
        log.info("新增员工：{}", activity);
        activityService.add(activity);
        return Result.success();
    }



    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        //System.out.println("根据ID删除："+ id);
        log.info("根据ID删除：{}", ids);
        activityService.deleteById(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Activity activity){
        //System.out.println("修改员工："+ staffs);
        log.info("修改员工：{}",activity);
        activityService.update(activity);
        return Result.success();
    }

  @GetMapping("/getStaffs/{id}")
    public Result getLeaveById(@PathVariable Integer id){
        log.info("根据ID查询请假：{}",id);
        List<Staffs> staffs= activityService.getStaffsById(id);
        return Result.success(staffs);
    }

}
