package com.sms.util;

import com.sms.model.Student;
import java.util.List;

/**
 * 🎓 EXPLANATION: SortUtil (Algorithm Class)
 * 
 * 1. WHY IT EXISTS:
 *    To organize student data for better readability (e.g., finding top students).
 * 
 * 2. LOGIC (Bubble Sort):
 *    - It compares adjacent elements and swaps them if they are in the wrong order.
 *    - After the first pass, the smallest (or largest) element "bubbles up" to its correct position.
 *    - NESTED LOOPS: The outer loop controls the number of passes, and the inner loop performs the comparisons.
 */
public class SortUtil {

    /**
     * Sorts students based on GPA in descending order using Bubble Sort.
     * @param students The list of students to sort.
     */
    public static void sortStudentsByGPA(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return;
        }

        int n = students.size();
        
        // Outer loop: iterate through the entire list
        for (int i = 0; i < n - 1; i++) {
            // Inner loop: compare adjacent elements
            for (int j = 0; j < n - i - 1; j++) {
                // If the current student's GPA is LESS than the next one, SWAP them (Descending)
                if (students.get(j).getGpa() < students.get(j + 1).getGpa()) {
                    // Logic for Swapping:
                    // 1. Temporarily store student A
                    // 2. Put B in A's spot
                    // 3. Put temp (A) in B's spot
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }
}
