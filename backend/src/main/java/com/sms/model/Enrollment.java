package com.sms.model;

import java.time.LocalDate;

/**
 * Enrollment — Links Student to a Course (Many-to-Many bridge).
 * Stores the assigned grade and enrollment date.
 */
public class Enrollment {

    private int enrollmentId;
    private int studentDbId;   // references Student.id
    private int courseDbId;    // references Course.id
    private String enrollmentDate;
    private double grade;      // 0-100; -1 = not yet assigned

    // --- Constructors ---

    public Enrollment() {
        this.enrollmentDate = LocalDate.now().toString();
        this.grade = -1;
    }

    public Enrollment(int enrollmentId, int studentDbId, int courseDbId) {
        this.enrollmentId = enrollmentId;
        this.studentDbId = studentDbId;
        this.courseDbId = courseDbId;
        this.enrollmentDate = LocalDate.now().toString();
        this.grade = -1;
    }

    // --- Getters & Setters ---

    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public int getStudentDbId() { return studentDbId; }
    public void setStudentDbId(int studentDbId) { this.studentDbId = studentDbId; }

    public int getCourseDbId() { return courseDbId; }
    public void setCourseDbId(int courseDbId) { this.courseDbId = courseDbId; }

    public String getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(String enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }
}
