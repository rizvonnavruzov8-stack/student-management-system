package com.sms.exception;

/**
 * EXCEPTION HANDLING — Custom checked exception.
 * Thrown when a grade value is outside the valid range (0–100).
 */
public class InvalidGradeException extends Exception {
    public InvalidGradeException(double grade) {
        super("Invalid grade: " + grade + ". Grade must be between 0 and 100.");
    }
    public InvalidGradeException(String message) {
        super(message);
    }
}
