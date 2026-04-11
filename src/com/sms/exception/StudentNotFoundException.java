package com.sms.exception;

/**
 * Custom Exception thrown when a student cannot be found in the system.
 * This is a checked exception, meaning the developer MUST handle it using try-catch or throws.
 */
public class StudentNotFoundException extends Exception {
    
    // Default constructor
    public StudentNotFoundException() {
        super("Student not found.");
    }

    // Constructor that accepts a custom message
    public StudentNotFoundException(String message) {
        super(message);
    }
}
