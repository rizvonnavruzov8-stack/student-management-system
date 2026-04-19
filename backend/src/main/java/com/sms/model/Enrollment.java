package com.sms.model;

import java.time.LocalDate;

/**
 * OOP — INHERITANCE & ENCAPSULATION
 *
 * Enrollment links a Student to a Course (Many-to-Many bridge).
 * Extends BaseEntity to inherit id, createdAt, updatedAt — consistent
 * with all other entity classes in the model layer.
 */
public class Enrollment extends BaseEntity {

    private int studentDbId;   // references Student.id
    private int courseDbId;    // references Course.id
    private String enrollmentDate;
    private double grade;      // 0-100; -1 = not yet assigned

    // --- Constructors ---

    public Enrollment() {
        super();
        this.enrollmentDate = LocalDate.now().toString();
        this.grade = -1;
    }

    public Enrollment(int enrollmentId, int studentDbId, int courseDbId) {
        super(enrollmentId);
        this.studentDbId = studentDbId;
        this.courseDbId = courseDbId;
        this.enrollmentDate = LocalDate.now().toString();
        this.grade = -1;
    }

    // POLYMORPHISM — implements the abstract method from BaseEntity
    @Override
    public String getSummary() {
        return "Enrollment{id=" + getId() + ", studentId=" + studentDbId
               + ", courseId=" + courseDbId + ", grade=" + grade + "}";
    }

    // --- Getters & Setters ---

    /** Convenience alias — returns the inherited BaseEntity id */
    public int getEnrollmentId() { return getId(); }
    public void setEnrollmentId(int enrollmentId) { setId(enrollmentId); }

    public int getStudentDbId() { return studentDbId; }
    public void setStudentDbId(int studentDbId) { this.studentDbId = studentDbId; }

    public int getCourseDbId() { return courseDbId; }
    public void setCourseDbId(int courseDbId) { this.courseDbId = courseDbId; }

    public String getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(String enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) {
        this.grade = grade;
        this.touch(); // ENCAPSULATION — update audit timestamp on change
    }

    @Override
    public String toString() {
        return "Enrollment{id=" + getId() + ", studentDbId=" + studentDbId
               + ", courseDbId=" + courseDbId + ", date='" + enrollmentDate
               + "', grade=" + grade + "}";
    }
}
