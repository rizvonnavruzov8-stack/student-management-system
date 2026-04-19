package com.sms.controller;

import com.sms.exception.CourseNotFoundException;
import com.sms.exception.InvalidGradeException;
import com.sms.exception.StudentNotFoundException;
import com.sms.model.Enrollment;
import com.sms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller — enrollment and grade assignment.
 */
@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    /** GET /api/enrollments */
    @GetMapping
    public List<Enrollment> getAll() {
        return enrollmentService.getAllEnrollments();
    }

    /** GET /api/enrollments/student/{studentDbId} */
    @GetMapping("/student/{studentDbId}")
    public List<Enrollment> getByStudent(@PathVariable int studentDbId) {
        return enrollmentService.getStudentCourses(studentDbId);
    }

    /** POST /api/enrollments — body: { "studentDbId": 1, "courseDbId": 2 } */
    @PostMapping
    public ResponseEntity<Enrollment> enroll(@RequestBody Map<String, Integer> body)
            throws StudentNotFoundException, CourseNotFoundException {
        int studentDbId = body.get("studentDbId");
        int courseDbId = body.get("courseDbId");
        return new ResponseEntity<>(enrollmentService.enrollStudent(studentDbId, courseDbId), HttpStatus.CREATED);
    }

    /** PUT /api/enrollments/{enrollmentId}/grade — body: { "grade": 85.5 } */
    @PutMapping("/{enrollmentId}/grade")
    public ResponseEntity<Enrollment> assignGrade(
            @PathVariable int enrollmentId,
            @RequestBody Map<String, Double> body)
            throws InvalidGradeException, StudentNotFoundException {
        double grade = body.get("grade");
        return ResponseEntity.ok(enrollmentService.assignGrade(enrollmentId, grade));
    }

    /** DELETE /api/enrollments/{enrollmentId} — unenroll student */
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> delete(@PathVariable int enrollmentId) {
        enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.noContent().build();
    }
}
