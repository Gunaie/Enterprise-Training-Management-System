package com.eduforge.training.mapper;

import com.eduforge.training.pojo.CourseEnroll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface CourseEnrollMapper {
    List<CourseEnroll> findByUserId(Integer userId);

    List<CourseEnroll> findAll();

    CourseEnroll findByUserIdAndCourseId(Integer userId, Integer courseId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(CourseEnroll enroll);

    void updateProgress(CourseEnroll enroll);

    void updateComplete(Integer userId, Integer courseId);

    void delete(Integer id);

    Integer countByCourseId(Integer courseId);
}