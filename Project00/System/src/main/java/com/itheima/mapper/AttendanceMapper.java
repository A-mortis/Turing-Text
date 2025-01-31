package com.itheima.mapper;

import com.itheima.pojo.Attendance;
import com.itheima.pojo.Leave;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AttendanceMapper {
    @Insert("insert into attendance(username, create_time) values(#{username}, #{createTime})")
    void insert(Attendance attendance);

    List<Attendance> list(String username, LocalDate createTime, LocalDate updateTime);

    @Select("select username, create_time from attendance where username = #{username}")
    List<Attendance> getAttendanceByUsername(String username);
}
