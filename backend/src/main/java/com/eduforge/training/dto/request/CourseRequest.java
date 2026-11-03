package com.eduforge.training.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseRequest {

    @NotBlank(message = "课程标题不能为空")
    @Size(max = 200, message = "课程标题不能超过200个字符")
    private String title;

    @Size(max = 500, message = "课程描述不能超过500个字符")
    private String description;

    @NotNull(message = "课程分类ID不能为空")
    private Integer categoryId;

    private String coverImage;

    @NotNull(message = "课程时长不能为空")
    private Integer duration;

    @NotNull(message = "课程状态不能为空")
    private Integer status;
}