package com.eduforge.training.service.impl;

import com.eduforge.training.mapper.QuestionMapper;
import com.eduforge.training.pojo.Question;
import com.eduforge.training.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Question> findByCourseId(Integer courseId) {
        return questionMapper.findByCourseId(courseId);
    }

    @Override
    public Question findById(Integer id) {
        return questionMapper.findById(id);
    }

    @Override
    public void save(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public void update(Question question) {
        questionMapper.update(question);
    }

    @Override
    public void delete(Integer id) {
        questionMapper.delete(id);
    }
}