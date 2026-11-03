package com.eduforge.training.mapper;

import com.eduforge.training.pojo.LearningProgress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LearningProgressMapper {
    List<LearningProgress> findByUserId(Integer userId);

    LearningProgress findByUserIdAndLessonId(Integer userId, Integer lessonId);

    void insert(LearningProgress progress);

    void update(LearningProgress progress);

    Integer countCompletedLessons(Integer userId, Integer courseId);
}