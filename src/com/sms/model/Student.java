package com.sms.model;

import com.sms.util.GPAUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 🎓 EXPLANATION: Student (The Core Data Model)
 * 
 * 1. REPRESENTATION:
 *    This class represents a student in the system with a name, GPA, and a list of enrolled courses.
 * 
 * 2. WHY IT EXISTS:
 *    It stores all data related to a single student and provides methods to manage their courses.
 * 
 * 3. OOP CONCEPTS USED:
 *    - INHERITANCE: It 'extends BaseEntity', inheriting the 'id' field and its methods.
 *    - ENCAPSULATION: Fields are 'private'; access is controlled via 'getters' and 'setters'.
 *    - COMPOSITION: It HAS-A 'List' of 'Course' objects.
 */
public class Student extends BaseEntity {
    
    private String name;
    private String surname;
    private String email;
    private String gender;
    private double gpa;
    
    // Using List<Course> instead of Arrays for dynamic sizing and easier manipulation
    private List<Course> courses;
    private List<Double> grades; // New: To store grades on 0-100 scale

    public Student() {
        super();
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public Student(int id, String name, String surname, String email, String gender, double gpa) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.gpa = gpa;
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        } else {
            System.out.println("Invalid GPA value. Must be between 0.0 and 4.0.");
        }
    }

    public List<Course> getCourses() {
        // Returns the list of courses the student is enrolled in
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }

    /**
     * Adds a grade to the student's record and recalculates global GPA.
     */
    public void addGrade(double grade) {
        this.grades.add(grade);
        try {
            this.gpa = calculateGPA();
        } catch (Exception e) {
            // Logged in Menu/Service
        }
    }

    /**
     * Adds a course to the student's list while preventing duplicates.
     * LOGIC:
     * 1. Check if course is null.
     * 2. Iterate through existing courses.
     * 3. If course ID matches, do not add (duplicate prevention).
     */
    public void addCourse(Course course) {
        if (course != null) {
            boolean exists = false;
            for (Course c : this.courses) {
                if (c.getCourseId() == course.getCourseId()) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                this.courses.add(course);
            }
        }
    }

    /**
     * Calculates the GPA based on the list of grades using GPAUtil.
     * POLYMORPHISM: This method can be overridden by subclasses.
     */
    public double calculateGPA() {
        try {
            return GPAUtil.calculateGPA(this.grades);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", gpa=" + gpa +
                ", courses=" + courses +
                '}';
    }
}
