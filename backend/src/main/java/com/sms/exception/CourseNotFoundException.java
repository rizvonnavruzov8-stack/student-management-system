package com.sms.exception;

/**
 * EXCEPTION HANDLING — Custom checked exception.
 * Thrown when a course cannot be found by ID.
 */
public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(int id) {
        super("Course with ID " + id + " was not found.");
    }
    public CourseNotFoundException(String message) {
        super(message);
    }
}
