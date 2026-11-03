package com.eduforge.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class TrainingPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainingPlatformApplication.class, (String[])args);
    }
}
