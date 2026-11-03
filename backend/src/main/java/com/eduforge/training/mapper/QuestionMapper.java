package com.eduforge.training.mapper;

import com.eduforge.training.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface QuestionMapper {
    List<Question> findByCourseId(Integer courseId);

    Question findById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Question question);

    void update(Question question);

    void delete(Integer id);
}