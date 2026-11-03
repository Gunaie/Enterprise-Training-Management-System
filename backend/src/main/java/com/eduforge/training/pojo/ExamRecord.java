package com.eduforge.training.pojo;

import java.time.LocalDateTime;

public class ExamRecord {
    private Integer id;
    private Integer examId;
    private Integer userId;
    private Integer studentId;
    private String studentName;
    private LocalDateTime examTime;
    private String answers;
    private Double score;
    private Integer isPassed;
    private LocalDateTime submitTime;
    private String examTitle;
    private Integer totalScore;
    private Integer passScore;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public LocalDateTime getExamTime() { return examTime; }
    public void setExamTime(LocalDateTime examTime) { this.examTime = examTime; }
    public String getAnswers() { return answers; }
    public void setAnswers(String answers) { this.answers = answers; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public Integer getIsPassed() { return isPassed; }
    public void setIsPassed(Integer isPassed) { this.isPassed = isPassed; }
    public LocalDateTime getSubmitTime() { return submitTime; }
    public void setSubmitTime(LocalDateTime submitTime) { this.submitTime = submitTime; }
    public String getExamTitle() { return examTitle; }
    public void setExamTitle(String examTitle) { this.examTitle = examTitle; }
    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }
    public Integer getPassScore() { return passScore; }
    public void setPassScore(Integer passScore) { this.passScore = passScore; }
}
