package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.Staffs;
import com.itheima.service.EnrollActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RequestMapping("/enrollActivity")
@RestController
public class EnrollActivityController {

    @Autowired
    private EnrollActivityService enrollActivityService;
    @PostMapping
    public Result enroll(@RequestParam Integer staff_id,
                         @RequestParam Integer activity_id){
        enrollActivityService.enroll(staff_id,activity_id);
        return Result.success();
    }

    @DeleteMapping
     public Result cancel(@RequestParam Integer staff_id,
                         @RequestParam Integer activity_id){
        enrollActivityService.cancel(staff_id,activity_id);
        return Result.success();
    }

    @GetMapping
     public Result getStatus(@RequestParam Integer staff_id,
                         @RequestParam Integer activity_id){
        enrollActivityService.getStatus(staff_id,activity_id);
        return Result.success();
    }


}
