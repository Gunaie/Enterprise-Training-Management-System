package com.eduforge.training.controller;

import com.eduforge.training.pojo.Result;
import com.eduforge.training.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @GetMapping("/statistics")
    public Result getStatistics() {
        log.info("获取统计数据");
        Map<String, Object> stats = reportService.getStatistics();
        return Result.success(stats);
    }

    @GetMapping("/dashboard")
    public Result getDashboard() {
        log.info("获取仪表盘数据");
        Map<String, Object> dashboard = reportService.getDashboard();
        return Result.success(dashboard);
    }

    @GetMapping("/courseStatistics")
    public Result getCourseStatistics() {
        log.info("获取课程统计数据");
        Map<String, Object> stats = reportService.getCourseStatistics();
        return Result.success(stats);
    }

    @GetMapping("/learningStatistics")
    public Result getLearningStatistics() {
        log.info("获取学习统计数据");
        Map<String, Object> stats = reportService.getLearningStatistics();
        return Result.success(stats);
    }
}