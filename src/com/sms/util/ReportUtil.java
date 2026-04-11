package com.sms.util;

import com.sms.model.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * 🎓 EXPLANATION: ReportUtil (Data Analysis)
 * 
 * 1. WHY IT EXISTS:
 *    To satisfy the proposal requirement for "General Reports".
 * 
 * 2. FEATURES:
 *    - Top Students: Identifies the highest performing students.
 *    - GPA Summary: Calculates the system-wide average performance.
 */
public class ReportUtil {

    /**
     * Prints a summary of top students.
     */
    public static void generateTopStudentReport(List<Student> students, int limit) {
        if (students.isEmpty()) {
            System.out.println("No data available for report.");
            return;
        }

        System.out.println("\n--- 🏆 TOP " + limit + " STUDENTS REPORT ---");
        List<Student> sorted = new ArrayList<>(students);
        SortUtil.sortStudentsByGPA(sorted);

        for (int i = 0; i < Math.min(limit, sorted.size()); i++) {
            Student s = sorted.get(i);
            System.out.printf("%d. %s (GPA: %.2f)%n", (i+1), s.getName(), s.getGpa());
        }
    }

    /**
     * Calculates system-wide GPA average.
     */
    public static void generateSystemSummary(List<Student> students) {
        if (students.isEmpty()) return;

        double totalGPA = 0;
        for (Student s : students) {
            totalGPA += s.getGpa();
        }
        double avg = totalGPA / students.size();

        System.out.println("\n--- 📊 SYSTEM-WIDE GPA SUMMARY ---");
        System.out.println("Total Students: " + students.size());
        System.out.printf("Average GPA: %.2f / 4.0%n", avg);
    }
}
