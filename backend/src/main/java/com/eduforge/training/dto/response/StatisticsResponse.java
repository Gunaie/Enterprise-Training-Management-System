package com.eduforge.training.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsResponse {

    private Integer totalCourses;
    private Integer totalStudents;
    private Integer totalExams;
    private Integer totalEnrollments;
    private Double averageScore;
    private Double passRate;
    private List<ExamRecordResponse> recentExamRecords;

    @Data
    public static class ExamRecordResponse {
        private Integer id;
        private String examTitle;
        private String studentName;
        private Integer score;
        private Integer passScore;
        private Boolean isPassed;
        private String examTime;
    }
}