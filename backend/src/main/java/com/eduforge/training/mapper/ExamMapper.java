package com.eduforge.training.mapper;

import com.eduforge.training.pojo.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ExamMapper {
    List<Exam> findByCourseId(Integer courseId);

    Exam findById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Exam exam);

    void update(Exam exam);

    void delete(Integer id);
}