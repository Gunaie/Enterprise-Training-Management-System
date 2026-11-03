package com.eduforge.training.controller;

import com.eduforge.training.mapper.ExamRecordMapper;
import com.eduforge.training.pojo.ExamRecord;
import com.eduforge.training.pojo.Result;
import com.eduforge.training.utils.CurrentHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExamRecordController {

    private static final Logger log = LoggerFactory.getLogger(ExamRecordController.class);

    @Autowired
    private ExamRecordMapper examRecordMapper;

    @GetMapping("/exam-records")
    public Result list(@RequestParam(required = false) Integer userId) {
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (userId != null) {
            log.info("查询用户考试记录，用户ID: {}", userId);
            if (!userId.equals(currentUserId) && !isAdmin(currentUserId)) {
                log.warn("用户{}尝试访问用户{}的考试记录，被拒绝", currentUserId, userId);
                return Result.error("无权访问此用户考试记录");
            }
            List<ExamRecord> records = examRecordMapper.findByUserId(userId);
            return Result.success(records);
        }
        log.info("查询所有考试记录");
        if (!isAdmin(currentUserId)) {
            log.warn("非管理员用户{}尝试访问所有考试记录，被拒绝", currentUserId);
            return Result.error("无权访问所有考试记录");
        }
        List<ExamRecord> records = examRecordMapper.findAll();
        return Result.success(records);
    }

    private boolean isAdmin(Integer userId) {
        return userId != null && userId == 1;
    }
}
