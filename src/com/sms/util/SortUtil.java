package com.sms.util;

import com.sms.model.Student;

import java.util.List;

public class SortUtil {

    /**
     * Sorts students based on GPA in descending order using Bubble Sort.
     */
    public static void sortStudentsByGPA(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return;
        }

        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).getGpa() < students.get(j + 1).getGpa()) {
                    // Swap
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }
}
