package com.sms.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends BaseEntity {
    private String name;
    private double gpa;
    private List<Course> courses;

    public Student() {
        super();
        this.courses = new ArrayList<>();
    }

    public Student(int id, String name, double gpa) {
        super(id);
        this.name = name;
        this.gpa = gpa;
        this.courses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        if (course != null) {
            this.courses.add(course);
        }
    }

    public double calculateGPA() {
        return this.gpa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                ", courses=" + courses +
                '}';
    }
}
