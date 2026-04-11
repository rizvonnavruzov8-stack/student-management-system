package com.sms.service.impl;

import com.sms.model.Course;
import com.sms.model.Student;
import com.sms.service.CourseService;
import java.util.ArrayList;
import java.util.List;

/**
 * 🎓 EXPLANATION: CourseServiceImpl (Business Logic Implementation)
 * 
 * 1. WHY IT EXISTS:
 *    It provides the actual logic for course management.
 * 
 * 2. LIST USAGE:
 *    We use a 'List' (ArrayList) to store all available courses because it can grow dynamically.
 * 
 * 3. DELEGATION:
 *    The 'assignCourse' method delegates the work to the 'student.addCourse()' method.
 *    This is a good OOP practice where each object manages its own data.
 */
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
        // Delegation: Telling the student object to enroll in a course
        if (student != null && course != null) {
            student.addCourse(course);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        return new ArrayList<>(this.allCourses);
    }

    @Override
    public void deleteCourse(int id) {
        this.allCourses.removeIf(c -> c.getCourseId() == id);
    }
}
