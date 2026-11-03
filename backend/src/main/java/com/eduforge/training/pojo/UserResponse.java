package com.eduforge.training.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Integer id;
    private String username;
    private String name;
    private Integer gender;
    private String phone;
    private String email;
    private String avatar;
    private Integer status;
    private LocalDateTime createTime;

    public UserResponse() {}

    public UserResponse(SysUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.status = user.getStatus();
        this.createTime = user.getCreateTime();
    }
}
