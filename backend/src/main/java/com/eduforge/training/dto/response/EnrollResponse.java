package com.eduforge.training.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnrollResponse {

    private Integer id;
    private Integer courseId;
    private String courseTitle;
    private String courseCover;
    private Integer studentId;
    private String studentName;
    private LocalDateTime enrollTime;
    private Integer status;
    private Integer progress;
    private Boolean isCompleted;
}