package com.sms.service.impl;

import com.sms.exception.CourseNotFoundException;
import com.sms.model.Course;
import com.sms.service.CourseService;
import com.sms.util.FileManager;
import com.sms.util.ActivityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * POLYMORPHISM — Implements CourseService interface.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final List<Course> courses = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @Autowired private FileManager fileManager;
    @Autowired private ActivityLogger activityLogger;

    @Override
    public Course addCourse(Course course) {
        course.setId(idCounter.getAndIncrement());
        courses.add(course);
        fileManager.saveCourses(courses);
        activityLogger.log("ADD_COURSE", "Added: " + course.getSummary());
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    @Override
    public Course findCourseById(int id) throws CourseNotFoundException {
        return courses.stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .orElseThrow(() -> new CourseNotFoundException(id));
    }

    public void loadCourses(List<Course> loaded) {
        courses.clear();
        courses.addAll(loaded);
        int maxId = loaded.stream().mapToInt(com.sms.model.BaseEntity::getId).max().orElse(0);
        idCounter.set(maxId + 1);
    }

    public List<Course> getCoursesRef() {
        return courses;
    }
}
