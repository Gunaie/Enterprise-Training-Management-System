package com.eduforge.training.service;

import com.eduforge.training.pojo.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findByCourseId(Integer courseId);

    Question findById(Integer id);

    void save(Question question);

    void update(Question question);

    void delete(Integer id);
}