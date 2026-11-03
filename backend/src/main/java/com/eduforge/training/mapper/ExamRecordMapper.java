package com.eduforge.training.mapper;

import com.eduforge.training.pojo.ExamRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ExamRecordMapper {
    List<ExamRecord> findByUserId(Integer userId);

    List<ExamRecord> findAll();

    ExamRecord findByUserIdAndExamId(Integer userId, Integer examId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ExamRecord record);

    void updateScore(ExamRecord record);

    Integer countByExamId(Integer examId);
}