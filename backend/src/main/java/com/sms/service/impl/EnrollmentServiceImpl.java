package com.sms.service.impl;

import com.sms.exception.CourseNotFoundException;
import com.sms.exception.InvalidGradeException;
import com.sms.exception.StudentNotFoundException;
import com.sms.model.Enrollment;
import com.sms.model.Student;
import com.sms.service.EnrollmentService;
import com.sms.util.FileManager;
import com.sms.util.ActivityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * POLYMORPHISM — Implements EnrollmentService interface.
 * EXCEPTION HANDLING — validates grades before assignment.
 */
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final List<Enrollment> enrollments = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @Autowired private FileManager fileManager;
    @Autowired private ActivityLogger activityLogger;
    @Autowired private StudentServiceImpl studentService;
    @Autowired private CourseServiceImpl courseService;

    @Override
    public Enrollment enrollStudent(int studentDbId, int courseDbId)
            throws StudentNotFoundException, CourseNotFoundException {

        // Validate both entities exist (throws if not found)
        Student student = studentService.findStudentById(studentDbId);
        courseService.findCourseById(courseDbId);

        Enrollment enrollment = new Enrollment(idCounter.getAndIncrement(), studentDbId, courseDbId);
        enrollments.add(enrollment);
        fileManager.saveEnrollments(enrollments);
        activityLogger.log("ENROLL", "Student ID=" + studentDbId + " → Course ID=" + courseDbId);
        return enrollment;
    }

    @Override
    public Enrollment assignGrade(int enrollmentId, double grade)
            throws InvalidGradeException, StudentNotFoundException {

        // EXCEPTION HANDLING — grade validation
        if (grade < 0 || grade > 100) {
            throw new InvalidGradeException(grade);
        }

        Enrollment enrollment = enrollments.stream()
            .filter(e -> e.getEnrollmentId() == enrollmentId)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Enrollment not found: " + enrollmentId));

        enrollment.setGrade(grade);

        // Recalculate student GPA
        Student student = studentService.findStudentById(enrollment.getStudentDbId());
        List<Double> allGrades = enrollments.stream()
            .filter(e -> e.getStudentDbId() == enrollment.getStudentDbId() && e.getGrade() >= 0)
            .map(Enrollment::getGrade)
            .collect(Collectors.toList());
        student.setGrades(allGrades);
        student.recalculateGpa();

        fileManager.saveEnrollments(enrollments);
        fileManager.saveStudents(studentService.getStudentsRef());
        activityLogger.log("GRADE", "Enrollment ID=" + enrollmentId + " grade=" + grade);
        return enrollment;
    }

    @Override
    public List<Enrollment> getStudentCourses(int studentDbId) {
        return enrollments.stream()
            .filter(e -> e.getStudentDbId() == studentDbId)
            .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }

    public void loadEnrollments(List<Enrollment> loaded) {
        enrollments.clear();
        enrollments.addAll(loaded);
        int maxId = loaded.stream().mapToInt(Enrollment::getEnrollmentId).max().orElse(0);
        idCounter.set(maxId + 1);
    }
}
