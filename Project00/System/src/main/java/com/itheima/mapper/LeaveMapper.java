package com.itheima.mapper;

import com.itheima.pojo.Leave;
import com.itheima.pojo.PageResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LeaveMapper {

    @Select("select `index`,id,reason,time,status from `leave` where id = #{id}")
    List<Leave> getLeaveById(Integer id);

    @Insert("insert into `leave`(id,reason,time) value (#{id},#{reason},#{time})")
    void insert(Leave leave);

     @Select("select id,reason,time,status from `leave` where `index` = #{index}")
    Leave getLeaveByIndex(Integer index);

    void update(Leave leave);

    void deleteByIndex(List<Integer> indexes);

    @Select("select name from staffs where id = #{id}")
    String getNameById(Integer id);


    List<Leave> page();

    // 通过index修改状态，更新status为2
    @Select("update `leave` set status = 2 where `index` = #{index}")
    void pass(Integer index);

    @Select("update `leave` set status = 3 where `index` = #{index}")
    void reject(Integer index);
}
