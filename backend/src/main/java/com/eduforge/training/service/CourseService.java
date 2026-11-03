package com.eduforge.training.service;

import com.eduforge.training.pojo.Course;
import com.eduforge.training.pojo.CourseCategory;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    Course findById(Integer id);

    List<Course> findByCategory(Integer categoryId);

    List<CourseCategory> findCategories();

    void save(Course course);

    void update(Course course);

    void delete(Integer id);
}