package com.eduforge.training.pojo;

import java.time.LocalDateTime;

public class CourseEnroll {
    private Integer id;
    private Integer userId;
    private Integer courseId;
    private Integer progress;
    private Integer isCompleted;
    private LocalDateTime enrollTime;
    private LocalDateTime completeTime;
    private String courseTitle;
    private String coverImage;
    private String studentName;
    private Integer status;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public Integer getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Integer isCompleted) { this.isCompleted = isCompleted; }
    public LocalDateTime getEnrollTime() { return enrollTime; }
    public void setEnrollTime(LocalDateTime enrollTime) { this.enrollTime = enrollTime; }
    public LocalDateTime getCompleteTime() { return completeTime; }
    public void setCompleteTime(LocalDateTime completeTime) { this.completeTime = completeTime; }
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
