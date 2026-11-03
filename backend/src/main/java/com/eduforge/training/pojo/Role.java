package com.eduforge.training.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Role {
    private Integer id;
    private String name;
    private String code;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}