package com.sms.service.impl;

import com.sms.exception.CourseNotFoundException;
import com.sms.exception.InvalidGradeException;
import com.sms.exception.StudentNotFoundException;
import com.sms.model.Enrollment;
import com.sms.model.Student;
import com.sms.service.CourseService;
import com.sms.service.EnrollmentService;
import com.sms.service.StudentService;
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
 *
 * Depends on StudentService and CourseService INTERFACES (not concrete classes)
 * to follow SOLID dependency inversion principle.
 */
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final List<Enrollment> enrollments = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @Autowired private FileManager fileManager;
    @Autowired private ActivityLogger activityLogger;
    // Inject interfaces, not concrete implementations (SOLID — Dependency Inversion)
    @Autowired private StudentService studentService;
    @Autowired private CourseService courseService;

    @Override
    public Enrollment enrollStudent(int studentDbId, int courseDbId)
            throws StudentNotFoundException, CourseNotFoundException {

        // Validate both entities exist (throws if not found)
        studentService.findStudentById(studentDbId);
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
            .filter(e -> e.getId() == enrollmentId)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Enrollment not found: " + enrollmentId));

        enrollment.setGrade(grade); // also updates updatedAt via touch()

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
    public void deleteEnrollment(int enrollmentId) {
        Enrollment enrollment = enrollments.stream()
            .filter(e -> e.getId() == enrollmentId)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Enrollment not found: " + enrollmentId));

        enrollments.remove(enrollment);

        // Recalculate GPA after removal
        Student student = studentService.findStudentById(enrollment.getStudentDbId());
        List<Double> allGrades = enrollments.stream()
            .filter(e -> e.getStudentDbId() == enrollment.getStudentDbId() && e.getGrade() >= 0)
            .map(Enrollment::getGrade)
            .collect(Collectors.toList());
        student.setGrades(allGrades);
        student.recalculateGpa();

        fileManager.saveEnrollments(enrollments);
        fileManager.saveStudents(studentService.getStudentsRef());
        activityLogger.log("DELETE_ENROLLMENT", "Deleted enrollment ID=" + enrollmentId);
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

    @Override
    public void loadEnrollments(List<Enrollment> loaded) {
        enrollments.clear();
        enrollments.addAll(loaded);
        int maxId = loaded.stream().mapToInt(Enrollment::getId).max().orElse(0);
        idCounter.set(maxId + 1);
    }
}
