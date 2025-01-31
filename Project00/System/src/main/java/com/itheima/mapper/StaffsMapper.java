package com.itheima.mapper;

import com.itheima.pojo.Staffs;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StaffsMapper {
     // 分页查询
    /*@Select("select count(*) from staffs s ")
    public Long count();


    @Select("select s.* from staffs s order by s.update_time desc limit #{start},#{pageSize}")
    public List<Staffs> list(Integer start, Integer pageSize);*/

     //@Select("select s.* from staffs s order by s.update_time desc ")
    public List<Staffs> list(String name, Integer gender, LocalDate createTime, LocalDate updateTime);

    @Select("select id, username, password from staffs where username = #{username} and password = #{password}")
    Staffs selectByUsernameAndPassword(Staffs staffs);



    void update(Staffs staffs);


    void deleteById(List<Integer> ids);

   /* @Delete("delete from staffs where id = #{id}")
    void deleteById(Integer id) {

    }*/

    //@Select("select id, username, password, name, salary, create_time createTime, update_time updateTime from staffs order by create_time desc")
    @Select("select id, username, password, name, salary, create_time, update_time,gender from staffs order by create_time desc")
    List<Staffs> findAll();

    @Insert("insert into staffs(username, password, name,gender, salary, create_time,update_time, image) values(#{username},#{password},#{name},#{gender},#{salary},#{createTime},#{updateTime},#{image})")
    void insert(Staffs staffs);

    @Select("select username, password, name,gender, salary, create_time, update_time,image from staffs where id = #{id}")
    Staffs getStaffsById(Integer id);

}
