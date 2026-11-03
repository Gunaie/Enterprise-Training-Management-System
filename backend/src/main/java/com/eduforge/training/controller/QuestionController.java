package com.eduforge.training.controller;

import com.eduforge.training.pojo.Question;
import com.eduforge.training.pojo.Result;
import com.eduforge.training.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public Result list(@RequestParam Integer courseId) {
        log.info("查询题目列表，课程ID: {}", courseId);
        List<Question> questions = questionService.findByCourseId(courseId);
        return Result.success(questions);
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("查询题目详情，ID: {}", id);
        Question question = questionService.findById(id);
        return Result.success(question);
    }

    @PostMapping
    public Result save(@RequestBody Question question) {
        log.info("新增题目");
        questionService.save(question);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Question question) {
        log.info("更新题目，ID: {}", question.getId());
        questionService.update(question);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除题目，ID: {}", id);
        questionService.delete(id);
        return Result.success();
    }
}