package com.itheima.controller;
import com.itheima.pojo.Leave;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@CrossOrigin("*")
@RequestMapping("/leave")
@RestController
public class LeaveController {
      @Autowired
    private LeaveService leaveService;
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize)
                      {
        PageResult<Leave> pageResult = leaveService.page(page,pageSize);
        return Result.success(pageResult);
    }


    @GetMapping("/by-index/{index}")
    public Result getLeaveByIndex(@PathVariable Integer index) {
        //System.out.println("根据ID查询员工："+ id);
        log.info("根据Index查询请假：{}", index);
        Leave leave= leaveService.getLeaveByIndex(index);
        return Result.success(leave);
    }

    @GetMapping("/get-name/{id}")
    public Result getNameById(@PathVariable Integer id){
        //System.out.println("根据ID查询员工："+ id);
        log.info("根据ID查询员工名字：{}",id);
        String name= leaveService.getNameById(id);
        return Result.success(name);
    }

  @GetMapping("/{id}")
    public Result getLeaveById(@PathVariable Integer id){
        log.info("根据ID查询请假：{}",id);
        List<Leave> leave= leaveService.getleaveById(id);
        return Result.success(leave);
    }

    @PostMapping
    public Result save(@RequestBody Leave leave){
        log.info("新增请假：{}",leave);
        leaveService.add(leave);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Leave leave){
        //System.out.println("修改员工："+ staffs);
        log.info("修改请假信息：{}",leave);
        leaveService.update(leave);
        return Result.success();
    }

     @DeleteMapping
    public Result delete(@RequestParam List<Integer> indexes){
        //System.out.println("根据ID删除："+ id);
        log.info("根据ID删除：{}", indexes);
        leaveService.deleteByIndex(indexes);
        return Result.success();
    }

    //根据index通过审核，将status改为2
    @PutMapping("/pass/{index}")
    public Result passStatus(@PathVariable Integer index){
        leaveService.pass(index);
        return Result.success();
    }

    @PutMapping("/reject/{index}")
    public Result unPassStatus(@PathVariable Integer index){
        leaveService.reject(index);
        return Result.success();
    }
}
