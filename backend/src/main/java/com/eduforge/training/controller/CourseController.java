package com.eduforge.training.controller;

import com.eduforge.training.anno.Log;
import com.eduforge.training.pojo.Course;
import com.eduforge.training.pojo.CourseCategory;
import com.eduforge.training.pojo.Result;
import com.eduforge.training.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @GetMapping
    public Result list(@RequestParam(required = false) Integer categoryId) {
        log.info("查询课程列表，分类ID: {}", categoryId);
        List<Course> courses = categoryId != null ? courseService.findByCategory(categoryId) : courseService.findAll();
        return Result.success(courses);
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("查询课程详情，ID: {}", id);
        Course course = courseService.findById(id);
        return Result.success(course);
    }

    @GetMapping("/categories")
    public Result categories() {
        log.info("查询课程分类");
        List<CourseCategory> categories = courseService.findCategories();
        return Result.success(categories);
    }

    @Log
    @PostMapping
    public Result save(@RequestBody Course course) {
        log.info("新增课程: {}", course.getTitle());
        courseService.save(course);
        return Result.success();
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Course course) {
        log.info("更新课程，ID: {}", course.getId());
        courseService.update(course);
        return Result.success();
    }

    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除课程，ID: {}", id);
        courseService.delete(id);
        return Result.success();
    }
}