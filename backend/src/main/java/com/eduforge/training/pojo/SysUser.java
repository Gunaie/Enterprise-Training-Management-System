package com.eduforge.training.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer gender;
    private String phone;
    private String email;
    private String avatar;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
