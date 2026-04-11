package com.sms.exception;

/**
 * Custom Exception thrown when a grade or GPA value is outside the valid range (0-100 or 0-4.0).
 * This ensures data integrity by forcing the user to handle invalid inputs.
 */
public class InvalidGradeException extends Exception {

    // Default constructor
    public InvalidGradeException() {
        super("Invalid grade value provided.");
    }

    // Constructor that accepts a custom message
    public InvalidGradeException(String message) {
        super(message);
    }
}
