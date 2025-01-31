package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resignation {
    private Integer index;
    private Integer id;
    private String reason;
    private String time;
    private Integer status;
}
