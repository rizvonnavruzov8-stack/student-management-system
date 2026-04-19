package com.sms.model;

/**
 * OOP — INHERITANCE & ENCAPSULATION
 *
 * Course extends BaseEntity, inheriting id and audit timestamps.
 */
public class Course extends BaseEntity {

    private String courseId;      // e.g. "CS-101"
    private String courseName;
    private String courseCode;
    private int credits;
    private String instructorName;

    // Constructors

    public Course() {
        super();
    }

    public Course(int id, String courseId, String courseName, String courseCode,
                  int credits, String instructorName) {
        super(id);
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
        this.instructorName = instructorName;
    }

    // POLYMORPHISM — implements the abstract method from BaseEntity
    @Override
    public String getSummary() {
        return courseName + " (" + courseCode + ") | " + credits + " credits | " + instructorName;
    }

    // Getters & Setters

    public String getCourseId() {
        return courseId; }
    public void setCourseId(String courseId) {
        this.courseId = courseId; }

    public String getCourseName() {
        return courseName; }
    public void setCourseName(String courseName) {
        this.courseName = courseName; }

    public String getCourseCode() {
        return courseCode; }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode; }

    public int getCredits() {
        return credits; }
    public void setCredits(int credits) {
        this.credits = credits; }

    public String getInstructorName() {
        return instructorName; }
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName; }

    @Override
    public String toString() {
        return "Course{id=" + getId() + ", courseId='" + courseId +
               "', name='" + courseName + "'}";
    }
}
