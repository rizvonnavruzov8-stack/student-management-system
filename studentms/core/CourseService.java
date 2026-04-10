package com.sms.service;

import com.sms.model.Course;
import com.sms.exception.InvalidGradeException;
import com.sms.exception.StudentNotFoundException;

public interface CourseService {
    /**
     * Adds a course to the course repository.
     * @param c the course to add
     */
    void addCourse(Course c);

    /**
     * Assigns a grade to a specific student for a course.
     * @param studentId the ID of the student
     * @param courseId the ID of the course
     * @param grade the grade to assign (0-100)
     * @throws InvalidGradeException if the grade is outside the valid range
     * @throws StudentNotFoundException if the student is not found
     */
    void assignGrade(int studentId, String courseId, double grade) throws InvalidGradeException, StudentNotFoundException;
}
