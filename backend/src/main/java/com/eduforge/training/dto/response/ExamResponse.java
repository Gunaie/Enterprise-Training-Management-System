package com.eduforge.training.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamResponse {

    private Integer id;
    private String title;
    private String description;
    private Integer courseId;
    private String courseTitle;
    private Integer duration;
    private Integer passScore;
    private Integer status;
    private Integer totalQuestions;
    private LocalDateTime createTime;
    private List<QuestionResponse> questions;

    @Data
    public static class QuestionResponse {
        private Integer id;
        private Integer type;
        private String typeName;
        private String content;
        private String options;
        private Integer score;
    }
}