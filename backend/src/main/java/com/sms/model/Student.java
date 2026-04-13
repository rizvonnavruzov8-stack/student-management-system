package com.sms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * OOP — INHERITANCE & ENCAPSULATION
 *
 * Student extends BaseEntity, inheriting id, createdAt, updatedAt.
 * Adds student-specific fields: firstName, lastName, email, dateOfBirth, status.
 * All fields are private (encapsulation).
 */
public class Student extends BaseEntity {

    private String studentId;   // e.g. "STU-001"
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth; // ISO format: YYYY-MM-DD
    private String status;      // ACTIVE | INACTIVE | GRADUATED
    private double gpa;
    private List<Double> grades = new ArrayList<>();

    // --- Constructors ---

    public Student() {
        super();
        this.status = "ACTIVE";
    }

    public Student(int id, String studentId, String firstName, String lastName,
                   String email, String dateOfBirth, String status) {
        super(id);
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    // POLYMORPHISM — overrides abstract method from BaseEntity
    @Override
    public String getSummary() {
        return firstName + " " + lastName + " (" + studentId + ") | GPA: " + String.format("%.2f", gpa);
    }

    // --- Getters & Setters ---

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public List<Double> getGrades() { return grades; }
    public void setGrades(List<Double> grades) { this.grades = grades; }

    // Helper: recalculate GPA from grades list
    public void recalculateGpa() {
        if (grades == null || grades.isEmpty()) {
            this.gpa = 0.0;
            return;
        }
        double sum = 0;
        for (double g : grades) sum += g;
        double average = sum / grades.size();
        // Convert 0-100 scale to 0-4.0 scale
        this.gpa = (average / 100.0) * 4.0;
    }

    @Override
    public String toString() {
        return "Student{id=" + getId() + ", studentId='" + studentId +
               "', name='" + firstName + " " + lastName + "', gpa=" + gpa + "}";
    }
}
