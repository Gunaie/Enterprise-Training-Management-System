package com.eduforge.training.controller;

import com.eduforge.training.mapper.ExamRecordMapper;
import com.eduforge.training.pojo.ExamRecord;
import com.eduforge.training.pojo.SysUser;
import com.eduforge.training.service.UserService;
import com.eduforge.training.util.ExcelExportUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/export")
@Tag(name = "数据导出", description = "数据导出相关接口")
public class ExportController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExamRecordMapper examRecordMapper;

    @GetMapping("/students")
    @Operation(summary = "导出学员列表", description = "将学员列表导出为Excel文件")
    public ResponseEntity<byte[]> exportStudents() throws IOException {
        List<SysUser> users = userService.findAll();

        String[] headers = {"ID", "用户名", "姓名", "手机号", "邮箱", "状态", "注册时间"};

        byte[] excelData = ExcelExportUtil.exportExcel("学员列表", headers, users, user -> new Object[]{
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getStatus() != null && user.getStatus() == 1 ? "正常" : "停用",
                user.getCreateTime()
        });

        String filename = "学员列表_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }

    @GetMapping("/exam-records")
    @Operation(summary = "导出考试记录", description = "将考试记录导出为Excel文件")
    public ResponseEntity<byte[]> exportExamRecords() throws IOException {
        List<ExamRecord> records = examRecordMapper.findAll();

        String[] headers = {"ID", "考试ID", "考试名称", "学员ID", "学员姓名", "成绩", "及格分", "是否及格", "考试时间"};

        byte[] excelData = ExcelExportUtil.exportExcel("考试记录", headers, records, record -> new Object[]{
                record.getId(),
                record.getExamId(),
                record.getExamTitle(),
                record.getStudentId(),
                record.getStudentName(),
                record.getScore(),
                record.getPassScore(),
                record.getIsPassed() == 1 ? "是" : "否",
                record.getExamTime()
        });

        String filename = "考试记录_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }

    @GetMapping("/enrollments")
    @Operation(summary = "导出报名记录", description = "将报名记录导出为Excel文件")
    public ResponseEntity<byte[]> exportEnrollments() throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + URLEncoder.encode("报名记录.xlsx", StandardCharsets.UTF_8))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new byte[0]);
    }
}
