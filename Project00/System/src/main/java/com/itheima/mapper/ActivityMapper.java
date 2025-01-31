package com.itheima.mapper;


import com.itheima.pojo.Activity;
import com.itheima.pojo.Staffs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ActivityMapper {

    List<Activity> list(String content, LocalDate createTime, LocalDate updateTime);

    @Insert("insert into activity(content , create_time,update_time) values(#{content}, #{createTime},#{updateTime})")
    void insert(Activity activity);

    void deleteById(List<Integer> ids);

    void update(Activity activity);

    @Select("select id, content, create_time,update_time from activity where id = #{id}")
    Activity getActivityById(Integer id);

    @Select("select  username, password, name from staffs where id in (select staff_id from staff_activity where staff_activity.activity_id = #{id})")
    List<Staffs> getStaffsById(Integer id);
}
