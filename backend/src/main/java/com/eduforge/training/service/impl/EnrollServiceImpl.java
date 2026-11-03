package com.eduforge.training.service.impl;

import com.eduforge.training.mapper.CourseEnrollMapper;
import com.eduforge.training.mapper.LearningProgressMapper;
import com.eduforge.training.mapper.LessonMapper;
import com.eduforge.training.pojo.CourseEnroll;
import com.eduforge.training.pojo.LearningProgress;
import com.eduforge.training.service.EnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollServiceImpl implements EnrollService {

    @Autowired
    private CourseEnrollMapper enrollMapper;

    @Autowired
    private LearningProgressMapper progressMapper;

    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public List<CourseEnroll> findByUserId(Integer userId) {
        return enrollMapper.findByUserId(userId);
    }

    @Override
    public List<CourseEnroll> findAll() {
        return enrollMapper.findAll();
    }

    @Override
    public CourseEnroll findByUserIdAndCourseId(Integer userId, Integer courseId) {
        return enrollMapper.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    @Transactional
    public void enroll(Integer userId, Integer courseId) {
        CourseEnroll existing = enrollMapper.findByUserIdAndCourseId(userId, courseId);
        if (existing == null) {
            CourseEnroll enroll = new CourseEnroll();
            enroll.setUserId(userId);
            enroll.setCourseId(courseId);
            enrollMapper.insert(enroll);
        }
    }

    @Override
    @Transactional
    public void updateProgress(Integer userId, Integer lessonId) {
        LearningProgress existing = progressMapper.findByUserIdAndLessonId(userId, lessonId);
        if (existing == null) {
            LearningProgress progress = new LearningProgress();
            progress.setUserId(userId);
            progress.setLessonId(lessonId);
            progress.setIsCompleted(1);
            progress.setCompleteTime(LocalDateTime.now());
            progressMapper.insert(progress);
        } else {
            existing.setIsCompleted(1);
            existing.setCompleteTime(LocalDateTime.now());
            progressMapper.update(existing);
        }
    }

    @Override
    @Transactional
    public void completeCourse(Integer userId, Integer courseId) {
        enrollMapper.updateComplete(userId, courseId);
    }
}