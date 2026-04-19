package com.sms.service;

import com.sms.exception.CourseNotFoundException;
import com.sms.model.Course;

import java.util.List;

/**
 * INTERFACE — contract for all course operations.
 * Controllers depend on this interface, not the concrete implementation.
 */
public interface CourseService {

    Course addCourse(Course course);

    Course updateCourse(int id, Course course) throws CourseNotFoundException;

    void deleteCourse(int id) throws CourseNotFoundException;

    List<Course> getAllCourses();

    Course findCourseById(int id) throws CourseNotFoundException;

    /** Called by FileManager on startup to restore persisted data */
    void loadCourses(List<Course> loaded);

    /** Returns the live list reference — used internally by EnrollmentServiceImpl */
    List<Course> getCoursesRef();
}
