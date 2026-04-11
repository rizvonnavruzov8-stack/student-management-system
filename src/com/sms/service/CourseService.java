package com.sms.service;

import com.sms.model.Course;
import com.sms.model.Student;
import java.util.List;

public interface CourseService {
    void addCourse(Course course);
    void assignCourse(Student student, Course course);
    List<Course> getAllCourses();
    void deleteCourse(int id);
}
