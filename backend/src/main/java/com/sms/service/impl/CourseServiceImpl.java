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
 * The controller and FileManager depend only on the interface, not this class.
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
    public Course updateCourse(int id, Course updated) throws CourseNotFoundException {
        Course existing = findCourseById(id);
        existing.setCourseId(updated.getCourseId());
        existing.setCourseName(updated.getCourseName());
        existing.setCourseCode(updated.getCourseCode());
        existing.setCredits(updated.getCredits());
        existing.setInstructorName(updated.getInstructorName());
        existing.touch(); // update audit timestamp
        fileManager.saveCourses(courses);
        activityLogger.log("UPDATE_COURSE", "Updated ID=" + id);
        return existing;
    }

    @Override
    public void deleteCourse(int id) throws CourseNotFoundException {
        Course course = findCourseById(id);
        courses.remove(course);
        fileManager.saveCourses(courses);
        activityLogger.log("DELETE_COURSE", "Deleted ID=" + id);
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

    @Override
    public void loadCourses(List<Course> loaded) {
        courses.clear();
        courses.addAll(loaded);
        int maxId = loaded.stream().mapToInt(com.sms.model.BaseEntity::getId).max().orElse(0);
        idCounter.set(maxId + 1);
    }

    @Override
    public List<Course> getCoursesRef() {
        return courses;
    }
}
