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

    void deleteEnrollment(int enrollmentId);

    List<Enrollment> getStudentCourses(int studentDbId);

    List<Enrollment> getAllEnrollments();

    /** Called by FileManager on startup to restore persisted data */
    void loadEnrollments(List<Enrollment> loaded);
}
