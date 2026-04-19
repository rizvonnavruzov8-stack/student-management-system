package com.sms.model;

/**
 * OOP — MULTI-LEVEL INHERITANCE & POLYMORPHISM
 *
 * GraduateStudent extends Student (which itself extends BaseEntity).
 * Adds graduate-specific fields and overrides getSummary() — demonstrating
 * method overriding (runtime polymorphism).
 */
public class GraduateStudent extends Student {

    private String researchTopic;
    private String supervisorName;

    // Constructors

    public GraduateStudent() {
        super();
        this.setStatus("ACTIVE");
    }

    public GraduateStudent(int id, String studentId, String firstName, String lastName,
                           String email, String dateOfBirth, String status,
                           String researchTopic, String supervisorName) {
        super(id, studentId, firstName, lastName, email, dateOfBirth, status);
        this.researchTopic = researchTopic;
        this.supervisorName = supervisorName;
    }

    // POLYMORPHISM — specialized override of the base getSummary()
    @Override
    public String getSummary() {
        return super.getSummary() + " | Research: " + researchTopic +
               " (Supervisor: " + supervisorName + ") [GRADUATE]";
    }

    // Getters & Setters

    public String getResearchTopic() {
        return researchTopic; }
    public void setResearchTopic(String researchTopic) {
        this.researchTopic = researchTopic; }

    public String getSupervisorName() {
        return supervisorName; }
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName; }

    @Override
    public String toString() {
        return "GraduateStudent{id=" + getId() + ", studentId='" + getStudentId() +
               "', name='" + getFirstName() + " " + getLastName() +
               "', research='" + researchTopic + "'}";
    }
}
