package com.sms.model;

public class GraduateStudent extends Student {
    private String thesisTopic;

    public GraduateStudent() {
        super();
    }

    public GraduateStudent(int id, String name, double gpa, String thesisTopic) {
        super(id, name, gpa);
        this.thesisTopic = thesisTopic;
    }

    public String getThesisTopic() {
        return thesisTopic;
    }

    public void setThesisTopic(String thesisTopic) {
        this.thesisTopic = thesisTopic;
    }

    @Override
    public double calculateGPA() {
        // Example implementation for graduate students differing from standard students
        // Demonstrating method overriding and polymorphism
        return super.getGpa();
    }

    @Override
    public String toString() {
        return "GraduateStudent{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", gpa=" + getGpa() +
                ", thesisTopic='" + thesisTopic + '\'' +
                ", courses=" + getCourses() +
                '}';
    }
}
