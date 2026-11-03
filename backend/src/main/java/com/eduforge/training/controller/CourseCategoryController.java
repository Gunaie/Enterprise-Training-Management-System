package com.eduforge.training.controller;

import com.eduforge.training.pojo.CourseCategory;
import com.eduforge.training.pojo.Result;
import com.eduforge.training.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseCategoryController {

    private static final Logger log = LoggerFactory.getLogger(CourseCategoryController.class);

    @Autowired
    private CourseService courseService;

    @GetMapping("/course-categories")
    public Result list() {
        log.info("查询课程分类");
        List<CourseCategory> categories = courseService.findCategories();
        return Result.success(categories);
    }
}