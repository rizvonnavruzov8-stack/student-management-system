package com.sms.util;

import com.sms.model.Student;
import com.sms.exception.StudentNotFoundException;
import java.util.List;

/**
 * 🎓 EXPLANATION: SearchUtil (Algorithm Class)
 * 
 * 1. WHY IT EXISTS:
 *    To demonstrate searching algorithms. Here we use RECURSION.
 * 
 * 2. WHAT IS RECURSION?
 *    A method that calls itself to solve a smaller part of the problem.
 * 
 * 3. LOGIC (Step-by-Step):
 *    - BASE CASE 1: If we reached the end of the list and didn't find the ID -> Throw Exception.
 *    - BASE CASE 2: If the current student's ID matches -> Return the student.
 *    - RECURSIVE STEP: If not found yet, call the same method again with the NEXT index.
 */
public class SearchUtil {

    /**
     * Recursive search to find a student by ID.
     */
    public static Student searchStudentById(List<Student> students, int id) throws StudentNotFoundException {
        if (students == null || students.isEmpty()) {
            throw new StudentNotFoundException("Student list is empty or null.");
        }
        return searchRecursive(students, id, 0);
    }

    /**
     * Helper method for recursion.
     * @param students The list to search in
     * @param id The ID we are looking for
     * @param index Current position in the list
     * @return The found student
     * @throws StudentNotFoundException if student is not in the list
     */
    private static Student searchRecursive(List<Student> students, int id, int index) throws StudentNotFoundException {
        // BASE CASE 1: Index out of bounds (End of list)
        if (index >= students.size()) {
            throw new StudentNotFoundException("Student with ID " + id + " not found.");
        }

        // BASE CASE 2: Found the matching ID
        if (students.get(index).getId() == id) {
            return students.get(index);
        }

        // RECURSIVE STEP: Look in the next position
        return searchRecursive(students, id, index + 1);
    }
}
