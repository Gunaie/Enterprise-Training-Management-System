package com.eduforge.training.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.eduforge.training.mapper.ExamMapper;
import com.eduforge.training.mapper.ExamQuestionMapper;
import com.eduforge.training.mapper.ExamRecordMapper;
import com.eduforge.training.pojo.Exam;
import com.eduforge.training.pojo.ExamRecord;
import com.eduforge.training.pojo.Question;
import com.eduforge.training.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Autowired
    private ExamRecordMapper examRecordMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Exam> findByCourseId(Integer courseId) {
        return examMapper.findByCourseId(courseId);
    }

    @Override
    public Exam findById(Integer id) {
        return examMapper.findById(id);
    }

    @Override
    @Transactional
    public void save(Exam exam) {
        examMapper.insert(exam);
    }

    @Override
    @Transactional
    public void update(Exam exam) {
        examMapper.update(exam);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        examQuestionMapper.deleteByExamId(id);
        examMapper.delete(id);
    }

    @Override
    public List<Question> getExamQuestions(Integer examId) {
        return examQuestionMapper.findQuestionsByExamId(examId);
    }

    @Override
    @Transactional
    public ExamRecord submitExam(Integer userId, Integer examId, String answers) {
        Exam exam = examMapper.findById(examId);
        double score = gradeExam(examId, answers);
        int isPassed = score >= exam.getPassScore() ? 1 : 0;

        ExamRecord record = new ExamRecord();
        record.setExamId(examId);
        record.setUserId(userId);
        record.setAnswers(answers);
        record.setScore(score);
        record.setIsPassed(isPassed);
        examRecordMapper.insert(record);

        return record;
    }

    @Override
    public List<ExamRecord> getExamRecords(Integer userId) {
        return examRecordMapper.findByUserId(userId);
    }

    private double gradeExam(Integer examId, String answers) {
        List<Question> questions = examQuestionMapper.findQuestionsByExamId(examId);
        double totalScore = 0;

        try {
            Map<String, String> answerMap = objectMapper.readValue(answers, new TypeReference<Map<String, String>>() {});
            for (Question question : questions) {
                String userAnswer = answerMap.get(String.valueOf(question.getId()));
                if (userAnswer != null && userAnswer.equals(question.getAnswer())) {
                    totalScore += question.getScore();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalScore;
    }
}