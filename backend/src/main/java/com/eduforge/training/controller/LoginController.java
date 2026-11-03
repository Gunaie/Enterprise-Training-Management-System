package com.eduforge.training.controller;

import com.eduforge.training.dto.request.LoginRequest;
import com.eduforge.training.pojo.LoginInfo;
import com.eduforge.training.pojo.Result;
import com.eduforge.training.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request) {
        log.info("登录请求，用户名: {}", request.getUsername());
        LoginInfo info = userService.login(request.getUsername(), request.getPassword());
        if (info != null) {
            return Result.success(info);
        }
        return Result.error("用户名或密码错误");
    }
}
