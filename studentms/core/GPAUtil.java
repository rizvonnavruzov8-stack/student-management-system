package core;

public class GPAUtil {

    /**
     * Calculates the overall GPA (weighted average) based on grades and credits.
     * Assumes grades are 0-100 as per specification.
     * @param grades Array of grades
     * @param credits Array of corresponding credits
     * @return Calculated weighted average GPA
     */
    public static double calculate(double[] grades, int[] credits) {
        if (grades == null || credits == null || grades.length != credits.length || grades.length == 0) {
            return 0.0;
        }

        double totalWeightedPoints = 0.0;
        int totalCredits = 0;

        for (int i = 0; i < grades.length; i++) {
            totalWeightedPoints += (grades[i] * credits[i]);
            totalCredits += credits[i];
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return totalWeightedPoints / totalCredits; // Scale out of 100
    }
}
