package com.eduforge.training.controller;

import com.eduforge.training.mapper.ExamRecordMapper;
import com.eduforge.training.pojo.Exam;
import com.eduforge.training.pojo.ExamRecord;
import com.eduforge.training.pojo.Question;
import com.eduforge.training.pojo.Result;
import com.eduforge.training.service.ExamService;
import com.eduforge.training.utils.CurrentHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private static final Logger log = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamRecordMapper examRecordMapper;

    @GetMapping
    public Result list(@RequestParam(required = false) Integer courseId) {
        log.info("查询考试列表，课程ID: {}", courseId);
        List<Exam> exams = examService.findByCourseId(courseId);
        return Result.success(exams);
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("查询考试详情，ID: {}", id);
        Exam exam = examService.findById(id);
        return Result.success(exam);
    }

    @GetMapping("/{id}/questions")
    public Result getQuestions(@PathVariable Integer id) {
        log.info("查询考试题目，考试ID: {}", id);
        List<Question> questions = examService.getExamQuestions(id);
        return Result.success(questions);
    }

    @PostMapping
    public Result save(@RequestBody Exam exam) {
        log.info("新增考试: {}", exam.getTitle());
        examService.save(exam);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Exam exam) {
        log.info("更新考试，ID: {}", exam.getId());
        examService.update(exam);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除考试，ID: {}", id);
        examService.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/submit")
    public Result submit(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        Integer currentUserId = CurrentHolder.getCurrentId();
        log.info("提交考试，用户ID: {}, 考试ID: {}", currentUserId, id);
        String answers = body.get("answers");
        ExamRecord record = examService.submitExam(currentUserId, id, answers);
        return Result.success(record);
    }

    @GetMapping("/records")
    public Result getRecords(@RequestParam Integer userId) {
        log.info("查询考试记录，用户ID: {}", userId);
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (!userId.equals(currentUserId)) {
            log.warn("用户{}尝试访问用户{}的考试记录，被拒绝", currentUserId, userId);
            return Result.error("无权访问此用户考试记录");
        }
        List<ExamRecord> records = examService.getExamRecords(userId);
        return Result.success(records);
    }

    @GetMapping("/records/me")
    public Result getMyRecords() {
        Integer currentUserId = CurrentHolder.getCurrentId();
        log.info("查询当前用户考试记录，用户ID: {}", currentUserId);
        List<ExamRecord> records = examService.getExamRecords(currentUserId);
        return Result.success(records);
    }

    @GetMapping("/exam-records")
    public Result getAllExamRecords() {
        log.info("查询所有考试记录");
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (!isAdmin(currentUserId)) {
            log.warn("非管理员用户{}尝试访问所有考试记录，被拒绝", currentUserId);
            return Result.error("无权访问所有考试记录");
        }
        List<ExamRecord> records = examRecordMapper.findAll();
        return Result.success(records);
    }

    private boolean isAdmin(Integer userId) {
        return userId != null && userId == 1;
    }
}
