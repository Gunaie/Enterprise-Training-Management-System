package com.eduforge.training.controller;

import com.eduforge.training.pojo.CourseEnroll;
import com.eduforge.training.pojo.Result;
import com.eduforge.training.service.EnrollService;
import com.eduforge.training.utils.CurrentHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollController {

    private static final Logger log = LoggerFactory.getLogger(EnrollController.class);

    @Autowired
    private EnrollService enrollService;

    @GetMapping
    public Result list() {
        log.info("查询所有报名列表");
        List<CourseEnroll> enrolls = enrollService.findAll();
        return Result.success(enrolls);
    }

    @GetMapping("/user/{userId}")
    public Result listByUserId(@PathVariable Integer userId) {
        log.info("查询用户报名列表，用户ID: {}", userId);
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (!userId.equals(currentUserId)) {
            log.warn("用户{}尝试访问用户{}的报名信息，被拒绝", currentUserId, userId);
            return Result.error("无权访问此用户报名信息");
        }
        List<CourseEnroll> enrolls = enrollService.findByUserId(userId);
        return Result.success(enrolls);
    }

    @GetMapping("/user/me")
    public Result listMyEnrollments() {
        Integer currentUserId = CurrentHolder.getCurrentId();
        log.info("查询当前用户报名列表，用户ID: {}", currentUserId);
        List<CourseEnroll> enrolls = enrollService.findByUserId(currentUserId);
        return Result.success(enrolls);
    }

    @GetMapping("/{userId}/{courseId}")
    public Result getInfo(@PathVariable Integer userId, @PathVariable Integer courseId) {
        log.info("查询报名详情，用户ID: {}, 课程ID: {}", userId, courseId);
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (!userId.equals(currentUserId)) {
            log.warn("用户{}尝试访问用户{}的报名详情，被拒绝", currentUserId, userId);
            return Result.error("无权访问此报名信息");
        }
        CourseEnroll enroll = enrollService.findByUserIdAndCourseId(userId, courseId);
        return Result.success(enroll);
    }

    @PostMapping
    public Result enroll(@RequestBody CourseEnroll enroll) {
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (!currentUserId.equals(enroll.getUserId())) {
            log.warn("用户{}尝试替用户{}报名课程，被拒绝", currentUserId, enroll.getUserId());
            return Result.error("无权替他人报名");
        }
        log.info("报名课程，用户ID: {}, 课程ID: {}", enroll.getUserId(), enroll.getCourseId());
        enrollService.enroll(enroll.getUserId(), enroll.getCourseId());
        return Result.success();
    }

    @PutMapping("/progress")
    public Result updateProgress(@RequestParam Integer lessonId) {
        Integer currentUserId = CurrentHolder.getCurrentId();
        log.info("更新学习进度，用户ID: {}, 课时ID: {}", currentUserId, lessonId);
        enrollService.updateProgress(currentUserId, lessonId);
        return Result.success();
    }

    @PutMapping("/complete/{courseId}")
    public Result completeCourse(@PathVariable Integer courseId) {
        Integer currentUserId = CurrentHolder.getCurrentId();
        log.info("完成课程，用户ID: {}, 课程ID: {}", currentUserId, courseId);
        enrollService.completeCourse(currentUserId, courseId);
        return Result.success();
    }
}
