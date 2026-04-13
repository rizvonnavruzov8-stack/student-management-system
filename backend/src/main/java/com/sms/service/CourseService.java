package com.sms.service;

import com.sms.exception.CourseNotFoundException;
import com.sms.model.Course;

import java.util.List;

/**
 * INTERFACE — contract for all course operations.
 */
public interface CourseService {

    Course addCourse(Course course);

    List<Course> getAllCourses();

    Course findCourseById(int id) throws CourseNotFoundException;
}
