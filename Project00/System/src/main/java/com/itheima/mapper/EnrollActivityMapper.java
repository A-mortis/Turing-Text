package com.itheima.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EnrollActivityMapper {

    @Insert("insert into staff_activity(staff_id, activity_id) values(#{staffId}, #{activityId})")
    void enroll(Integer staffId, Integer activityId);

    @Delete("delete from staff_activity where staff_id = #{staffId} and activity_id = #{activityId}")
    void cancel(Integer staffId, Integer activityId);

    @Select("select id from staff_activity where staff_id = #{staffId} and activity_id = #{activityId}")
    void getStatus(Integer staffId, Integer activityId);
}
