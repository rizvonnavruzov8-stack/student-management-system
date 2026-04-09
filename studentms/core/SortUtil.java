package com.sms.util;

import com.sms.model.Student;
import java.util.List;

public class SortUtil {

    /**
     * Sorts the list of students by GPA in descending order using Bubble Sort.
     * This implementation shows a manual algorithm as typically required in CS courses.
     * @param students The list to sort
     */
    public static void sortByGPA(List<Student> students) {
        if (students == null || students.size() <= 1) {
            return; // Already sorted or empty
        }

        int n = students.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                // Descending order: swap if current is less than next
                if (students.get(j).getGpa() < students.get(j + 1).getGpa()) {
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                    swapped = true;
                }
            }
            // If no elements were swapped in the inner loop, it's already sorted
            if (!swapped) {
                break;
            }
        }
    }
}
