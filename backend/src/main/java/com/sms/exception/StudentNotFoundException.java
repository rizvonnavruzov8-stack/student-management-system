package com.sms.exception;

/**
 * EXCEPTION HANDLING — Custom checked exception.
 * Thrown when a student cannot be found by ID.
 */
public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(int id) {
        super("Student with ID " + id + " was not found.");
    }
    public StudentNotFoundException(String message) {
        super(message);
    }
}
