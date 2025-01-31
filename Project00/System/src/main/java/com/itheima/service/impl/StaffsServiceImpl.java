package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.StaffsMapper;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Staffs;
import com.itheima.service.StaffsService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StaffsServiceImpl implements StaffsService {


    @Autowired
    private StaffsMapper staffsMapper;
    @Override
    public List<Staffs> findAll() {
        return staffsMapper.findAll();
    }

    @Override
    public LoginInfo login(Staffs staffs) {
        Staffs s = staffsMapper.selectByUsernameAndPassword(staffs);
        if (s != null){
            log.info("登录成功");
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",s.getId());
            claims.put("username",s.getUsername());
            String jwt = JwtUtils.generateToken(claims);
         return new LoginInfo(s.getId(),s.getUsername(),s.getPassword(),jwt);
        }
        return null;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteById(List<Integer> ids) {
        staffsMapper.deleteById(ids);
        log.info("删除成功");
    }

    @Transactional
    @Override
    public void add(Staffs staffs) {
        staffs.setCreateTime(LocalDate.now());
        staffs.setUpdateTime(LocalDateTime.now());
        staffsMapper.insert(staffs);
    }

    //原始分页查询
    /*@Override
    public PageResult<Staffs> page(Integer page, Integer pageSize) {
         Long total = staffsMapper.count();
         Integer start = (page-1)*pageSize;
         List<Staffs> rows =staffsMapper.list(start,pageSize);
        return new PageResult<Staffs>(total,rows);
    }*/

    @Override
    public PageResult<Staffs> page(Integer page, Integer pageSize , String name, Integer gender, LocalDate createTime, LocalDate updateTime) {
       //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        List<Staffs> staffsList = staffsMapper.list(name, gender, createTime, updateTime);

        Page<Staffs> p = (Page<Staffs>) staffsList;

        return new PageResult<Staffs>(p.getTotal(), p.getResult());
    }

    @Override
    public void update(Staffs staffs) {
        staffs.setUpdateTime(LocalDateTime.now());
        staffsMapper.update(staffs);

        //staffsMapper.deleteById();
    }

    @Override
    public Staffs getStaffsById(Integer id) {
        return staffsMapper.getStaffsById(id);
    }
}
