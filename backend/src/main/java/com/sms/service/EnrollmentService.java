package com.sms.service;

import com.sms.exception.CourseNotFoundException;
import com.sms.exception.InvalidGradeException;
import com.sms.exception.StudentNotFoundException;
import com.sms.model.Enrollment;

import java.util.List;

/**
 * INTERFACE — contract for enrollment operations.
 */
public interface EnrollmentService {

    Enrollment enrollStudent(int studentDbId, int courseDbId)
        throws StudentNotFoundException, CourseNotFoundException;

    Enrollment assignGrade(int enrollmentId, double grade)
        throws InvalidGradeException, StudentNotFoundException;

    List<Enrollment> getStudentCourses(int studentDbId);

    List<Enrollment> getAllEnrollments();
}
