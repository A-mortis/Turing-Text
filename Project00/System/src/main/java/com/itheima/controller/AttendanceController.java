package com.itheima.controller;


import com.itheima.pojo.Attendance;
import com.itheima.pojo.Leave;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@CrossOrigin("*")
@RequestMapping("/attendance")
@RestController
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping
    public Result save(@RequestBody Attendance attendance) {
        //System.out.println("新增员工："+ staffs);
        log.info("新增员工：{}", attendance);
        attendanceService.add(attendance);
        return Result.success();
    }

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String username,
                       @DateTimeFormat(pattern = "YYYY-MM-DD") LocalDate createTime,
                        @DateTimeFormat(pattern = "YYYY-MM-DD")LocalDate updateTime){
        log.info("分页查询员工数据：{},{},{},{},{}",page,pageSize,username,createTime,updateTime);
        PageResult<Attendance> pageResult = attendanceService.page(page, pageSize,username,createTime,updateTime);
        return Result.success(pageResult);
    }

    @GetMapping("/get-name/{username}")
    public Result getNameByUsername(@PathVariable String username){
        log.info("根据用户名查询员工姓名：{}",username);
        List<Attendance> attendance = attendanceService.getAttendanceByUsername(username);
        return Result.success(attendance);
    }
}
