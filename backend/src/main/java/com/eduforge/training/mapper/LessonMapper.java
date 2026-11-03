package com.eduforge.training.mapper;

import com.eduforge.training.pojo.Lesson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface LessonMapper {
    List<Lesson> findByChapterId(Integer chapterId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Lesson lesson);

    void update(Lesson lesson);

    void delete(Integer id);

    void deleteByChapterId(Integer chapterId);

    Integer countByCourseId(Integer courseId);
}