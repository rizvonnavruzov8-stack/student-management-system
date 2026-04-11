package com.sms.util;

import com.sms.exception.InvalidGradeException;
import java.util.List;

/**
 * 🎓 EXPLANATION: GPAUtil (Business Logic)
 * 
 * 1. WHY IT EXISTS:
 *    To provide a centralized place for grade-related calculations.
 * 
 * 2. LOGIC (Step-by-Step):
 *    - Check if the grades list is empty.
 *    - Loop through each grade.
 *    - Validate that each grade is between 0 and 100 (Throws InvalidGradeException if not).
 *    - Sum all grades and find the average.
 *    - Convert average to a 4.0 scale: (Average / 100) * 4.0.
 */
public class GPAUtil {

    /**
     * Calculates the GPA based on a list of grades on a 0-100 scale.
     * @param grades List of numeric grades
     * @return GPA on a 4.0 scale
     * @throws InvalidGradeException if a grade is out of range
     */
    public static double calculateGPA(List<Double> grades) throws InvalidGradeException {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        double totalScore = 0;
        for (Double grade : grades) {
            // Validation Logic: Grades must be realistic (0-100) as per proposal
            if (grade < 0 || grade > 100) {
                // Throwing a custom checked exception if data is invalid
                throw new InvalidGradeException("Invalid grade encountered: " + grade + " (Must be between 0 and 100).");
            }
            totalScore += grade;
        }

        double averageGrade = totalScore / grades.size();
        
        // Final Step: Conversion from 100-point scale to 4.0 scale
        // Formula: (Average / 100) * 4.0
        return (averageGrade / 100.0) * 4.0;
    }
}
