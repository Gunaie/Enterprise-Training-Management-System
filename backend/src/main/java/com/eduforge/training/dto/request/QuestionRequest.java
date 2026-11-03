package com.eduforge.training.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionRequest {

    @NotNull(message = "考试ID不能为空")
    private Integer examId;

    @NotNull(message = "题目类型不能为空")
    private Integer type;

    @NotBlank(message = "题目内容不能为空")
    @Size(max = 1000, message = "题目内容不能超过1000个字符")
    private String content;

    @Size(max = 1000, message = "选项不能超过1000个字符")
    private String options;

    @NotBlank(message = "正确答案不能为空")
    @Size(max = 100, message = "正确答案不能超过100个字符")
    private String answer;

    @NotNull(message = "题目分数不能为空")
    private Integer score;
}