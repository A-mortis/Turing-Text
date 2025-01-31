package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staffs {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private  Integer salary;
    private LocalDate createTime;
    private LocalDateTime updateTime;
    private Integer gender;
    private String image;

   /* public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    //有参构造器
    public Staffs(Integer id, String username, String password, String name, Integer salary, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.salary = salary;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }*/

   /* public Staffs() {
    }*/
    @Override
    public String toString() {
    return "Staffs{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", salary=" + salary +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", gender='" + gender + '\'' +
            ", image='" + image + '\'' +
            '}';
    }
}




