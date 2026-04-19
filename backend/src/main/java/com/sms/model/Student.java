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
    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        this.email = email;
    }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public List<Double> getGrades() { return grades; }
    public void setGrades(List<Double> grades) { this.grades = grades; }

    // Helper: recalculate GPA from grades list using university scale
    public void recalculateGpa() {
        if (grades == null || grades.isEmpty()) {
            this.gpa = 0.0;
            return;
        }
        double sum = 0;
        for (double g : grades) sum += g;
        double avg = sum / grades.size();

        // 90-100=4.0, 85-90=3.7, 80-85=3.3, 75-80=3.0, 70-75=2.7, 65-70=2.3, 60-65=2.0, 55-60=1.7, 50-55=1.0, <50=0.0
        if (avg >= 90) this.gpa = 4.0;
        else if (avg >= 85) this.gpa = 3.7;
        else if (avg >= 80) this.gpa = 3.3;
        else if (avg >= 75) this.gpa = 3.0; // User wrote 780, assumed typo for 80
        else if (avg >= 70) this.gpa = 2.7;
        else if (avg >= 65) this.gpa = 2.3;
        else if (avg >= 60) this.gpa = 2.0;
        else if (avg >= 55) this.gpa = 1.7;
        else if (avg >= 50) this.gpa = 1.0;
        else this.gpa = 0.0;
    }

    @Override
    public String toString() {
        return "Student{id=" + getId() + ", studentId='" + studentId +
               "', name='" + firstName + " " + lastName + "', gpa=" + gpa + "}";
    }
}
