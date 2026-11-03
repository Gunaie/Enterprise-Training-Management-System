package com.eduforge.training.mapper;

import com.eduforge.training.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> findAll();

    Course findById(Integer id);

    List<Course> findByCategory(Integer categoryId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Course course);

    void update(Course course);

    void delete(Integer id);

    @Select("SELECT COUNT(*) FROM course_enroll WHERE course_id = #{courseId}")
    Integer countEnrollments(Integer courseId);

    void updateTotalStudents(Integer courseId);
}