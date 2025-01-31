package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private Integer id;
    private String content;
    private LocalDate createTime;
    private LocalDate updateTime;
}
