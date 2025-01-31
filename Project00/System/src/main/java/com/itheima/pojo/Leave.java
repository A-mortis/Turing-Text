package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Leave {
    private Integer index;
    private Integer id;
    private String reason;
    private String time;
    private Integer status;
}


