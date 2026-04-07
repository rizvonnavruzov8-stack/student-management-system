package com.sms.service;

import com.sms.model.Course;
import com.sms.model.Student;

public interface CourseService {
    void addCourse(Course course);
    void assignCourse(Student student, Course course);
}
