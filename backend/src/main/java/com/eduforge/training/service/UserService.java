package com.eduforge.training.service;

import com.eduforge.training.pojo.LoginInfo;
import com.eduforge.training.pojo.SysUser;

import java.util.List;

public interface UserService {

    List<SysUser> findAll();

    SysUser findById(Integer id);

    LoginInfo login(String username, String password);
}
