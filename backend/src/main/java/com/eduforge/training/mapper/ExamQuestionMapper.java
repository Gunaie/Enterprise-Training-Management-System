package com.eduforge.training.mapper;

import com.eduforge.training.pojo.ExamQuestion;
import com.eduforge.training.pojo.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamQuestionMapper {
    void insert(ExamQuestion examQuestion);

    void deleteByExamId(Integer examId);

    List<Question> findQuestionsByExamId(Integer examId);
}