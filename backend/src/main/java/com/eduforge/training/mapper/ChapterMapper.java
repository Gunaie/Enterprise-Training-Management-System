package com.eduforge.training.mapper;

import com.eduforge.training.pojo.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ChapterMapper {
    List<Chapter> findByCourseId(Integer courseId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Chapter chapter);

    void update(Chapter chapter);

    void delete(Integer id);

    void deleteByCourseId(Integer courseId);
}