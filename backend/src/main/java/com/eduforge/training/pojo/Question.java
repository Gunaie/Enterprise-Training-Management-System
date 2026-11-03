package com.eduforge.training.pojo;

public class Question {
    private Integer id;
    private Integer type;
    private String content;
    private String options;
    private String answer;
    private Integer score;
    private Integer courseId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getOptions() { return options; }
    public void setOptions(String options) { this.options = options; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
}
