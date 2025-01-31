package com.itheima.controller;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.Staffs;
import com.itheima.service.StaffsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

//@Slf4j
@CrossOrigin("*")
@RequestMapping("/staffs")
@RestController
public class StaffsController {

    //日志
    private static final Logger log = LoggerFactory.getLogger(StaffsController.class);

    @Autowired
    private StaffsService staffsService;

    //@RequestMapping(value = "/staffs", method = RequestMethod.GET)
    /*@GetMapping
    public Result list(){
        //System.out.println("查询全部员工数据");
        log.info("查询全部员工数据");
        List<Staffs> staffsList = staffsService.findAll();
        return Result.success(staffsList);
    }*/

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Integer gender,
                       @DateTimeFormat(pattern = "YYYY-MM-DD")LocalDate createTime,
                       @DateTimeFormat(pattern = "YYYY-MM-DD")LocalDate updateTime){
        log.info("分页查询员工数据：{},{},{},{},{},{}",page,pageSize,name,gender,createTime,updateTime);
        PageResult<Staffs> pageResult = staffsService.page(page, pageSize,name,gender,createTime,updateTime);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        //System.out.println("根据ID删除："+ id);
        log.info("根据ID删除：{}", ids);
        staffsService.deleteById(ids);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Staffs staffs){
        //System.out.println("新增员工："+ staffs);
        log.info("新增员工：{}",staffs);
        staffsService.add(staffs);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getStaffsById(@PathVariable Integer id){
        //System.out.println("根据ID查询员工："+ id);
        log.info("根据ID查询员工：{}",id);
        Staffs staffs = staffsService.getStaffsById(id);
        return Result.success(staffs);
    }

    @PutMapping
    public Result update(@RequestBody Staffs staffs){
        //System.out.println("修改员工："+ staffs);
        log.info("修改员工：{}",staffs);
        staffsService.update(staffs);
        return Result.success();
    }
}
