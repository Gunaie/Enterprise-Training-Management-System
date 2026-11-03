package com.eduforge.training.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CourseResponse {

    private Integer id;
    private String title;
    private String description;
    private Integer categoryId;
    private String categoryName;
    private String coverImage;
    private Integer duration;
    private Integer status;
    private Integer totalStudents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<ChapterResponse> chapters;

    @Data
    public static class ChapterResponse {
        private Integer id;
        private String title;
        private Integer orderNum;
        private List<LessonResponse> lessons;
    }

    @Data
    public static class LessonResponse {
        private Integer id;
        private String title;
        private String videoUrl;
        private Integer duration;
        private Integer orderNum;
    }
}