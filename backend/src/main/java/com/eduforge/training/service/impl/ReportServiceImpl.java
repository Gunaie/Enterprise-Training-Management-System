package com.eduforge.training.service.impl;

import com.eduforge.training.mapper.CourseEnrollMapper;
import com.eduforge.training.mapper.CourseMapper;
import com.eduforge.training.mapper.ExamMapper;
import com.eduforge.training.mapper.ExamRecordMapper;
import com.eduforge.training.mapper.LearningProgressMapper;
import com.eduforge.training.mapper.SysUserMapper;
import com.eduforge.training.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseEnrollMapper enrollMapper;

    @Autowired
    private LearningProgressMapper progressMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamRecordMapper examRecordMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public Map<String, Object> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalCourses", courseMapper.findAll().size());
        dashboard.put("totalStudents", userMapper.findAll().size());
        dashboard.put("totalEnrollments", enrollMapper.findAll().size());
        dashboard.put("totalExams", examMapper.findByCourseId(null).size());
        return dashboard;
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCourses", courseMapper.findAll().size());
        stats.put("totalStudents", userMapper.findAll().size());
        stats.put("totalEnrollments", enrollMapper.findAll().size());
        stats.put("totalExams", examMapper.findByCourseId(null).size());
        return stats;
    }

    @Override
    public Map<String, Object> getCourseStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("courses", courseMapper.findAll());
        return stats;
    }

    @Override
    public Map<String, Object> getLearningStatistics() {
        Map<String, Object> stats = new HashMap<>();
        return stats;
    }
}
