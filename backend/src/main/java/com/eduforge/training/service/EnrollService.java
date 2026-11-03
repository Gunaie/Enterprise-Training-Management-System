package com.eduforge.training.service;

import com.eduforge.training.pojo.CourseEnroll;

import java.util.List;

public interface EnrollService {
    List<CourseEnroll> findByUserId(Integer userId);

    List<CourseEnroll> findAll();

    CourseEnroll findByUserIdAndCourseId(Integer userId, Integer courseId);

    void enroll(Integer userId, Integer courseId);

    void updateProgress(Integer userId, Integer lessonId);

    void completeCourse(Integer userId, Integer courseId);
}