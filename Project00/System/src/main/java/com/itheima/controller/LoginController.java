package com.itheima.controller;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.Result;
import com.itheima.pojo.Staffs;
import com.itheima.service.StaffsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private StaffsService staffsService;


    @PostMapping("/login")
    public Result login(@RequestBody Staffs staffs){
    //log.info("登录：{}",staffs);
        log.info("登录：{}",staffs);
    LoginInfo loginInfo = staffsService.login(staffs);
    if (loginInfo != null){
        return Result.success(loginInfo);
    }
    return Result.error("登录失败");
    }

}
