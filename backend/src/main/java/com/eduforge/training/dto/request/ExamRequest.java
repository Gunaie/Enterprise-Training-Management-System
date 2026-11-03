package com.eduforge.training.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExamRequest {

    @NotBlank(message = "考试标题不能为空")
    @Size(max = 200, message = "考试标题不能超过200个字符")
    private String title;

    @Size(max = 500, message = "考试描述不能超过500个字符")
    private String description;

    @NotNull(message = "课程ID不能为空")
    private Integer courseId;

    @NotNull(message = "考试时长不能为空")
    private Integer duration;

    @NotNull(message = "及格分数不能为空")
    private Integer passScore;

    @NotNull(message = "考试状态不能为空")
    private Integer status;
}