package com.itheima.mapper;

import com.itheima.pojo.Resignation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResignationMapper {

    List<Resignation> page();

    @Select("select `index`, id, reason, time, status from resignation where `index` = #{index}")
    Resignation getResignationByIndex(Integer index);

    @Select("select name from staffs where id = #{id}")
    String getNameById(Integer id);

    @Select("select  `index`,id, reason, time, status from resignation where id = #{id}")
    List<Resignation> getResignationById(Integer id);


   @Insert("insert into resignation(id,reason,time) value (#{id},#{reason},#{time})")
    void add(Resignation resignation);

    void update(Resignation resignation);

    void deleteByIndex(List<Integer> indexes);

    @Select("update resignation set status = 2 where `index` = #{index}")
    void pass(Integer index);

    @Select("update resignation set status = 3 where `index` = #{index}")
    void reject(Integer index);
}
