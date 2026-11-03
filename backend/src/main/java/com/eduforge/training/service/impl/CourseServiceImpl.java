package com.eduforge.training.service.impl;

import com.eduforge.training.mapper.ChapterMapper;
import com.eduforge.training.mapper.CourseCategoryMapper;
import com.eduforge.training.mapper.CourseMapper;
import com.eduforge.training.mapper.LessonMapper;
import com.eduforge.training.pojo.Chapter;
import com.eduforge.training.pojo.Course;
import com.eduforge.training.pojo.CourseCategory;
import com.eduforge.training.pojo.Lesson;
import com.eduforge.training.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private LessonMapper lessonMapper;

    @Autowired
    private CourseCategoryMapper categoryMapper;

    @Override
    public List<Course> findAll() {
        List<Course> courses = courseMapper.findAll();
        courses.forEach(this::loadChaptersAndLessons);
        return courses;
    }

    @Override
    public Course findById(Integer id) {
        Course course = courseMapper.findById(id);
        if (course != null) {
            loadChaptersAndLessons(course);
        }
        return course;
    }

    @Override
    public List<Course> findByCategory(Integer categoryId) {
        List<Course> courses = courseMapper.findByCategory(categoryId);
        courses.forEach(this::loadChaptersAndLessons);
        return courses;
    }

    @Override
    public List<CourseCategory> findCategories() {
        return categoryMapper.findAll();
    }

    @Override
    @Transactional
    public void save(Course course) {
        courseMapper.insert(course);
        if (course.getChapters() != null) {
            for (Chapter chapter : course.getChapters()) {
                chapter.setCourseId(course.getId());
                chapterMapper.insert(chapter);
                if (chapter.getLessons() != null) {
                    for (Lesson lesson : chapter.getLessons()) {
                        lesson.setChapterId(chapter.getId());
                        lessonMapper.insert(lesson);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void update(Course course) {
        courseMapper.update(course);
        chapterMapper.deleteByCourseId(course.getId());
        if (course.getChapters() != null) {
            for (Chapter chapter : course.getChapters()) {
                chapter.setCourseId(course.getId());
                chapterMapper.insert(chapter);
                if (chapter.getLessons() != null) {
                    for (Lesson lesson : chapter.getLessons()) {
                        lesson.setChapterId(chapter.getId());
                        lessonMapper.insert(lesson);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        courseMapper.delete(id);
    }

    private void loadChaptersAndLessons(Course course) {
        List<Chapter> chapters = chapterMapper.findByCourseId(course.getId());
        chapters.forEach(chapter -> {
            List<Lesson> lessons = lessonMapper.findByChapterId(chapter.getId());
            chapter.setLessons(lessons);
        });
        course.setChapters(chapters);
    }
}