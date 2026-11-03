package com.eduforge.training.mapper;

import com.eduforge.training.pojo.CourseCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseCategoryMapper {
    List<CourseCategory> findAll();
}