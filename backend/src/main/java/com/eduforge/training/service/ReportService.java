package com.eduforge.training.service;

import java.util.Map;

public interface ReportService {
    Map<String, Object> getDashboard();

    Map<String, Object> getStatistics();

    Map<String, Object> getCourseStatistics();

    Map<String, Object> getLearningStatistics();
}