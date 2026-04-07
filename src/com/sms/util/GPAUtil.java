package com.sms.util;

import com.sms.exception.InvalidGradeException;

import java.util.List;

public class GPAUtil {

    /**
     * Calculates the GPA based on a list of grades on a 0-100 scale.
     */
    public static double calculateGPA(List<Double> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        double totalScore = 0;
        for (Double grade : grades) {
            if (grade < 0 || grade > 100) {
                throw new InvalidGradeException("Invalid grade encountered: " + grade + " (Must be between 0 and 100).");
            }
            totalScore += grade;
        }

        double averageGrade = totalScore / grades.size();
        
        // Simple conversion to 4.0 scale
        return (averageGrade / 100.0) * 4.0;
    }
}
