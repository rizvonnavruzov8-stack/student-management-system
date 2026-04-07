package com.sms.service.impl;

import com.sms.model.Course;
import com.sms.model.Student;
import com.sms.service.CourseService;

import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements CourseService {

    private List<Course> allCourses;

    public CourseServiceImpl() {
        this.allCourses = new ArrayList<>();
    }

    @Override
    public void addCourse(Course course) {
        if (course != null) {
            boolean exists = false;
            for (Course c : allCourses) {
                if (c.getCourseId() == course.getCourseId()) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                allCourses.add(course);
            }
        }
    }

    @Override
    public void assignCourse(Student student, Course course) {
        if (student != null && course != null) {
            student.addCourse(course);
        }
    }
}
