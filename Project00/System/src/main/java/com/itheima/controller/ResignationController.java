package com.itheima.controller;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Resignation;
import com.itheima.pojo.Result;
import com.itheima.service.ResignationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
//@CrossOrigin("*")
@RequestMapping("/resignation")
@RestController
public class ResignationController {

    @Autowired
    private ResignationService resignationService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult<Resignation> pageResult = resignationService.page(page, pageSize);
        return Result.success(pageResult);
    }


    @GetMapping("/by-index/{index}")
    public Result getResignationByIndex(@PathVariable Integer index) {
        //System.out.println("根据ID查询员工："+ id);
        log.info("根据Index查询请假：{}", index);
        Resignation resignation = resignationService.getResignationByIndex(index);
        return Result.success(resignation);
    }

    @GetMapping("/get-name/{id}")
    public Result getNameById(@PathVariable Integer id) {
        //System.out.println("根据ID查询员工："+ id);
        log.info("根据ID查询员工名字：{}", id);
        String name = resignationService.getNameById(id);
        return Result.success(name);
    }

    @GetMapping("/{id}")
    public Result getResignationById(@PathVariable Integer id) {
        //System.out.println("根据ID查询员工："+ id);
        log.info("根据ID查询请假：{}", id);
        List<Resignation> leave = resignationService.getResignationById(id);
        return Result.success(leave);
    }

    @PostMapping
    public Result save(@RequestBody Resignation resignation) {
        log.info("新增请假：{}", resignation);
        resignationService.add(resignation);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Resignation resignation) {
        //System.out.println("修改员工："+ staffs);
        log.info("修改请假信息：{}", resignation);
        resignationService.update(resignation);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> indexes) {
        //System.out.println("根据ID删除："+ id);
        log.info("根据ID删除：{}", indexes);
        resignationService.deleteByIndex(indexes);
        return Result.success();
    }

    //根据index通过审核，将status改为2
    @PutMapping("/pass/{index}")
    public Result passStatus(@PathVariable Integer index) {
        resignationService.pass(index);
        return Result.success();
    }

    @PutMapping("/reject/{index}")
    public Result unPassStatus(@PathVariable Integer index) {
        resignationService.reject(index);
        return Result.success();
    }
}

