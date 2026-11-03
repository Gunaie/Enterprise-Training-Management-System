package com.eduforge.training.controller;

import com.eduforge.training.pojo.Result;
import com.eduforge.training.pojo.SysUser;
import com.eduforge.training.pojo.UserResponse;
import com.eduforge.training.service.UserService;
import com.eduforge.training.utils.CurrentHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public Result list() {
        log.info("查询学员列表");
        List<SysUser> users = userService.findAll();
        List<UserResponse> response = users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
        return Result.success(response);
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("查询学员详情，ID: {}", id);
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (!id.equals(currentUserId)) {
            log.warn("用户{}尝试访问用户{}的信息，被拒绝", currentUserId, id);
            return Result.error("无权访问此用户信息");
        }
        SysUser user = userService.findById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(new UserResponse(user));
    }

    @GetMapping("/me")
    public Result getCurrentUser() {
        Integer currentUserId = CurrentHolder.getCurrentId();
        log.info("查询当前登录用户信息，ID: {}", currentUserId);
        SysUser user = userService.findById(currentUserId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(new UserResponse(user));
    }
}
