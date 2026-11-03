package com.eduforge.training.service;

import com.eduforge.training.pojo.Exam;
import com.eduforge.training.pojo.ExamRecord;
import com.eduforge.training.pojo.Question;

import java.util.List;

public interface ExamService {
    List<Exam> findByCourseId(Integer courseId);

    Exam findById(Integer id);

    void save(Exam exam);

    void update(Exam exam);

    void delete(Integer id);

    List<Question> getExamQuestions(Integer examId);

    ExamRecord submitExam(Integer userId, Integer examId, String answers);

    List<ExamRecord> getExamRecords(Integer userId);
}